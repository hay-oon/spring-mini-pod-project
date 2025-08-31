package com.brs.pod.service;

import com.brs.pod.controller.dto.*;
import com.brs.pod.repository.*;
import com.brs.pod.repository.entity.*;
import com.brs.pod.repository.vo.ReviewStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final BaseProductRepository baseProductRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductReviewHistoryRepository productReviewHistoryRepository;

    // 3. 사진 업로드를 통한 상품 생성 API
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

    // 4. 현재 로그인한 유저가 등록한 상품 조회 API
    @Transactional
    public List<ProductDetailResponse> getUserProducts(User user) {
        // 유저가 등록한 상품 조회
        List<Product> products = productRepository.findByUserOrderByCreatedAtDesc(user);

        return products.stream()
                .map(product -> {
                    // 이미지 조회
                    List<ProductImage> images = productImageRepository.findByProductId(product.getId());
                    // 리뷰 히스토리 조회
                    List<ProductReviewHistory> histories = productReviewHistoryRepository.findByProductOrderByCreatedAtDesc(product);
                    
                    return ProductDetailResponse.from(product, images, histories);
                })
                .toList();
    }

    // 5. 등록된 모든 상품 조회 API
    @Transactional
    public PageResponse<ProductListResponse> getRegisteredProducts(int page, int size) {
        // 등록된 상품 중 승인된 상품만 조회 (Pagenation)
        Page<Product> productPage = productRepository.findByCurrentStatusOrderByCreatedAtDesc(
                ReviewStatus.APPROVED,
                PageRequest.of(page, size) // pageable 객체 생성
        );

        // 이미지 포함 응답 생성
        Page<ProductListResponse> responsePage = productPage.map(product -> {
            List<ProductImage> images = productImageRepository.findByProductId(product.getId());
            return ProductListResponse.from(product, images);
        });

        return PageResponse.from(responsePage);
    }

    // 6. 등록된 상품에 대한 내부 리뷰 API
    @Transactional
    public ProductDetailResponse updateProductStatus(Integer productId, UpdateProductStatusRequest request) {
        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // 상태 변경
        product.updateStatus(request.getStatus(), request.getReason());
        
        // 이미지 포함 응답 생성
        List<ProductImage> images = productImageRepository.findByProductId(product.getId());
        return ProductDetailResponse.from(product, images, product.getReviewHistories());
    }
}