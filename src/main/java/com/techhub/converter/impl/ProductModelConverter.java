package com.techhub.converter.impl;

import com.techhub.converter.Converter;
import com.techhub.dto.reponse.ProductModelResponseDto;
import com.techhub.entity.Product;
import com.techhub.entity.ProductImage;
import com.techhub.entity.ProductModel;

import java.util.List;
import java.util.stream.Collectors;

public class ProductModelConverter implements Converter<ProductModel, ProductModelResponseDto> {
    @Override
    public ProductModelResponseDto convert(ProductModel productModel) {
        Product product = productModel.getProduct();
        ProductImage image = product.getProductImages().get(0);
        return ProductModelResponseDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .image(image.getUrl())
                .price(productModel.getPrice())
                .build();
    }

    @Override
    public List<ProductModelResponseDto> convert(List<ProductModel> productModelList) {
        return productModelList.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public ProductModel revert(ProductModelResponseDto source) {
        return null;
    }

    @Override
    public List<ProductModel> revert(List<ProductModelResponseDto> sourceList) {
        return null;
    }
}
