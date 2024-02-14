package com.example.demo.controller;

import com.example.demo.dto.product.ProductAddDto;
import com.example.demo.dto.product.ProductResultDto;
import com.example.demo.dto.product.ProductUpdateDto;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductResultDto productAdd(@RequestBody ProductAddDto productAddDto) {
        return productService.productAdd(productAddDto);
    }

    @PostMapping("/{id}")
    public ProductResultDto productUpdate(@RequestBody ProductUpdateDto productUpdateDto) {
        return productService.productUpdate(productUpdateDto);
    }

    @GetMapping("/getAll")
    public List<ProductResultDto> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/getAllByUserId/{id}")
    public List<ProductResultDto> getAllByUserId(@PathVariable(name = "id") Integer id) {
        return productService.getProductsByUserId(id);
    }

    @PostMapping("/calculateTax/{id}")
    public Double calculateTax(@PathVariable Integer id) {
        return productService.calculateTax(id);
    }
    @DeleteMapping("/{id}")
    public ProductResultDto deleteProduct(@PathVariable(name = "id") Integer id) {
        return productService.deleteProduct(id);
    }
}
