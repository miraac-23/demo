package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "units_in_stock")
    private Integer unitsInStock;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
