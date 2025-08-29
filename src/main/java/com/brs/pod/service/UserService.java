package com.brs.pod.service;

import com.brs.pod.controller.dto.UserSignUpRequest;
import com.brs.pod.controller.dto.UserResponse;
import com.brs.pod.repository.UserRepository;
import com.brs.pod.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    @Transactional
    public UserResponse signUp(UserSignUpRequest request) {
        // 중복 아이디 체크
        if (userRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 유저 생성 및 저장
        User user = User.create(request.getName(), request.getPassword());
        userRepository.save(user);

        return UserResponse.from(user);
    }
}
