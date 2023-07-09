package com.techhub.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductRequestDto {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Long price;
    @NotNull
    private Long stock;
    private MultipartFile image;
    private Long firstVariantId;
    private Long secondVariantId;
}
