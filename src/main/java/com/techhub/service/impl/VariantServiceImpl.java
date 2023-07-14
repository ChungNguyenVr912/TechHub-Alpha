package com.techhub.service.impl;

import com.techhub.converter.impl.VariantConverter;
import com.techhub.converter.impl.VariantTypeConverter;
import com.techhub.dto.reponse.VariantResponseDto;
import com.techhub.dto.reponse.VariantTypeResponseDto;
import com.techhub.entity.Variant;
import com.techhub.entity.VariantType;
import com.techhub.repository.VariantRepository;
import com.techhub.repository.VariantTypeRepository;
import com.techhub.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {
    private final VariantRepository variantRepository;
    private final VariantTypeRepository variantTypeRepository;
    private final VariantTypeConverter variantTypeConverter;
    private final VariantConverter variantConverter;

    @Override
    public List<VariantTypeResponseDto> findAllVariantType() {
        List<VariantType> variantTypes = variantTypeRepository.findAll();
        List<VariantTypeResponseDto> variantTypeResponseDtoList = new ArrayList<>();
        for (VariantType variantType : variantTypes) {
            if (variantType.getId() != 1) {
                VariantTypeResponseDto variantTypeResponseDto = variantTypeConverter.convert(variantType);
                List<Variant> variants = variantType.getVariants();
                variantTypeResponseDto
                        .setVariants(variants.stream()
                                .map(variantConverter::convert)
                                .filter(variantResponseDto -> variantResponseDto.getId() != 1)
                                .collect(Collectors.toList()));
                variantTypeResponseDtoList.add(variantTypeResponseDto);

            }
        }
        return variantTypeResponseDtoList;
    }
}
