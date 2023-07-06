package com.techhub.service;

import com.techhub.dto.reponse.ProductResponseDto;
import com.techhub.dto.request.ProductRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Page<ProductResponseDto> findAll(Pageable pageable);

    ProductResponseDto save(ProductRequestDto productRequestDto);
}
