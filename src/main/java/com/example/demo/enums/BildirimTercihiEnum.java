package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum BildirimTercihiEnum {

    SMS("1","Sms"),

    EMAIL("2","E-mail"),

    HICBIRI("3","Hiçbiri");

    private final String kodu;

    private final String aciklama;
    BildirimTercihiEnum(String kodu, String aciklama) {
        this.kodu = kodu;
        this.aciklama = aciklama;
    }

}
