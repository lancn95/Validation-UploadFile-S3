package com.amigos.awsuploadimage.repository;

import com.amigos.awsuploadimage.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {
    Boolean existsUserProfileByUserProfileIdNotAndUsernameIgnoreCase(Long id, String username);

    Boolean existsUserProfileByUsernameIgnoreCase(String username);

    Boolean existsUserProfileByUserProfileIdAndFileNameIgnoreCase(Long id, String fileName);

    /**
     * Find user by user name
     * @param userName
     * @return User
     */
    Optional<UserProfile> findByUsername(String userName);

    /**
     * Check exists an user by user name
     * @param userName
     * @return Boolean
     */
    Boolean existsByUsername(String userName);

    /**
     * Check exists an user by email
     * @param email
     * @return Boolean
     */
    Boolean existsByEmail(String email);

}
