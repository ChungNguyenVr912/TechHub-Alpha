package com.techhub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequestDto {
    @NotBlank
    private Long id;
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
