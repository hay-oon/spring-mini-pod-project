package com.brs.pod.repository;

import com.brs.pod.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByName(String name);
}
