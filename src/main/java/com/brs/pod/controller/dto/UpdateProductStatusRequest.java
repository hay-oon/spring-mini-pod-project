package com.brs.pod.controller.dto;

import com.brs.pod.repository.vo.ReviewStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProductStatusRequest {
    
    @NotNull
    private ReviewStatus status;
    
    private String reason;
}
