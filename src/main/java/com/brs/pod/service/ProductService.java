package com.brs.pod.service;

import com.brs.pod.controller.dto.CreateProductRequest;
import com.brs.pod.controller.dto.ProductResponse;
import com.brs.pod.controller.dto.ProductDetailResponse;
import com.brs.pod.repository.*;
import com.brs.pod.repository.entity.BaseProduct;
import com.brs.pod.repository.entity.Product;
import com.brs.pod.repository.entity.ProductImage;
import com.brs.pod.repository.entity.ProductReviewHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.brs.pod.repository.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final BaseProductRepository baseProductRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductReviewHistoryRepository productReviewHistoryRepository;

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
                .toList();
        productImageRepository.saveAll(images);

        return ProductResponse.from(product, images);
    }

    @Transactional
    public List<ProductDetailResponse> getUserProducts(User user) {
        return productRepository.findByUserOrderByCreatedAtDesc(user).stream() // 사용자의 모든 상품 조회
                .map(product -> {

                    // 각 상품별 이미지 조회
                    List<ProductImage> images = productImageRepository.findByProductId(product.getId());

                    // 각 상품별 리뷰 이력 조회
                    List<ProductReviewHistory> histories = productReviewHistoryRepository.findByProductOrderByCreatedAtDesc(product);
                    
                    return ProductDetailResponse.from(product, images, histories);
                })
                .toList();
    }
}
