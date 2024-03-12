package com.example.productservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long categoryId;
    private String categoryIds;
    public String categoryName;
    // 是否为数字类型 0否 1是
    private Integer isNumeric;
    private String unit;
    // 是否为sku通用属性 0否 1是
    private Integer isGeneric;
    private String value;
}
