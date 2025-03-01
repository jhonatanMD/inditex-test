package com.inditex.infrastructure.mapper;

import com.inditex.domain.model.PriceResponseDTO;
import com.inditex.infrastructure.repository.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPriceMapper {

    @Mapping(source = "brand.id", target = "brandId")
    PriceResponseDTO toDomain(PriceEntity entity);
}
