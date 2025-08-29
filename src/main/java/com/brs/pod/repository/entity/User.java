package com.brs.pod.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String name;
    
    private String password;
    
    private LocalDateTime createdAt;

    public static User create(String name, String password) {
        return new User(
            null,
            name,
            password,
            LocalDateTime.now()
        );
    }
}
