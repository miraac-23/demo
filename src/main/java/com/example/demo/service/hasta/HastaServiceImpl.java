package com.example.demo.service.hasta;

import com.example.demo.dto.hasta.HastaDto;
import com.example.demo.entity.HastaEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppNotFoundException;
import com.example.demo.exception.AppValidationException;
import com.example.demo.mapper.HastaMapper;
import com.example.demo.repository.HastaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HastaServiceImpl implements HastaService {

    @Autowired
    private HastaRepository hastaRepository;

    private HastaMapper hastaMapper;

    @Autowired
    public void setHastaMapper(HastaMapper hastaMapper) {
        this.hastaMapper = hastaMapper;
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String QUEUE_NAME = "hastaBildirimQueue";

    @Override
    public HastaDto hastaOlustur(HastaDto hastaDto) {
        try {
            if (hastaDto == null) {
                throw new AppValidationException("Kayıt Bilgileri Eksik");
            }

            HastaEntity hastaEntity = hastaMapper.dtoToEntity(hastaDto);
            hastaRepository.save(hastaEntity);

            sendNotificationMessage(hastaEntity);

            return hastaMapper.entityToDto(hastaEntity);
        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public HastaDto hastaGetirById(Long id) {
        try {
            if (id == null) {
                throw new AppValidationException("Id bilgisi boş olamaz");
            }

            HastaEntity hastaEntity = hastaRepository.findById(id)
                    .orElseThrow(() -> new AppNotFoundException(id + " id'sine sahip bir hasta sistemde mevcut değil"));
            return hastaMapper.entityToDto(hastaEntity);
        } catch (AppValidationException | AppNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public List<HastaDto> hastaGetirByCinsiyet(String cinsiyet) {
        try {
            if (cinsiyet.isEmpty()) {
                throw new AppValidationException("Cinsiyet bilgisi boş olamaz");
            }

            List<HastaEntity> hastaEntityList = hastaRepository.findByCinsiyet(cinsiyet);


            return hastaEntityList.stream().map(hastaMapper::entityToDto).collect(Collectors.toList());
        } catch (AppValidationException | AppNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public  List<HastaDto> hastaGetirByAdi(String adi) {
        try {
            if (adi.isEmpty()) {
                throw new AppValidationException("Adi bilgisi boş olamaz");
            }

            List<HastaEntity> hastaEntityList = hastaRepository.findByAdi(adi);

            return hastaEntityList.stream().map(hastaMapper::entityToDto).collect(Collectors.toList());
        } catch (AppValidationException | AppNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public  List<HastaDto> hastaGetirBySoyadi(String soyadi) {
        try {
            if (soyadi.isEmpty()) {
                throw new AppValidationException("Cinsiyet bilgisi boş olamaz");
            }

            List<HastaEntity> hastaEntityList = hastaRepository.findBySoyadi(soyadi);

            return hastaEntityList.stream().map(hastaMapper::entityToDto).collect(Collectors.toList());
        } catch (AppValidationException | AppNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public List<HastaDto> tumHastalariGetir() {
        try {
            List<HastaEntity> hastaEntityList = hastaRepository.findAll();
            return hastaEntityList.stream().map(hastaMapper::entityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public HastaDto hastaSil(Long id) {
        try {
            if (id == null) {
                throw new AppValidationException("Hasta silmek için id bilgisi eksik");
            }
            HastaEntity hastaEntity = hastaRepository.findById(id)
                    .orElseThrow(() -> new AppNotFoundException(id + " id'sine sahip bir hasta sistemde mevcut değil"));

            hastaRepository.delete(hastaEntity);

            return hastaMapper.entityToDto(hastaEntity);
        } catch (AppValidationException | AppNotFoundException | AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);

        }
    }

    @Override
    public HastaDto hastaGuncelle(HastaDto hastaDto) {
        try {
            if (hastaDto == null || hastaDto.getId() == null) {
                throw new AppValidationException("Güncelleme Bilgileri Eksik");
            }

            HastaEntity existingHastaEntity = hastaRepository.findById(hastaDto.getId())
                    .orElseThrow(() -> new AppValidationException("Hasta bulunamadı"));

            HastaEntity updatedHastaEntity = hastaMapper.dtoToEntity(hastaDto);
            updatedHastaEntity.setId(existingHastaEntity.getId());

            hastaRepository.save(updatedHastaEntity);

            sendNotificationMessage(updatedHastaEntity);

            return hastaMapper.entityToDto(updatedHastaEntity);
        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }


    private void sendNotificationMessage(HastaEntity hasta) {
        String message = "Hasta ID: " + hasta.getId() + ", Cinsiyet: " + hasta.getCinsiyet() + ", Yaş: " + calculateAge(hasta.getDogumTarihi()) + ", Bildirim Tercihi" + hasta.getBildirimTercihleri();
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
    }

    private int calculateAge(OffsetDateTime birthDate) {
        return OffsetDateTime.now().getYear() - birthDate.getYear();
    }

}
