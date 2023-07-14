package com.techhub.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantTypeResponseDto {
    private List<VariantResponseDto> variants;
    private Long id;
    private String name;
}
