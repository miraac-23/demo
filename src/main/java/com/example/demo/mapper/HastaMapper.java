package com.example.demo.mapper;

import com.example.demo.dto.hasta.HastaDto;
import com.example.demo.entity.HastaEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HastaIletisimMapper.class, HastaKimlikMapper.class, BildirimTercihiMapper.class})
public interface HastaMapper {

    HastaDto entityToDto(HastaEntity hastaEntity);

    HastaEntity dtoToEntity(HastaDto hastaDto);

    List<HastaDto> entityListToDtoList(List<HastaEntity> hastaEntityList);

    List<HastaEntity> dtoListToEntityList(List<HastaDto> hastaDtoList);

    @AfterMapping
    default void setRelations(@MappingTarget HastaEntity hastaEntity) {
        if (hastaEntity.getKimlikler() != null) {
            hastaEntity.getKimlikler().forEach(kimlik -> kimlik.setHasta(hastaEntity));
        }

        if (hastaEntity.getIletisimler() != null) {
            hastaEntity.getIletisimler().forEach(iletisim -> iletisim.setHasta(hastaEntity));
        }

        if (hastaEntity.getBildirimTercihleri() != null) {
            hastaEntity.getBildirimTercihleri().forEach(bildirimTercihi -> bildirimTercihi.setHasta(hastaEntity));
        }
    }
}
