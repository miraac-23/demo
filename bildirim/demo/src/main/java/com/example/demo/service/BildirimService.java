package com.example.demo.service;

import com.example.demo.dto.BildirimSablonuDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.List;

public interface BildirimService {
    @RabbitListener(queues = "hastaBildirimQueue")
    void receiveMessage(String message);

    void evaluateHastaForNotification(Long hastaId, String cinsiyet, Integer yas);
    List<BildirimSablonuDto> getAllBildirimSablonlari();
    void sendNotification(Long sablonId);

    BildirimSablonuDto bildirimSablonuOlustur(BildirimSablonuDto bildirimSablonuDto);

    List<BildirimSablonuDto> bildirimSablonuOlusturForList(List<BildirimSablonuDto> bildirimSablonuDtoList);

    List<BildirimSablonuDto> tumBildirimSablonlariniGetir();


}