package com.tasklytic.userservice.repository;

import com.tasklytic.userservice.model.UserEntity;
import com.tasklytic.userservice.model.UserStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	// Fetch all users
	List<UserEntity> findAll(); 
	
    // Custom query to find user by email
    Optional<UserEntity> findByEmail(String email);

    // Custom query to find user by mobile number
    Optional<UserEntity> findByMobileNumber(String mobileNumber);

    // Custom query to find user by id
    Optional<UserEntity> findById(UUID userId);
    
    //Find all user by filter
	List<UserEntity> findByStatus(UserStatus status);
}
