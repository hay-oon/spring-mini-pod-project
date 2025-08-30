package com.brs.pod.repository;

import com.brs.pod.repository.entity.Product;
import com.brs.pod.repository.entity.ProductReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewHistoryRepository extends JpaRepository<ProductReviewHistory, Integer> {
    List<ProductReviewHistory> findByProductOrderByCreatedAtDesc(Product product);
}
