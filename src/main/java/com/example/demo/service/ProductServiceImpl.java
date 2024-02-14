package com.example.demo.service;

import com.example.demo.dto.product.ProductAddDto;
import com.example.demo.dto.product.ProductResultDto;
import com.example.demo.dto.product.ProductUpdateDto;
import com.example.demo.dto.user.UserResultDto;
import com.example.demo.entity.ProductEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppNotFoundException;
import com.example.demo.exception.AppValidationException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private UserService userService;
    private UserMapper userMapper;

    @Override
    public ProductResultDto productAdd(ProductAddDto productAddDto) {

        if (productAddDto == null) {
            throw new AppValidationException("eksik bilgi");
        }
        try {

            ProductEntity productEntity = productMapper.addDtoToEntity(productAddDto);

            UserResultDto user = userService.getUserById(productAddDto.getUserId());
            productEntity.setUser(userMapper.dtoToEntity(user));

            productRepository.save(productEntity);
            return productMapper.entityToSonucDto(productEntity);
        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

    }

    @Override
    public ProductResultDto productUpdate(ProductUpdateDto productUpdateDto) {
        if (productUpdateDto == null) {
            throw new AppValidationException("eksik bilgi");
        }
        UserResultDto user = userService.getUserById(productUpdateDto.getUserId());

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        ProductEntity productEntity = productRepository.findById(productUpdateDto.getId())
                .orElseThrow(() -> new AppNotFoundException("Ürün Bulunamadı"));

        if (!productEntity.getUser().getEmail().equals(userName)) {

            throw new AppValidationException("Yalnızca kendi ürünlerinizi güncelleyebilirsiniz");

        }

        try {
            productMapper.updateDtoToEntity(productUpdateDto, productEntity);

            productEntity.setUser(userMapper.dtoToEntity(user));

            productRepository.save(productEntity);
            return productMapper.entityToSonucDto(productEntity);

        } catch (AppException | AppValidationException | AppNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public List<ProductResultDto> getAllProduct() {

        try {
            List<ProductEntity> productResultDtoList = productRepository.findAll();
            return productResultDtoList.stream().map(productMapper::entityToResultDto).collect(Collectors.toList());

        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public ProductResultDto deleteProduct(Integer id) {
        try {
            if (id == null) {
                throw new AppValidationException("Ürün silmek için id bilgisi eksik");
            }
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();

            ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new AppNotFoundException(id + " id'sine sahip bir ürün sistemde mevcut değil"));

            if (!productEntity.getUser().getEmail().equals(userName)) {
                throw new AppValidationException("Yalnızca kendi ürünlerinizi silme yetkiniz bulunmaktadır");
            }

            productRepository.delete(productEntity);

            return productMapper.entityToSonucDto(productEntity);

        } catch (AppValidationException | AppNotFoundException | AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);

        }
    }

    @Override
    public List<ProductResultDto> getProductsByUserId(Integer userId) {

        try {
            if (userId == null) {
                throw new AppValidationException("Ürün listelemek için id bilgisi eksik");
            }
            UserResultDto user = userService.getUserById(userId);
            List<ProductEntity> productResultDtoList = productRepository.findAll();
            List<ProductEntity> result = new ArrayList<>();

            productResultDtoList.forEach(product -> {
                if (product.getUser().getEmail().equals(user.getEmail())) {
                    result.add(product);
                }
            });

            return result.stream().map(productMapper::entityToResultDto).collect(Collectors.toList());
        }catch (AppValidationException | AppNotFoundException | AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public Double calculateTax(Integer id) {
        try {
            if (id == null) {
                throw new AppValidationException("Vergi hesaplamak için id bilgisi eksik");
            }

            ProductEntity productEntity = productRepository.findById(id)
                    .orElseThrow(() -> new AppNotFoundException(id + " id'sine sahip bir ürün sistemde mevcut değil"));

            ProductResultDto productResultDto = productMapper.entityToSonucDto(productEntity);

            double tax = productResultDto.getUnitPrice() * 18 / 100;
            return  tax;
        }catch (AppValidationException | AppNotFoundException | AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
