package com.brs.pod.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateProductRequest {
    
    @NotNull(message = "베이스 상품 ID는 필수입니다.")
    private Integer baseProductId;

    @NotEmpty // 띄어쓰기 포함 20자 고려
    @Size(max = 20, message = "상품 이름은 최대 20자까지 가능합니다.")
    private String title;

    @NotBlank
    @Size(min = 1, max = 5, message = "상품 이미지는 최소 1장, 최대 5장까지 등록 가능합니다.")
    private List<String> imageUrls;
}
