package com.example.demo.service;

import com.example.demo.dto.hasta.HastaDto;
import com.example.demo.entity.HastaEntity;
import com.example.demo.exception.AppNotFoundException;
import com.example.demo.exception.AppValidationException;
import com.example.demo.mapper.HastaMapper;
import com.example.demo.repository.HastaRepository;
import com.example.demo.service.hasta.HastaServiceImpl;
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

class HastaServiceTest {

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
        hastaDto.setAdi("Mirac");
        hastaDto.setSoyadi("Guntogar");
        hastaDto.setCinsiyet("Erkek");
        hastaDto.setDogumTarihi(OffsetDateTime.now().minusYears(30));

        hastaEntity = new HastaEntity();
        hastaEntity.setId(1L);
        hastaEntity.setAdi("Mirac");
        hastaEntity.setSoyadi("Guntogar");
        hastaEntity.setCinsiyet("Erkek");
        hastaEntity.setDogumTarihi(OffsetDateTime.now().minusYears(30));
    }

    @Test
    void testHastaOlustur() {
        when(hastaMapper.dtoToEntity(any(HastaDto.class))).thenReturn(hastaEntity);
        when(hastaRepository.save(any(HastaEntity.class))).thenReturn(hastaEntity);
        when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

        HastaDto result = hastaService.hastaOlustur(hastaDto);

        assertNotNull(result);
        assertEquals(hastaDto.getAdi(), result.getAdi());
        verify(hastaRepository, times(1)).save(hastaEntity);
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString());
    }

    @Test
    void testHastaOlustur_NullHastaDto() {
        assertThrows(AppValidationException.class, () -> hastaService.hastaOlustur(null));
        verify(hastaRepository, times(0)).save(any(HastaEntity.class));
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
    void testHastaGetirByCinsiyet_EmptyCinsiyet() {
        assertThrows(AppValidationException.class, () -> hastaService.hastaGetirByCinsiyet(""));
        verify(hastaRepository, times(0)).findByCinsiyet(anyString());
    }
    @Test
    void testHastaGetirByAdi() {
        when(hastaRepository.findByAdi("Mirac")).thenReturn(Arrays.asList(hastaEntity));
        when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

        List<HastaDto> result = hastaService.hastaGetirByAdi("Mirac");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(hastaRepository, times(1)).findByAdi("Mirac");
    }

    @Test
    void testHastaGetirByAdi_EmptyAdi() {
        assertThrows(AppValidationException.class, () -> hastaService.hastaGetirByAdi(""));
        verify(hastaRepository, times(0)).findByAdi(anyString());
    }

    @Test
    void testHastaGetirBySoyadi() {
        when(hastaRepository.findBySoyadi("Guntogar")).thenReturn(Arrays.asList(hastaEntity));
        when(hastaMapper.entityToDto(any(HastaEntity.class))).thenReturn(hastaDto);

        List<HastaDto> result = hastaService.hastaGetirBySoyadi("Guntogar");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(hastaRepository, times(1)).findBySoyadi("Guntogar");
    }

    @Test
    void testHastaGetirBySoyadi_EmptySoyadi() {
        assertThrows(AppValidationException.class, () -> hastaService.hastaGetirBySoyadi(""));
        verify(hastaRepository, times(0)).findBySoyadi(anyString());
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

        verify(hastaRepository, times(1)).delete(hastaEntity);
    }

    @Test
    void testHastaSil_NotFound() {
        when(hastaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppNotFoundException.class, () -> hastaService.hastaSil(1L));
        verify(hastaRepository, times(1)).findById(1L);
        verify(hastaRepository, times(0)).delete(any(HastaEntity.class));
    }

    @Test
    void testHastaSil_NullId() {
        assertThrows(AppValidationException.class, () -> hastaService.hastaSil(null));
        verify(hastaRepository, times(0)).findById(anyLong());
        verify(hastaRepository, times(0)).delete(any(HastaEntity.class));
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

    @Test
    void testHastaGuncelle_NotFound() {
        when(hastaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppValidationException.class, () -> hastaService.hastaGuncelle(hastaDto));
        verify(hastaRepository, times(1)).findById(1L);
        verify(hastaRepository, times(0)).save(any(HastaEntity.class));
    }

    @Test
    void testHastaGuncelle_NullHastaDto() {
        assertThrows(AppValidationException.class, () -> hastaService.hastaGuncelle(null));
        verify(hastaRepository, times(0)).findById(anyLong());
        verify(hastaRepository, times(0)).save(any(HastaEntity.class));
    }

    @Test
    void testHastaGuncelle_NullHastaDtoId() {
        hastaDto.setId(null);
        assertThrows(AppValidationException.class, () -> hastaService.hastaGuncelle(hastaDto));
        verify(hastaRepository, times(0)).findById(anyLong());
        verify(hastaRepository, times(0)).save(any(HastaEntity.class));
    }
}

