package com.brs.pod.controller.dto;

import com.brs.pod.repository.entity.ProductReviewHistory;
import com.brs.pod.repository.vo.ReviewStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewHistoryResponse {
    private final ReviewStatus status;
    private final LocalDateTime createdAt;
    private final String reason;

    public static ReviewHistoryResponse from(ProductReviewHistory history) {
        return new ReviewHistoryResponse(
            history.getReviewStatus(),
            history.getCreatedAt(),
            history.getReason()
        );
    }
}
