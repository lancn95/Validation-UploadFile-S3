package com.amigos.awsuploadimage.repository;

import com.amigos.awsuploadimage.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {
    Boolean existsUserProfileByUserProfileIdNotAndUsernameIgnoreCase(Long id, String username);

    Boolean existsUserProfileByUsernameIgnoreCase(String username);

    Boolean existsUserProfileByUserProfileIdAndFileNameIgnoreCase(Long id, String fileName);

}
