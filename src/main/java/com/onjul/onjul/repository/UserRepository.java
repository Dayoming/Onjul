package com.onjul.onjul.repository;

import com.onjul.onjul.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
