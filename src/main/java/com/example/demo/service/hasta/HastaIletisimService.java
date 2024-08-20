package com.example.demo.service.hasta;

import com.example.demo.dto.hasta.HastaIletisimDto;

import java.util.List;

public interface HastaIletisimService {

    HastaIletisimDto hastaIletisimOlustur(HastaIletisimDto hastaIletisimDto);

    List<HastaIletisimDto> hastaIletisimGetirById(Long id);

    List<HastaIletisimDto> tumHastaIletisimBilgileriniGetir();

    HastaIletisimDto hastaIletisimSilById(Long id);

    List<HastaIletisimDto> hastaIletisimleriOlustur(List<HastaIletisimDto> hastaIletisimDtoList);

}
