package com.example.demo.controller;

import com.example.demo.dto.hasta.HastaDto;
import com.example.demo.service.hasta.HastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/hasta")
public class HastaController {

    @Autowired
    private HastaService hastaService;

    @PostMapping("/olustur")
    public ResponseEntity<HastaDto> hastaOlustur(@RequestBody HastaDto hastaDto) {
        hastaDto.setSurum(1);
        hastaDto.setOlusturulmaTarihi(OffsetDateTime.now());
        hastaDto.setGuncellenmeTarihi(OffsetDateTime.now());
        HastaDto createdHasta = hastaService.hastaOlustur(hastaDto);
        return new ResponseEntity<>(createdHasta, HttpStatus.CREATED);
    }

    @PostMapping("/guncelle/{id}")
    public ResponseEntity<HastaDto> hastaGuncelle(@PathVariable Long id, @RequestBody HastaDto hastaDto) {
        hastaDto.setGuncellenmeTarihi(OffsetDateTime.now());
        HastaDto updatedHasta = hastaService.hastaGuncelle(hastaDto);
        return new ResponseEntity<>(updatedHasta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HastaDto> getHastaById(@PathVariable Long id) {
        HastaDto hastaDto = hastaService.hastaGetirById(id);
        return new ResponseEntity<>(hastaDto, HttpStatus.OK);
    }

    @GetMapping("/tum-hastalar")
    public ResponseEntity<List<HastaDto>> tumHastalariGetir() {
        List<HastaDto> hastalar = hastaService.tumHastalariGetir();
        return new ResponseEntity<>(hastalar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHasta(@PathVariable Long id) {
        hastaService.hastaSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/adi/{adi}")
    public ResponseEntity<List<HastaDto>> hastalariGetirByAdi(@PathVariable String  adi) {
        List<HastaDto> hastalar = hastaService.hastaGetirByAdi(adi);
        return new ResponseEntity<>(hastalar, HttpStatus.OK);
    }

    @GetMapping("/soyadi/{soyadi}")
    public ResponseEntity<List<HastaDto>> hastalariGetirBySoyadi(@PathVariable String  soyadi) {
        List<HastaDto> hastalar = hastaService.hastaGetirBySoyadi(soyadi);
        return new ResponseEntity<>(hastalar, HttpStatus.OK);
    }
    @GetMapping("/cinsiyet/{cinsiyet}")
    public ResponseEntity<List<HastaDto>> hastalariGetirByCinsiyet(@PathVariable String  cinsiyet) {
        List<HastaDto> hastalar = hastaService.hastaGetirByCinsiyet(cinsiyet);
        return new ResponseEntity<>(hastalar, HttpStatus.OK);
    }
}
