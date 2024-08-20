package com.example.demo.dto.hasta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BildirimTercihiDto {

    private Long id;
    private String tercihTuru;
    private Boolean aktif;
    private Integer surum;

}
