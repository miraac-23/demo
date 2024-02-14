package com.example.demo.dto.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductUpdateDto {

    private Integer id;
    private String name;
    private Double unitPrice;
    private Integer unitsInStock;
    private Integer userId;

}
