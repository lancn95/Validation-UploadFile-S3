package com.amigos.awsuploadimage.service.impl;

import com.amigos.awsuploadimage.profile.UserProfile;
import com.amigos.awsuploadimage.repository.UserProfileRepository;
import com.amigos.awsuploadimage.request.UserProfileRequest;
import com.amigos.awsuploadimage.service.UserProfileService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(request.getUsername());
        userProfile.setUserProfileImageLink(request.getLinkImage());
        userProfile.setAge(request.getAge());
        userProfileRepository.save(userProfile);
        return userProfile;
    }


}
