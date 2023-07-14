package com.techhub.controller.rest;

import com.techhub.dto.reponse.CommonResponseDto;
import com.techhub.dto.reponse.ProductDisplayDto;
import com.techhub.dto.reponse.ProductResponseDto;
import com.techhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductRestController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<?> getProducts(@PageableDefault(page = 0,size = 12) Pageable pageable) {
        List<ProductDisplayDto> products = productService.findAll(pageable);
        CommonResponseDto commonResponseDto = CommonResponseDto.builder()
                .success(true).message("Success!").data(products).build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }
}
