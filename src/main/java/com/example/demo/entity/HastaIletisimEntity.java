package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hasta_iletisim")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HastaIletisimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private HastaEntity hasta;

    @Column(nullable = false)
    private String iletisimTuru;

    @Column(nullable = false)
    private String iletisimDegeri;

    @Column(nullable = true)
    private Integer surum;

}
