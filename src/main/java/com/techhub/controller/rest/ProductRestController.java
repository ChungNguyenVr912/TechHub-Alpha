package com.techhub.controller.rest;

import com.techhub.dto.reponse.CommonResponseDto;
import com.techhub.dto.reponse.ProductResponseDto;
import com.techhub.dto.request.ProductRequestDto;
import com.techhub.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam @PageableDefault(sort = "price,asc") Pageable pageable){
        Page<ProductResponseDto> products = productService.findAll(pageable);
        CommonResponseDto commonResponseDto = CommonResponseDto.builder()
                .success(true).message("Success!").data(products).build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestPart ProductRequestDto productRequestDto, @RequestPart MultipartFile image){
        ProductResponseDto responseDto = productService.save(productRequestDto);
        CommonResponseDto commonResponseDto = CommonResponseDto.builder()
                .success(true).message("Success!").data(responseDto).build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }
}
