package com.example.demo.dto.hasta;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class HastaOlusturmaDto {
    private String adi;
    private String soyadi;
    private OffsetDateTime dogumTarihi;
    private String cinsiyet;
    private Integer surum;
    private OffsetDateTime olusturulmaTarihi;
    private OffsetDateTime guncellenmeTarihi;
}
