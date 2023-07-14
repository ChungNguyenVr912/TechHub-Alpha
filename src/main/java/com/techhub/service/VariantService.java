package com.techhub.service;

import com.techhub.dto.reponse.VariantTypeResponseDto;

import java.util.List;

public interface VariantService {
    List<VariantTypeResponseDto> findAllVariantType();
}
