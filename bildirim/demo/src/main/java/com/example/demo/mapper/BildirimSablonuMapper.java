package com.example.demo.mapper;

import com.example.demo.dto.BildirimSablonuDto;
import com.example.demo.entity.BildirimSablonuEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BildirimSablonuMapper {

    BildirimSablonuDto entityToDto(BildirimSablonuEntity bildirimSablonuEntity);

    BildirimSablonuEntity dtoToEntity(BildirimSablonuDto bildirimSablonuDto);

    List<BildirimSablonuDto> dtoListToEntityList(List<BildirimSablonuEntity> bildirimSablonuEntityList);

    List<BildirimSablonuEntity> entityListToDtoList(List<BildirimSablonuDto> bildirimSablonuDtoList);

}
