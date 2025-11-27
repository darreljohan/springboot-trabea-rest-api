package com.iglo.trabea.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByWorkEmail(String workEmail);
    boolean existsByWorkEmail(String workEmail);
    Optional<User> findByWorkEmailAndUserRoles_Role_Name(String workEmail, String roleName);
}
