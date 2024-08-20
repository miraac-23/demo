package com.example.demo.repository;

import com.example.demo.entity.HedefHastaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HedefHastaRepository extends JpaRepository<HedefHastaEntity, Long> {
    List<HedefHastaEntity> findByBildirimSablonuId(Long sablonId);


}
