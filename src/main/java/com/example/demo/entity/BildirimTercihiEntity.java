package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bildirim_tercihi")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BildirimTercihiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private HastaEntity hasta;

    @Column(nullable = false)
    private String tercihTuru;

    @Column(nullable = false)
    private Boolean aktif;

    @Column(nullable = true)
    private Integer surum;

}
