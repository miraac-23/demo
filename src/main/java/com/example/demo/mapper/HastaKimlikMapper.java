package com.example.demo.mapper;

import com.example.demo.dto.hasta.HastaKimlikDto;
import com.example.demo.entity.HastaKimlikEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HastaKimlikMapper {

    HastaKimlikDto entityToDto(HastaKimlikEntity hastaKimlikEntity);

    HastaKimlikEntity dtoToEntity(HastaKimlikDto hastaKimlikDto);
}
