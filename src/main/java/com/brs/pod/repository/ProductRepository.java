package com.brs.pod.repository;

import com.brs.pod.repository.entity.Product;
import com.brs.pod.repository.entity.User;
import com.brs.pod.repository.vo.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByUserOrderByCreatedAtDesc(User user);
    
    Page<Product> findByCurrentStatusOrderByCreatedAtDesc(ReviewStatus status, Pageable pageable);
}