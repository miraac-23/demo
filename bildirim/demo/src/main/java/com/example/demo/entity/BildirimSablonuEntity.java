package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bildirim_sablonu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BildirimSablonuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String cinsiyetKriteri;

    @Column()
    private Integer yasKriteri;

    @Column()
    private String mesajSablonu;

}
