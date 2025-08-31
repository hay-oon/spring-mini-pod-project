package com.brs.pod.service;

import com.brs.pod.controller.dto.UserSignUpRequest;
import com.brs.pod.controller.dto.UserResponse;
import com.brs.pod.repository.UserRepository;
import com.brs.pod.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 2. 유저 회원가입 API
    @Transactional
    public UserResponse signUp(UserSignUpRequest request) {
        // 중복 아이디 체크
        if (userRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 유저 생성
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.create(request.getName(), encodedPassword);
        userRepository.save(user);

        return UserResponse.from(user);
    }

    // Spring Security 내부 처리를 위한 UserDetailsService 인터페이스 구현체
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // DaoAuthenticationProvider 에서 검증 시 사용하는 UserDetails 객체 반환 (암호화된 비밀번호 확인)
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
