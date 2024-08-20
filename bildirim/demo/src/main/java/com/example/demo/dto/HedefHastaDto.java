package com.example.demo.dto;

import com.example.demo.entity.BildirimSablonuEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HedefHastaDto {

    private Long id;

    private Long hastaId;

    private Boolean bildirildi;

    private BildirimSablonuEntity bildirimSablonu;

}
