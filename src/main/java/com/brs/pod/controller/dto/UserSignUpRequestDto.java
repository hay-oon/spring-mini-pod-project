package com.brs.pod.controller.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignUpRequestDto {
    
    @Size(min = 5, max = 10, message = "아이디는 5자 이상 10자 이하여야 합니다.")
    private String name;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
            message = "비밀번호는 최소 8자 이상이며, 대문자와 소문자가 각각 1개 이상 포함되어야 합니다.")
    private String password;
}
