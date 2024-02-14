package com.example.demo.service;

import com.example.demo.dto.product.ProductAddDto;
import com.example.demo.dto.product.ProductResultDto;
import com.example.demo.dto.product.ProductUpdateDto;

import java.util.List;

public interface ProductService {
    ProductResultDto productAdd(ProductAddDto productAddDto);

    ProductResultDto productUpdate(ProductUpdateDto productUpdateDto);

    List<ProductResultDto> getAllProduct();

    ProductResultDto deleteProduct(Integer id);

    List<ProductResultDto> getProductsByUserId(Integer userId);

    Double calculateTax(Integer id);
}
