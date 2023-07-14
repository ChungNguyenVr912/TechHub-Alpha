package com.techhub.service;

import com.techhub.dto.reponse.ProductDisplayDto;
import com.techhub.dto.reponse.ProductResponseDto;
import com.techhub.dto.request.ProductCreateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    List<ProductDisplayDto> findAll(Pageable pageable);

    ProductResponseDto save(ProductCreateRequestDto productCreateRequestDto) throws IOException;

    List<ProductResponseDto> findAllProductType();
}
