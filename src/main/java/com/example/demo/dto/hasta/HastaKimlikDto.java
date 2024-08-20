package com.example.demo.dto.hasta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HastaKimlikDto {

    private Long id;
    private String kimlikTuru;
    private String kimlikDegeri;
    private Integer surum;
    private HastaDto hastaDto;

}
