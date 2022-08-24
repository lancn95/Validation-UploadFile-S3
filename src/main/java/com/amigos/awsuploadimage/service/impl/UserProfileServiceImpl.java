package com.amigos.awsuploadimage.service.impl;

import com.amigos.awsuploadimage.entity.UserProfile;
import com.amigos.awsuploadimage.exceptions.DuplicateRecordException;
import com.amigos.awsuploadimage.exceptions.ResourceNotFoundException;
import com.amigos.awsuploadimage.repository.UserProfileRepository;
import com.amigos.awsuploadimage.request.UserProfileCreateRequest;
import com.amigos.awsuploadimage.request.UserProfileUpdateRequest;
import com.amigos.awsuploadimage.service.FileUploadService;
import com.amigos.awsuploadimage.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Value("${application.file.filesPath}")
    private String filesPath;

    private final UserProfileRepository userProfileRepository;
    private final FileUploadService fileUploadService;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, FileUploadService fileUploadService) {
        this.userProfileRepository = userProfileRepository;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public List<UserProfile> findAllUserProfile() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile createNewUserProfile(UserProfileCreateRequest request) {
        //check duplicate user's name is existed
        checkUserNameIsExisted(true, null, request.getUsername());

        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(request.getUsername());
        userProfile.setUserProfileImageLink(request.getLinkImage());
        userProfile.setAge(request.getAge());
        userProfileRepository.save(userProfile);
        return userProfile;
    }

    private void checkUserNameIsExisted(Boolean isCreatingNew, Long id, String name) {
        if (isCreatingNew) {
            Boolean isExisted = userProfileRepository.existsUserProfileByUsernameIgnoreCase(name.trim());
            if (isExisted) {
                throw new DuplicateRecordException("There is an existing user name associated to this organisation");
            }
        } else {
            Boolean nameIsExisted = userProfileRepository.existsUserProfileByUserProfileIdNotAndUsernameIgnoreCase(id, name.trim());
            if (nameIsExisted) {
                throw new DuplicateRecordException("There is an existing user name associated to this organisation");
            }
        }
    }

    @Override
    public void uploadUserProfileImage(Long userId, MultipartFile file) {
        //1. Check if image is not empty
        //2. If file is an image
        //3. The user exists in our database
        //4. Grab some metadata from file if any
        //5. Store the image in s3 and update database with s3 image link
    }

    @Override
    public void update(Long id, UserProfileUpdateRequest request) {
        checkUserNameIsExisted(false, id, request.getUsername());
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user name", "id", String.valueOf(id)));
        userProfile.setUsername(request.getUsername());
        userProfile.setUserProfileImageLink(request.getLinkImage());
        userProfile.setAge(request.getAge());
        userProfileRepository.save(userProfile);
    }

    @Override
    public void uploadUserFile(Long id, MultipartFile[] files) {
        UserProfile userProfile = getUserById(id);
        String[] userExistedFiles;
        String file = "";

        if (userProfile.getFileName() != null) {
            userExistedFiles = userProfile.getFileName().split(",");
            List<String> filesNameExisted;
            //check file name is existed
            filesNameExisted = checkFileIsExisted(files, userExistedFiles);

            // if there no file name is existed => save file
            if (filesNameExisted.size() == 0) {
                saveFile(files, userProfile, file);
            } else {
                // throw all file with name are existed
                throw new DuplicateRecordException(filesNameExisted);
            }
        } else {
            saveFile(files, userProfile, file);
        }
    }

    private List<String> checkFileIsExisted(MultipartFile[] files, String[] userExistedFiles) {
        Optional<String> result;
        List<String> filesNameExisted = new ArrayList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                int finalI = i;
                result = Arrays.stream(userExistedFiles)
                        .filter(x -> x.equals(files[finalI].getOriginalFilename()))
                        .findFirst();
                if (result.isPresent()) {
                    filesNameExisted.add(files[finalI].getOriginalFilename());
                }
            }
        }
        return filesNameExisted;
    }

    private void saveFile(MultipartFile[] files, UserProfile userProfile, String file) {
        for (int i = 0; i < files.length; i++) {
            String pathFile = storeFile(files[i], userProfile.getUserProfileId());
            if (!StringUtils.isBlank(pathFile)) {
                file = file + pathFile + ",";

            }
        }
        if (userProfile.getFileName() == null) {
            userProfile.setFileName(file);
        } else {
            userProfile.setFileName(userProfile.getFileName() + file);
        }
        userProfileRepository.save(userProfile);
    }


    @Override
    public ResponseEntity<?> downloadUserFile(Long userId, String fileName, HttpServletResponse httpServletResponse) throws IOException {
        UserProfile user = getUserById(userId);
        String[] arrOfStr = user.getFileName().split(",");
        String result = String.valueOf(Arrays.stream(arrOfStr)
                .filter(x -> x.equals(fileName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Not found file with name : " + fileName)));


        File file = new File(filesPath + "/business/" + userId + "/" + result);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(getHeader(file))
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private String storeFile(MultipartFile file, Long id) {
        if (file == null || file.isEmpty()) return "";
        String path = "/business/" + id + "/";
        return fileUploadService.store(file, path);
    }

    private UserProfile getUserById(Long userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user name", "id", String.valueOf(userId)));
    }

    private HttpHeaders getHeader(File file) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return header;
    }

}
