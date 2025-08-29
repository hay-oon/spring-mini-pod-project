package com.brs.pod.repository;

import com.brs.pod.repository.entity.BaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseProductRepository extends JpaRepository<BaseProduct, Integer> {
}
