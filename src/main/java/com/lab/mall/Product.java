package com.lab.mall;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long productCode;
    private String productName;
    private int price;

    @Lob
    private String description;
    private String filename;

    public Product(long productCode){
        this.productCode = productCode;
    }

    @Builder
    public Product(long productCode, String productName, int price, String description, String filename) {
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.filename = filename;
    }
}
