package com.example.demo.dto.hasta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HastaDto {

    private Long id;
    private String adi;
    private String soyadi;
    private OffsetDateTime dogumTarihi;
    private String cinsiyet;
    private Integer surum;
    private OffsetDateTime olusturulmaTarihi;
    private OffsetDateTime guncellenmeTarihi;

    private List<HastaKimlikDto> kimlikler;
    private List<HastaIletisimDto> iletisimler;
    private List<BildirimTercihiDto> bildirimTercihleri;
}
