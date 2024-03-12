package com.example.productservice.controller;

import com.example.productservice.service.SkuService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "商品Sku接口")
@RestController
@RequestMapping("/sku")
@RequiredArgsConstructor
public class SkuController {
    private final SkuService skuService;
}
