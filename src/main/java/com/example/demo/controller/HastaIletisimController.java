package com.example.demo.controller;

import com.example.demo.dto.hasta.HastaIletisimDto;
import com.example.demo.service.hasta.HastaIletisimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hasta-iletisim")
public class HastaIletisimController {

    @Autowired
    private HastaIletisimService hastaIletisimService;

    @PostMapping("/olustur")
    public ResponseEntity<HastaIletisimDto> hastaIletisimOlustur(@RequestBody HastaIletisimDto hastaIletisimDto){
        HastaIletisimDto createdHastaIletisim = hastaIletisimService.hastaIletisimOlustur(hastaIletisimDto);
        return new ResponseEntity<>(createdHastaIletisim, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<HastaIletisimDto>> getHastaIletisimById(@PathVariable Long id) {
        List<HastaIletisimDto> hastaIletisimDtoList = hastaIletisimService.hastaIletisimGetirById(id);
        return new ResponseEntity<>(hastaIletisimDtoList, HttpStatus.OK);
    }

    @GetMapping("/tum-hasta-iletisim")
    public ResponseEntity<List<HastaIletisimDto>> tumHastaIletisimBilgileriniGetir() {
        List<HastaIletisimDto> hastaIletisimDtoList = hastaIletisimService.tumHastaIletisimBilgileriniGetir();
        return new ResponseEntity<>(hastaIletisimDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHastaIletisim(@PathVariable Long id) {
        hastaIletisimService.hastaIletisimSilById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
