package com.techhub.dto.reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponseDto {
    private boolean success;
    private String message;
    private Object data;
}