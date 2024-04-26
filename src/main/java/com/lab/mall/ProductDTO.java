package com.lab.mall;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.ErrorResponse;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class ProductDTO {

    private long productCode;
    private String productName;
    private int price;
    private String description;
    private String filename;
    private MultipartFile fi1e;


}
