package com.example.demo.controller;

import com.example.demo.dto.BildirimSablonuDto;
import com.example.demo.service.BildirimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bildirim")
public class BildirimController {

    @Autowired
    private BildirimService bildirimService;

    @PostMapping("/olustur")
    public BildirimSablonuDto bildirimSablonuOlustur(@RequestBody BildirimSablonuDto bildirimSablonuDto) {
        BildirimSablonuDto bildirimSablonuOlustur = bildirimService.bildirimSablonuOlustur(bildirimSablonuDto);
        return bildirimSablonuOlustur;
    }

    @PostMapping("/olustur-list")
    public List<BildirimSablonuDto> bildirimSablonuOlusturList(@RequestBody List<BildirimSablonuDto> bildirimSablonuDtoList) {
        List<BildirimSablonuDto> bildirimSablonuOlusturForList = bildirimService.bildirimSablonuOlusturForList(bildirimSablonuDtoList);

        return bildirimSablonuOlusturForList;
    }

    @GetMapping("/tum-bildirimler")
    public ResponseEntity<List<BildirimSablonuDto>> tumHastalariGetir() {
        List<BildirimSablonuDto> hastalar = bildirimService.tumBildirimSablonlariniGetir();
        return new ResponseEntity<>(hastalar, HttpStatus.OK);
    }

}
