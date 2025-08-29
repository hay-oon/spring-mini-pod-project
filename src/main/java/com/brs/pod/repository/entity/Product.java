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

    public static Product create(String title, BaseProduct baseProduct, User user) {
        Product product = new Product();
        product.title = title;
        product.currentStatus = ReviewStatus.REGISTERED;
        product.createdAt = LocalDateTime.now();
        product.user = user; 
        product.baseProduct = baseProduct;
        return product;
    }
}
