package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "hasta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HastaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String adi;

    @Column(nullable = false)
    private String soyadi;

    @Column(nullable = false)
    private OffsetDateTime dogumTarihi;

    @Column(nullable = false)
    private String cinsiyet;

    @Column(nullable = false)
    private Integer surum;

    @Column(nullable = false)
    private OffsetDateTime olusturulmaTarihi;

    @Column(nullable = false)
    private OffsetDateTime guncellenmeTarihi;

    @OneToMany(mappedBy = "hasta", cascade = CascadeType.ALL)
    private List<HastaKimlikEntity> kimlikler;

    @OneToMany(mappedBy = "hasta", cascade = CascadeType.ALL)
    private List<HastaIletisimEntity> iletisimler;

    @OneToMany(mappedBy = "hasta", cascade = CascadeType.ALL)
    private List<BildirimTercihiEntity> bildirimTercihleri;

}
