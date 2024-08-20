package com.example.demo.dto.hasta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HastaIletisimDto {

    private Long id;
    private String iletisimTuru;
    private String iletisimDegeri;
    private Integer surum;
    private Long hastaId;
}

