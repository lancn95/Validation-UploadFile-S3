package com.amigos.awsuploadimage.service.impl;

import com.amigos.awsuploadimage.exceptions.DuplicateRecordException;
import com.amigos.awsuploadimage.exceptions.ResourceNotFoundException;
import com.amigos.awsuploadimage.entity.UserProfile;
import com.amigos.awsuploadimage.repository.UserProfileRepository;
import com.amigos.awsuploadimage.request.UserProfileCreateRequest;
import com.amigos.awsuploadimage.request.UserProfileUpdateRequest;
import com.amigos.awsuploadimage.service.FileUploadService;
import com.amigos.awsuploadimage.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {
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
        //1. Check if iamge is not empty
        //2. If file is an iamge
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
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user name", "id", String.valueOf(id)));
        String file = "";
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String pathFile = storeFile(files[i], id);
                if(!StringUtils.isBlank(pathFile)){
                    file = file + pathFile + ",";
                }
            }
        }
        if(userProfile.getFileName() == null){
            userProfile.setFileName(file);
        }else{
            userProfile.setFileName(userProfile.getFileName() + "," + file);
        }
        userProfileRepository.save(userProfile);
    }

    private String storeFile(MultipartFile file, Long id) {
        if (file == null || file.isEmpty()) return "";
        String path = "UserWithId" + id + "/";
        return fileUploadService.store(file, path);
    }


}
