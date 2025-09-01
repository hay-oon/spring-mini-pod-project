package com.brs.pod.controller;

import com.brs.pod.controller.dto.*;
import com.brs.pod.service.ProductService;
import com.brs.pod.repository.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;

    // 3. 사진 업로드를 통한 상품 생성 API
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        return ResponseEntity.ok(productService.createProduct(request, user));
    }

    // 4. 현재 로그인한 유저가 등록한 상품 조회 API
    @GetMapping("/my")
    public ResponseEntity<List<ProductDetailResponse>> getMyProducts() {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        return ResponseEntity.ok(productService.getUserProducts(user));
    }

    // 5. 등록된 모든 상품 조회 API
    @GetMapping
    public ResponseEntity<PageResponse<ProductListResponse>> getRegisteredProducts(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        return ResponseEntity.ok(productService.getRegisteredProducts(page, size));
    }

    // 6. 등록된 상품에 대한 내부 리뷰 API
    @PatchMapping("/{productId}/status")
    public ResponseEntity<ProductDetailResponse> updateProductStatus(
            @PathVariable Integer productId,
            @Valid @RequestBody UpdateProductStatusRequest request) {
        return ResponseEntity.ok(productService.updateProductStatus(productId, request));
    }
}
