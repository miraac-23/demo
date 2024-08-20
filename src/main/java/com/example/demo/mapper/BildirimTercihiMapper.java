package com.example.demo.mapper;

import com.example.demo.dto.hasta.BildirimTercihiDto;
import com.example.demo.entity.BildirimTercihiEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BildirimTercihiMapper {

    BildirimTercihiDto entityToDto(BildirimTercihiEntity bildirimTercihiEntity);

    BildirimTercihiEntity dtoToEntity(BildirimTercihiDto bildirimTercihiDto);

    List<BildirimTercihiDto> dtoListToEntityList(List<BildirimTercihiEntity> bildirimTercihiEntityList);

    List<BildirimTercihiEntity> entityListToDtoList(List<BildirimTercihiDto> bildirimTercihiDtoList);
}
