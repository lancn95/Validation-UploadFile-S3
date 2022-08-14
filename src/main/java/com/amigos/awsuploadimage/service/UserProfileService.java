package com.amigos.awsuploadimage.service;

import com.amigos.awsuploadimage.profile.UserProfile;
import com.amigos.awsuploadimage.request.UserProfileRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserProfileService {

    List<UserProfile> findAllUserProfile();

    UserProfile createNewUserProfile(UserProfileRequest request);

    void uploadUserProfileImage(Long userId, MultipartFile file);
}
