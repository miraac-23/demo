package com.example.demo.service.hasta;

import com.example.demo.dto.hasta.HastaDto;

import java.util.List;

public interface HastaService {
    HastaDto hastaOlustur(HastaDto hastaDto);

    HastaDto hastaGetirById(Long id);
    List<HastaDto> hastaGetirByCinsiyet(String cinsiyet);
    List<HastaDto> hastaGetirByAdi(String adi);
    List<HastaDto> hastaGetirBySoyadi(String soyadi);
    List<HastaDto> tumHastalariGetir();
    HastaDto hastaSil(Long id);

    HastaDto hastaGuncelle(HastaDto hastaDto);

}
