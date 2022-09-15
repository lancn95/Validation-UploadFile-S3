package com.amigos.awsuploadimage.repository;

import com.amigos.awsuploadimage.entity.Role;
import com.amigos.awsuploadimage.enumeration.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find role by name
     * @param name
     * @return Role
     */
    Optional<Role> findByName(ERole name);
}
