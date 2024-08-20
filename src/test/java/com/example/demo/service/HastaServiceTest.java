package com.example.demo.service;

import com.example.demo.dto.hasta.HastaDto;
import com.example.demo.entity.HastaEntity;
import com.example.demo.exception.AppNotFoundException;
import com.example.demo.exception.AppValidationException;
import com.example.demo.mapper.HastaMapper;
import com.example.demo.repository.HastaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 class HastaServiceImpl {

     @Mock
     private HastaRepository hastaRepository;

     @Mock
     private HastaMapper hastaMapper;

     @Mock
     private RabbitTemplate rabbitTemplate;

     @InjectMocks
     private HastaServiceImpl hastaService;

     private HastaDto hastaDto;
     private HastaEntity hastaEntity;

     @BeforeEach
     void setUp() {
         MockitoAnnotations.openMocks(this);

         hastaDto = new HastaDto();
         hastaDto.setId(1L);
         hastaDto.setAdi("John");
         hastaDto.setSoyadi("Doe");
         hastaDto.setCinsiyet("Erkek");
         hastaDto.setDogumTarihi(OffsetDateTime.now().minusYears(30));

         hastaEntity = new HastaEntity();
         hastaEntity.setId(1L);
         hastaEntity.setAdi("John");
         hastaEntity.setSoyadi("Doe");
         hastaEntity.setCinsiyet("Erkek");
         hastaEntity.setDogumTarihi(OffsetDateTime.now().minusYears(30));
     }

     @Test
     void testHastaOlustur() {
         when(hastaMapper.dtoToEntity(any(HastaDto.class))).thenReturn(hastaEntity);
         when(hastaRepository.save(any(HastaEntity.class))).thenReturn(hastaEntity);
         when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

         HastaDto result = hastaService.has(hastaDto);

         assertNotNull(result);
         assertEquals(hastaDto.getAdi(), result.getAdi());
         verify(hastaRepository, times(1)).save(hastaEntity);
         verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString());
     }

     @Test
     void testHastaGetirById() {
         when(hastaRepository.findById(1L)).thenReturn(Optional.of(hastaEntity));
         when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

         HastaDto result = hastaService.hastaGetirById(1L);

         assertNotNull(result);
         assertEquals(hastaDto.getId(), result.getId());
         verify(hastaRepository, times(1)).findById(1L);
     }

     @Test
     void testHastaGetirById_NotFound() {
         when(hastaRepository.findById(1L)).thenReturn(Optional.empty());

         assertThrows(AppNotFoundException.class, () -> hastaService.hastaGetirById(1L));
         verify(hastaRepository, times(1)).findById(1L);
     }

     @Test
     void testHastaGetirByCinsiyet() {
         when(hastaRepository.findByCinsiyet("Erkek")).thenReturn(Arrays.asList(hastaEntity));
         when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

         List<HastaDto> result = hastaService.hastaGetirByCinsiyet("Erkek");

         assertNotNull(result);
         assertEquals(1, result.size());
         verify(hastaRepository, times(1)).findByCinsiyet("Erkek");
     }

     @Test
     void testHastaGetirByAdi() {
         when(hastaRepository.findByAdi("John")).thenReturn(Arrays.asList(hastaEntity));
         when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

         List<HastaDto> result = hastaService.hastaGetirByAdi("John");

         assertNotNull(result);
         assertEquals(1, result.size());
         verify(hastaRepository, times(1)).findByAdi("John");
     }

     @Test
     void testHastaGetirBySoyadi() {
         when(hastaRepository.findBySoyadi("Doe")).thenReturn(Arrays.asList(hastaEntity));
         when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

         List<HastaDto> result = hastaService.hastaGetirBySoyadi("Doe");

         assertNotNull(result);
         assertEquals(1, result.size());
         verify(hastaRepository, times(1)).findBySoyadi("Doe");
     }

     @Test
     void testTumHastalariGetir() {
         when(hastaRepository.findAll()).thenReturn(Arrays.asList(hastaEntity));
         when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

         List<HastaDto> result = hastaService.tumHastalariGetir();

         assertNotNull(result);
         assertEquals(1, result.size());
         verify(hastaRepository, times(1)).findAll();
     }

     @Test
     void testHastaSil() {
         when(hastaRepository.findById(1L)).thenReturn(Optional.of(hastaEntity));

         HastaDto result = hastaService.hastaSil(1L);

         assertNotNull(result);
         verify(hastaRepository, times(1)).delete(hastaEntity);
     }

     @Test
     void testHastaGuncelle() {
         when(hastaRepository.findById(1L)).thenReturn(Optional.of(hastaEntity));
         when(hastaMapper.dtoToEntity(any(HastaDto.class))).thenReturn(hastaEntity);
         when(hastaRepository.save(any(HastaEntity.class))).thenReturn(hastaEntity);
         when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

         HastaDto result = hastaService.hastaGuncelle(hastaDto);

         assertNotNull(result);
         assertEquals(hastaDto.getAdi(), result.getAdi());
         verify(hastaRepository, times(1)).save(hastaEntity);
         verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString());
     }
}
