package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "hedef_hasta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HedefHastaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long hastaId;


    @Column(nullable = false)
    private Boolean bildirildi;

    @ManyToOne
    @JoinColumn(name = "bildirim_sablonu_id", nullable = false)
    private BildirimSablonuEntity bildirimSablonu;

}
