package com.amigos.awsuploadimage.service;

import com.amigos.awsuploadimage.entity.UserProfile;
import com.amigos.awsuploadimage.request.UserProfileCreateRequest;
import com.amigos.awsuploadimage.request.UserProfileUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserProfileService {

    List<UserProfile> findAllUserProfile();

    UserProfile createNewUserProfile(UserProfileCreateRequest request);

    void uploadUserProfileImage(Long userId, MultipartFile file);

    void update(Long id, UserProfileUpdateRequest request);

    void uploadUserFile(Long id, MultipartFile[] file);
}
