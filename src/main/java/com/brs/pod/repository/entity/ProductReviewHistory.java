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
public class ProductReviewHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private ReviewStatus reviewStatus;
    private String reason;
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    public static ProductReviewHistory create(Product product, ReviewStatus reviewStatus, String reason) {
        ProductReviewHistory history = new ProductReviewHistory();
        history.product = product;
        history.reviewStatus = reviewStatus;
        history.reason = reason;
        history.createdAt = LocalDateTime.now();
        return history;
    }
}