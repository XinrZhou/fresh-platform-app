package com.example.productservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpuDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long categoryId;
    private String categoryName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long brandId;
    private String brandName;
    private String imageUrl;
    private String detailImageUrl;
    private String defaultSku;
    // 是否上架 0否 1是
    private Integer saleStatus;
    private String description;
    // json
    private String tags;
    // json
    private String genericSpec;
    // json
    private String specialSpec;
}
