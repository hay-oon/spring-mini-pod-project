package com.brs.pod.repository.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.brs.pod.repository.vo.ReviewStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private ReviewStatus currentStatus;
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "base_product_id")
    private BaseProduct baseProduct;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReviewHistory> reviewHistories = new ArrayList<>();

    // updateStatus 처럼 처리
    // productImageRepository.saveAll(images); 없어도 됨
    public static Product create(String title, BaseProduct baseProduct, User user) {
        Product product = new Product();
        product.title = title;
        product.currentStatus = ReviewStatus.REGISTERED;
        product.createdAt = LocalDateTime.now();
        product.user = user; 
        product.baseProduct = baseProduct;
        return product;
    }

    public void updateStatus(ReviewStatus newStatus, String reason) {
        validateStatus(newStatus);
        ProductReviewHistory history = ProductReviewHistory.create(this, newStatus, reason);
        this.reviewHistories.add(history);
        this.currentStatus = newStatus;
    }

    private void validateStatus(ReviewStatus newStatus) {
        switch (newStatus) {
            // 등록 / 거절 / 금지일때만 > 승인으로 변경 가능
            case APPROVED -> {
                if (currentStatus != ReviewStatus.REGISTERED && 
                    currentStatus != ReviewStatus.REJECTED && 
                    currentStatus != ReviewStatus.BANNED) {
                    throw new IllegalArgumentException("현재 상태에서는 승인된 상품으로 변경할 수 없습니다.");
                }
            }
            // 등록일때만 > 거절 가능
            case REJECTED -> {
                if (currentStatus != ReviewStatus.REGISTERED) {
                    throw new IllegalArgumentException("등록된 상품만 거절 상태로 변경 할 수 있습니다.");
                }
            }
            // 승인일때만 > 금지 가능
            case BANNED -> {
                if (currentStatus != ReviewStatus.APPROVED) {
                    throw new IllegalArgumentException("승인된 상품만 금지 상태로 변경 할 수 있습니다.");
                }
            }
        }
    }
}
