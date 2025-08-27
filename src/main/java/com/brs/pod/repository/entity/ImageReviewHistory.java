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
public class ImageReviewHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private ReviewStatus reviewStatus;
    private String reason;
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "product_image_id")
    private ProductImage productImage   ;
}
