package com.brs.pod.repository.entity;

import java.time.LocalDateTime;

import com.brs.pod.repository.vo.ReviewStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String imageUrl;
    private ReviewStatus currentStatus;
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    public static ProductImage create(String imageUrl, Product product) {
        ProductImage image = new ProductImage();
        image.imageUrl = imageUrl;
        image.currentStatus = ReviewStatus.REGISTERED;
        image.createdAt = LocalDateTime.now();
        image.product = product;
        return image;
    }
}
