package com.brs.pod.controller.dto;

import com.brs.pod.repository.entity.Product;
import com.brs.pod.repository.entity.ProductImage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProductListResponse {
    private final Integer id;
    private final String title;
    private final LocalDateTime createdAt;
    private final Integer baseProductId;
    private final String sellerName;
    private final List<String> imageUrls;

    public static ProductListResponse from(Product product, List<ProductImage> images) {
        return new ProductListResponse(
            product.getId(),
            product.getTitle(),
            product.getCreatedAt(),
            product.getBaseProduct().getId(),
            product.getUser().getName(),
            images.stream()
                .map(ProductImage::getImageUrl)
                .toList()
        );
    }
}
