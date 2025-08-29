package com.brs.pod.controller.dto;

import com.brs.pod.repository.entity.Product;
import com.brs.pod.repository.entity.ProductImage;
import com.brs.pod.repository.vo.ReviewStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private final Integer id;
    private final String title;
    private final ReviewStatus currentStatus;
    private final LocalDateTime createdAt;
    private final Integer baseProductId;
    private final List<String> imageUrls;

    public static ProductResponse from(Product product, List<ProductImage> images) {
        return new ProductResponse(
            product.getId(),
            product.getTitle(),
            product.getCurrentStatus(),
            product.getCreatedAt(),
            product.getBaseProduct().getId(),
            images.stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList())
        );
    }
}


