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
public class SkuDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String spuName;
    private String name;
    private String imageUrl;
    private String detailImageUrl;
    private Integer stock;
    private BigDecimal originPrice;
    private BigDecimal discountPrice;
    private String unit;
    private String description;
    // 是否有效 0无效 1有效
    private Integer enable;
    // json
    private String tags;
    // json
    private String genericSpec;
    // json
    private String specialSpec;
}
