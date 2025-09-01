package com.brs.pod.repository.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String name;

    @Override
    public String getUsername() {
        return name;
    }

    private String password;
    
    private LocalDateTime createdAt;

    @Transient
    private List<SimpleGrantedAuthority> authorities;


    public static User create(String name, String password) {
        return new User(
            null,
            name,
            password,
            LocalDateTime.now(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
