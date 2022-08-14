package com.amigos.awsuploadimage.service.impl;

import com.amigos.awsuploadimage.exceptions.DuplicateRecordException;
import com.amigos.awsuploadimage.profile.UserProfile;
import com.amigos.awsuploadimage.repository.UserProfileRepository;
import com.amigos.awsuploadimage.request.UserProfileRequest;
import com.amigos.awsuploadimage.service.UserProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public List<UserProfile> findAllUserProfile() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile createNewUserProfile(UserProfileRequest request) {
        //check duplicate user's name is existed
        checkUserNameIsExisted(request.getUsername());

        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(request.getUsername());
        userProfile.setUserProfileImageLink(request.getLinkImage());
        userProfile.setAge(request.getAge());
        userProfileRepository.save(userProfile);
        return userProfile;
    }

    private void checkUserNameIsExisted(String name) {
        Boolean isExisted = userProfileRepository.existsUserProfileByUsernameIgnoreCase(name.trim());
        if(isExisted){
            throw new DuplicateRecordException("There is an existing user name associated to this organisation");
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


}
