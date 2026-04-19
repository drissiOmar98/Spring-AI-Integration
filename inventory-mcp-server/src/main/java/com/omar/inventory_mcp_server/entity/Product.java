package com.omar.inventory_mcp_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private Double price;
    private Integer stock;

    // Constructors
    public Product() {}

    public Product(String name,
                   String category,
                   Double price,
                   Integer stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }
}