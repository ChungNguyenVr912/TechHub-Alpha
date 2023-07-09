package com.techhub.service.impl;

import com.techhub.dto.reponse.ProductResponseDto;
import com.techhub.dto.request.ProductRequestDto;
import com.techhub.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Page<ProductResponseDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ProductResponseDto save(ProductRequestDto productRequestDto) {
        return null;
    }
}
