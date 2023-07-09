package com.techhub.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class VariantTypeRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private List<String> variants;
}
