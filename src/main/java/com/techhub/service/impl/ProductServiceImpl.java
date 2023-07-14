package com.techhub.service.impl;

import com.techhub.converter.Converter;
import com.techhub.dto.reponse.ProductDisplayDto;
import com.techhub.dto.reponse.ProductResponseDto;
import com.techhub.dto.request.ProductCreateRequestDto;
import com.techhub.entity.Product;
import com.techhub.entity.ProductImage;
import com.techhub.entity.ProductModel;
import com.techhub.entity.Variant;
import com.techhub.repository.ProductModelRepository;
import com.techhub.repository.ProductRepository;
import com.techhub.repository.VariantRepository;
import com.techhub.service.ImageService;
import com.techhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;
    private final ProductModelRepository productModelRepository;
    private final ImageService imageService;
    private final Converter<Product, ProductResponseDto> productDtoConverter;

    @Override
    public List<ProductDisplayDto> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductDisplayDto> displayDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductModel productModel = productModelRepository.findFirstByProductOrderByPrice(product);
            displayDtoList.add(ProductDisplayDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .image((product.getProductImages().get(0)).getUrl())
                            .price(productModel.getPrice())
                    .build());
        }
        return displayDtoList;
    }

    @Override
    public ProductResponseDto save(ProductCreateRequestDto productCreateRequestDto) throws IOException {
        Product product;
        String imageUrl = null;
        Long id = productCreateRequestDto.getId();
        if (id != null) {
            product = productRepository.findById(id).get();
        } else {
            product = Product.builder()
                    .name(productCreateRequestDto.getName())
                    .description(productCreateRequestDto.getDescription())
                    .addedDate(LocalDateTime.now())
                    .productImages(new ArrayList<>())
                    .build();
            MultipartFile image = productCreateRequestDto.getImage();
            String fileName = imageService.save(image);
            imageUrl = imageService.getImageUrl(fileName);
            ProductImage productImage = new ProductImage(imageUrl);
            product.getProductImages().add(productImage);
            productImage.setProduct(product);
            productRepository.save(product);
        }

        Long firstVariantId = productCreateRequestDto.getFirstVariantId();
        Long secondVariantId = productCreateRequestDto.getSecondVariantId();
        Variant firstVariant = firstVariantId != null ? variantRepository.getReferenceById(firstVariantId) : null;
        Variant secondVariant = secondVariantId != null ? variantRepository.getReferenceById(secondVariantId) : null;

        ProductModel productModel
                = ProductModel.builder()
                .product(product)
                .price(productCreateRequestDto.getPrice())
                .stock(productCreateRequestDto.getStock())
                .firstVariant(firstVariant)
                .secondVariant(secondVariant)
                .sold(0L)
                .build();

        productModelRepository.save(productModel);

        ProductResponseDto productResponseDto = productDtoConverter.convert(product);
        productResponseDto.setImage(imageUrl);
        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> findAllProductType() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = productDtoConverter.convert(product);
            ProductImage image = product.getProductImages().get(0);
            if (image != null) {
                productResponseDto.setImage(image.getUrl());
            }
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }
}
