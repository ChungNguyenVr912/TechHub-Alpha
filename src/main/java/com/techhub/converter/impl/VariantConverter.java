package com.techhub.converter.impl;

import com.techhub.converter.Converter;
import com.techhub.dto.reponse.VariantResponseDto;
import com.techhub.entity.Variant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VariantConverter implements Converter<Variant, VariantResponseDto> {
    @Override
    public VariantResponseDto convert(Variant variant) {
        VariantResponseDto variantResponseDto = new VariantResponseDto();
        BeanUtils.copyProperties(variant,variantResponseDto);
        return variantResponseDto;
    }

    @Override
    public List<VariantResponseDto> convert(List<Variant> sourceList) {
        return null;
    }

    @Override
    public Variant revert(VariantResponseDto source) {
        return null;
    }

    @Override
    public List<Variant> revert(List<VariantResponseDto> sourceList) {
        return null;
    }
}
