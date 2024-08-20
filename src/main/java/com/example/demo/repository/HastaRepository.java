package com.example.demo.repository;

import com.example.demo.entity.HastaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface HastaRepository extends JpaRepository<HastaEntity, Long> {
    List<HastaEntity> findByAdiAndSoyadi(String adi, String soyadi);
    List<HastaEntity> findByDogumTarihiBetween(OffsetDateTime startDate, OffsetDateTime endDate);

    @Query(value = "SELECT * FROM hasta WHERE adi = :adi", nativeQuery = true)
    List<HastaEntity> findByAdi(String adi);

    @Query(value = "SELECT * FROM hasta WHERE soyadi = :soyadi", nativeQuery = true)
    List<HastaEntity> findBySoyadi(String soyadi);

    @Query(value = "SELECT * FROM hasta WHERE cinsiyet = :cinsiyet", nativeQuery = true)
    List<HastaEntity> findByCinsiyet(String cinsiyet);



}
