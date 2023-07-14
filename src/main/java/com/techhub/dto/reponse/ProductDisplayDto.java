package com.techhub.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDisplayDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Long price;
    private boolean lowOnStock;
}
