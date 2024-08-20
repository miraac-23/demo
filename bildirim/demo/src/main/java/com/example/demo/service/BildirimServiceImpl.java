package com.example.demo.service;

import com.example.demo.dto.BildirimSablonuDto;
import com.example.demo.entity.BildirimSablonuEntity;
import com.example.demo.entity.HedefHastaEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppValidationException;
import com.example.demo.mapper.BildirimSablonuMapper;
import com.example.demo.repository.BildirimSablonuRepository;
import com.example.demo.repository.HedefHastaRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BildirimServiceImpl implements BildirimService {

    @Autowired
    private BildirimSablonuRepository bildirimSablonuRepository;

    @Autowired
    private HedefHastaRepository hedefHastaRepository;

    private BildirimSablonuMapper bildirimSablonuMapper;

    @Autowired
    public void setBildirimSablonuMapper(BildirimSablonuMapper bildirimSablonuMapper) {
        this.bildirimSablonuMapper = bildirimSablonuMapper;
    }




    @RabbitListener(queues = "hastaBildirimQueue")
    @Override
    public void receiveMessage(String message) {
        System.out.println("Received Message: " + message);

        // Process the message and extract information
        String[] parts = message.split(", ");
        Long hastaId = Long.valueOf(parts[0].split(": ")[1]);
        String cinsiyet = parts[1].split(": ")[1];
        Integer yas = Integer.valueOf(parts[2].split(": ")[1]);

        // Evaluate the patient's eligibility for notifications
        evaluateHastaForNotification(hastaId, cinsiyet, yas);
    }

    @Transactional
    public void evaluateHastaForNotification(Long hastaId, String cinsiyet, Integer yas) {
        List<BildirimSablonuEntity> sablonlar = bildirimSablonuRepository.findAll();

        boolean notificationSent = false;

        for (BildirimSablonuEntity sablon : sablonlar) {
            boolean matches = sablon.getCinsiyetKriteri().equals(cinsiyet) && yas.equals(sablon.getYasKriteri());

            if (matches) {
                HedefHastaEntity hedefHasta = new HedefHastaEntity();
                hedefHasta.setHastaId(hastaId);
                hedefHasta.setBildirimSablonu(sablon);
                hedefHasta.setBildirildi(Boolean.TRUE);
                hedefHastaRepository.save(hedefHasta);
                notificationSent = true;
            }
        }

        if (!notificationSent) {
            System.out.println("No matching notification template found for Hasta ID: {}"+ hastaId);
        } else {
            System.out.println("Notification sent for Hasta ID: {}"+ hastaId);
        }
    }

    @Override
    @Transactional()
    public List<BildirimSablonuDto> getAllBildirimSablonlari() {

        List<BildirimSablonuEntity> bildirimSablonuEntityList = bildirimSablonuRepository.findAll();

        return bildirimSablonuEntityList.stream().map(bildirimSablonuMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void sendNotification(Long sablonId) {
        List<HedefHastaEntity> hedefHastalar = hedefHastaRepository.findByBildirimSablonuId(sablonId);
        for (HedefHastaEntity hedefHasta : hedefHastalar) {
            // Burada bildirim gönderme işlemi yapılacak
        }
    }

    @Override
    public BildirimSablonuDto bildirimSablonuOlustur(BildirimSablonuDto bildirimSablonuDto) {

        try {
            if (bildirimSablonuDto == null) {
                throw new AppValidationException("Kayıt Bilgileri Eksik");
            }

            BildirimSablonuEntity bildirimSablonuEntity = bildirimSablonuMapper.dtoToEntity(bildirimSablonuDto);
            bildirimSablonuRepository.save(bildirimSablonuEntity);

            return bildirimSablonuMapper.entityToDto(bildirimSablonuEntity);
        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public List<BildirimSablonuDto> bildirimSablonuOlusturForList(List<BildirimSablonuDto> bildirimSablonuDtoList) {

        try {
            if (CollectionUtils.isEmpty(bildirimSablonuDtoList)) {
                throw new AppValidationException("Kayıt Bilgileri Eksik");
            }

            List<BildirimSablonuEntity> bildirimSablonuEntityList = bildirimSablonuDtoList.stream()
                    .map(bildirimSablonuMapper::dtoToEntity).collect(Collectors.toList());

            for (BildirimSablonuEntity bildirimSablonuEntity : bildirimSablonuEntityList) {
                bildirimSablonuRepository.save(bildirimSablonuEntity);
            }

            return bildirimSablonuMapper.dtoListToEntityList(bildirimSablonuEntityList);

        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public List<BildirimSablonuDto> tumBildirimSablonlariniGetir() {
        try {
            List<BildirimSablonuEntity> bildirimSablonuEntityList = bildirimSablonuRepository.findAll();
            return bildirimSablonuEntityList.stream().map(bildirimSablonuMapper::entityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

}