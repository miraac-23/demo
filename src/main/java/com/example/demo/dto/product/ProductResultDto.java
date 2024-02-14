package com.example.demo.dto.product;

import com.example.demo.dto.user.UserSummaryDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductResultDto {
    private String name;
    private Double unitPrice;
    private Integer unitsInStock;
    private UserSummaryDto user;

}
