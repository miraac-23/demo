package com.example.demo.service.hasta;

import com.example.demo.dto.hasta.HastaDto;
import com.example.demo.dto.hasta.HastaIletisimDto;
import com.example.demo.entity.HastaIletisimEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppNotFoundException;
import com.example.demo.exception.AppValidationException;
import com.example.demo.mapper.HastaIletisimMapper;
import com.example.demo.mapper.HastaMapper;
import com.example.demo.repository.HastaIletisimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HastaIletisimServiceImpl implements HastaIletisimService {

    @Autowired
    private HastaIletisimRepository hastaIletisimRepository;

    private HastaIletisimMapper hastaIletisimMapper;

    private HastaService hastaService;

    @Autowired
    public void setHastaService(HastaService hastaService) {
        this.hastaService = hastaService;
    }

    private HastaMapper hastaMapper;

    @Autowired
    public void setHastaMapper(HastaMapper hastaMapper) {
        this.hastaMapper = hastaMapper;
    }

    @Autowired
    public void setHastaIletisimMapper(HastaIletisimMapper hastaIletisimMapper) {
        this.hastaIletisimMapper = hastaIletisimMapper;
    }


    @Override
    public HastaIletisimDto hastaIletisimOlustur(HastaIletisimDto hastaIletisimDto) {

        try {
            if (hastaIletisimDto == null) {
                throw new AppValidationException("Kayıt Bilgileri Eksik");
            }

            HastaDto hastaDto = hastaService.hastaGetirById(hastaIletisimDto.getHastaId());

            if (hastaDto == null) {

                throw new AppValidationException("Girilen hasta sistemde mevcut değil");

            }

            HastaIletisimEntity hastaIletisimEntity = hastaIletisimMapper.dtoToEntity(hastaIletisimDto);

            hastaIletisimEntity.setHasta(hastaMapper.dtoToEntity(hastaDto));
            hastaIletisimRepository.save(hastaIletisimEntity);

            return hastaIletisimMapper.entityToDto(hastaIletisimEntity);

        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }


    }

    @Override
    public List<HastaIletisimDto> hastaIletisimGetirById(Long id) {

        try {
            if (id == null) {
                throw new AppValidationException("Id bilgisi boş olamaz");
            }

            List<HastaIletisimEntity> actualHastaIletisim = new ArrayList<>();


            List<HastaIletisimEntity> hastaIletisimEntityList = hastaIletisimRepository.findAll();

            for (HastaIletisimEntity hastaIletisimEntity : hastaIletisimEntityList) {

                if (hastaIletisimEntity.getHasta().getId() == id) {
                    actualHastaIletisim.add(hastaIletisimEntity);
                }
            }

            return actualHastaIletisim.stream().map(hastaIletisimMapper::entityToDto).collect(Collectors.toList());

        } catch (AppValidationException | AppNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }


    }

    @Override
    public List<HastaIletisimDto> tumHastaIletisimBilgileriniGetir() {

        try {
            List<HastaIletisimEntity> hastaIletisimEntityList = hastaIletisimRepository.findAll();

            return hastaIletisimEntityList.stream().map(hastaIletisimMapper::entityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

    }

    @Override
    public HastaIletisimDto hastaIletisimSilById(Long id) {

        try {
            if (id == null) {
                throw new AppValidationException("Hasta iletişim bilgisi silmek için id bilgisi eksik");
            }

            HastaIletisimEntity hastaIletisimEntity = hastaIletisimRepository.findById(id)
                    .orElseThrow(() -> new AppNotFoundException(id + " id'sine sahip bir hasta iletişim bilgisi sistemde mevcut değil"));

            hastaIletisimRepository.delete(hastaIletisimEntity);

            return hastaIletisimMapper.entityToDto(hastaIletisimEntity);

        } catch (AppValidationException | AppNotFoundException | AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);

        }
    }

    @Override
    public List<HastaIletisimDto> hastaIletisimleriOlustur(List<HastaIletisimDto> hastaIletisimDtoList) {

        try {
            if (CollectionUtils.isEmpty(hastaIletisimDtoList)) {
                throw new AppValidationException("Hasta iletisim bilgileri bulunamadı");
            }

            for (HastaIletisimDto hastaIletisimDto : hastaIletisimDtoList) {
                hastaIletisimRepository.save(hastaIletisimMapper.dtoToEntity(hastaIletisimDto));
            }

            return hastaIletisimDtoList;
        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

    }


}
