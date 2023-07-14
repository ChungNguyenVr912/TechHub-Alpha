package com.techhub.controller.rest;

import com.techhub.dto.reponse.CommonResponseDto;
import com.techhub.dto.reponse.ProductResponseDto;
import com.techhub.dto.reponse.VariantTypeResponseDto;
import com.techhub.dto.request.ProductCreateRequestDto;
import com.techhub.service.ProductService;
import com.techhub.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager/products")
public class ProductManagerRestController {
    private final ProductService productService;
    private final VariantService variantService;
    @PostMapping
    public ResponseEntity<?> createProduct(@ModelAttribute ProductCreateRequestDto productCreateRequestDto) throws IOException {
        ProductResponseDto responseDto = productService.save(productCreateRequestDto);
        CommonResponseDto commonResponseDto = CommonResponseDto.builder()
                .success(true).message("Success!").data(responseDto).build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<?> getAllProductType() {
        List<ProductResponseDto> products = productService.findAllProductType();
        CommonResponseDto commonResponseDto = CommonResponseDto.builder()
                .success(true).message("Success!").data(products).build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }
    @GetMapping("/variant-types")
    public ResponseEntity<?> getAllVariantType() {
        List<VariantTypeResponseDto> responseDtoList = variantService.findAllVariantType();
        CommonResponseDto commonResponseDto = CommonResponseDto.builder()
                .success(true).message("Success!").data(responseDtoList).build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }
}
