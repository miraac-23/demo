package com.example.demo.service.hasta;

import com.example.demo.dto.hasta.HastaKimlikDto;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppValidationException;
import com.example.demo.mapper.HastaKimlikMapper;
import com.example.demo.repository.HastaKimlikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class HastaKimlikServiceImpl implements HastaKimlikService{

    @Autowired
    private HastaKimlikRepository hastaKimlikRepository;

    private HastaKimlikMapper hastaKimlikMapper;

    @Autowired
    public void setHastaKimlikMapper(HastaKimlikMapper hastaKimlikMapper) {
        this.hastaKimlikMapper = hastaKimlikMapper;
    }

    @Override
    public List<HastaKimlikDto> hastaKimlikOlustur(List<HastaKimlikDto> hastaKimlikDtoList) {

        try {
            if (CollectionUtils.isEmpty(hastaKimlikDtoList)) {
                throw new AppValidationException("Hasta kimlik bilgileri bulunamadı");
            }

            for (HastaKimlikDto hastaKimliikDto: hastaKimlikDtoList) {

                hastaKimlikRepository.save(hastaKimlikMapper.dtoToEntity(hastaKimliikDto));

            }

            return hastaKimlikDtoList;

        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

    }
}
