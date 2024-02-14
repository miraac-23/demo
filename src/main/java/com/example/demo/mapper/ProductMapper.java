package com.example.demo.mapper;

import com.example.demo.dto.product.ProductAddDto;
import com.example.demo.dto.product.ProductResultDto;
import com.example.demo.dto.product.ProductUpdateDto;
import com.example.demo.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity addDtoToEntity(ProductAddDto productAddDto);
    ProductResultDto entityToSonucDto(ProductEntity productEntity);

    ProductResultDto entityToResultDto(ProductEntity productEntity);

    void updateDtoToEntity(ProductUpdateDto productUpdateDto, @MappingTarget ProductEntity productEntity);


}
