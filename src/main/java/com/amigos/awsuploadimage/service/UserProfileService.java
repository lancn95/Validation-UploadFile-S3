package com.amigos.awsuploadimage.service;

import com.amigos.awsuploadimage.profile.UserProfile;
import com.amigos.awsuploadimage.request.UserProfileRequest;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserProfileService {

    List<UserProfile> findAllUserProfile();

    UserProfile createNewUserProfile(UserProfileRequest request);

}
