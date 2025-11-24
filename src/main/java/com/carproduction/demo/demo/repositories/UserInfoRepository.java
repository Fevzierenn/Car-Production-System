package com.carproduction.demo.demo.repositories;

import com.carproduction.demo.demo.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByName(String name); // Added for login flexibility
}
