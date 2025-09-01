package com.brs.pod.controller.dto;

import com.brs.pod.repository.entity.Product;
import com.brs.pod.repository.entity.ProductImage;
import com.brs.pod.repository.vo.ReviewStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProductDetailResponse {
    private final Integer id;
    private final String title;
    private final ReviewStatus currentStatus;
    private final LocalDateTime createdAt;
    private final Integer baseProductId;
    private final List<String> imageUrls;
    private final List<ReviewHistoryResponse> reviewHistories;

    public static ProductDetailResponse from( Product product) {
        return new ProductDetailResponse(
            product.getId(),
            product.getTitle(),
            product.getCurrentStatus(),
            product.getCreatedAt(),
            product.getBaseProduct().getId(),
            product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .toList(),
            product.getReviewHistories().stream()
                .map(ReviewHistoryResponse::from)
                .toList()
        );
    }
}
