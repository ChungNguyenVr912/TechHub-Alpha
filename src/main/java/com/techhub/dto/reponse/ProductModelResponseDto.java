package com.techhub.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModelResponseDto {
    private Long productId;
    private String productName;
    private String description;
    private Long price;
    private String image;
}
