package com.brs.pod.controller;

import com.brs.pod.controller.dto.CreateProductRequest;
import com.brs.pod.controller.dto.ProductResponse;
import com.brs.pod.controller.dto.ProductDetailResponse;
import com.brs.pod.service.ProductService;
import com.brs.pod.repository.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        return ResponseEntity.ok(productService.createProduct(request, user));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProductDetailResponse>> getMyProducts() {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        return ResponseEntity.ok(productService.getUserProducts(user));
    }
}
