package com.techhub.converter.impl;

import com.techhub.converter.Converter;
import com.techhub.dto.reponse.VariantTypeResponseDto;
import com.techhub.entity.VariantType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class VariantTypeConverter implements Converter<VariantType, VariantTypeResponseDto> {
    @Override
    public VariantTypeResponseDto convert(VariantType variantType) {
        return new VariantTypeResponseDto(new ArrayList<>() ,variantType.getId(), variantType.getName());
    }

    @Override
    public List<VariantTypeResponseDto> convert(List<VariantType> sourceList) {
        return null;
    }

    @Override
    public VariantType revert(VariantTypeResponseDto source) {
        return null;
    }

    @Override
    public List<VariantType> revert(List<VariantTypeResponseDto> sourceList) {
        return null;
    }
}
