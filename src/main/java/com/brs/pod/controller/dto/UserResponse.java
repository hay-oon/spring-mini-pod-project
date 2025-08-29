package com.brs.pod.controller.dto;

import java.time.LocalDateTime;

import com.brs.pod.repository.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final Integer id;
    private final String name;
    private final LocalDateTime createdAt;

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getCreatedAt()
        );
    }
}
