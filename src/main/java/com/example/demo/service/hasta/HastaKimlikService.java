package com.example.demo.service.hasta;

import com.example.demo.dto.hasta.HastaKimlikDto;

import java.util.List;

public interface HastaKimlikService {
    List<HastaKimlikDto> hastaKimlikOlustur(List<HastaKimlikDto> hastaKimlikDtoList);
}
