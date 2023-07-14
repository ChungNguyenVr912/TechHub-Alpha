package com.techhub.converter.impl;

import com.techhub.converter.Converter;
import com.techhub.dto.reponse.ProductResponseDto;
import com.techhub.entity.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductConverter implements Converter<Product, ProductResponseDto> {
    @Override
    public ProductResponseDto convert(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        BeanUtils.copyProperties(product,productResponseDto);
        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> convert(List<Product> products) {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : products) {
            productResponseDtoList.add(convert(product));
        }
        return productResponseDtoList;
    }

    @Override
    public Product revert(ProductResponseDto source) {
        return null;
    }

    @Override
    public List<Product> revert(List<ProductResponseDto> sourceList) {
        return null;
    }
}
