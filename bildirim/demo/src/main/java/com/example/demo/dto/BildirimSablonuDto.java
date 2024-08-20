package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BildirimSablonuDto {

    private Long id;
    private String cinsiyetKriteri;
    private Integer yasKriteri;
    private String mesajSablonu;

}
