package com.brs.pod.service;

import com.brs.pod.controller.dto.CreateProductRequest;
import com.brs.pod.controller.dto.ProductResponse;
import com.brs.pod.repository.BaseProductRepository;
import com.brs.pod.repository.ProductImageRepository;
import com.brs.pod.repository.ProductRepository;
import com.brs.pod.repository.entity.BaseProduct;
import com.brs.pod.repository.entity.Product;
import com.brs.pod.repository.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.brs.pod.repository.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final BaseProductRepository baseProductRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request, User user) {
        // 베이스 상품 존재 여부 확인
        BaseProduct baseProduct = baseProductRepository.findById(request.getBaseProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 베이스 상품입니다."));

        // 상품 생성
        Product product = Product.create(request.getTitle(), baseProduct, user);
        productRepository.save(product);

        // 이미지 생성
        List<ProductImage> images = request.getImageUrls().stream()
                .map(url -> ProductImage.create(url, product))
                .collect(Collectors.toList());
        productImageRepository.saveAll(images);

        return ProductResponse.from(product, images);
    }
}
