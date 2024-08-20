package com.example.demo.mapper;

import com.example.demo.dto.HedefHastaDto;
import com.example.demo.entity.HedefHastaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HedefHastaMapper {

    HedefHastaDto entityToDto(HedefHastaEntity hedefHastaEntity);

    HedefHastaEntity dtoToEntity(HedefHastaDto hedefHastaDto);

    List<HedefHastaDto> dtoListToEntityList(List<HedefHastaEntity> hedefHastaEntityList);

    List<HedefHastaEntity> entityListToDtoList(List<HedefHastaDto> hedefHastaDtoList);
}
