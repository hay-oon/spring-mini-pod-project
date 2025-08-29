package com.brs.pod.controller.dto;

import java.time.LocalDateTime;

import com.brs.pod.repository.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private final Integer id;
    private final String name;
    private final LocalDateTime createdAt;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getCreatedAt()
        );
    }
}
