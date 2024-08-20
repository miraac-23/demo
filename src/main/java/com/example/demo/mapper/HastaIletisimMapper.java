package com.example.demo.mapper;

import com.example.demo.dto.hasta.HastaIletisimDto;
import com.example.demo.entity.HastaIletisimEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HastaIletisimMapper {

    HastaIletisimDto entityToDto(HastaIletisimEntity hastaIletisimEntity);

    HastaIletisimEntity dtoToEntity(HastaIletisimDto hastaIletisimDto);

    List<HastaIletisimDto> dtoListToEntityList(List<HastaIletisimEntity> hastaIletisimEntityList);

    List<HastaIletisimEntity> entityListToDtoList(List<HastaIletisimDto> hastaIletisimDtoList);
}
