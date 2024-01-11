package com.minproject.session.mapper;

import com.minproject.session.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserName(String userName);
}
