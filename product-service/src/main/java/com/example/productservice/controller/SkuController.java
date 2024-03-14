package com.example.productservice.controller;

import com.example.common.vo.ResultVO;
import com.example.productservice.service.SkuService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@Api(tags = "商品Sku接口")
@RestController
@RequestMapping("/sku")
@RequiredArgsConstructor
public class SkuController {
    private final SkuService skuService;

    @GetMapping("/skus/{sid}")
    public Mono<ResultVO> getSkus(@PathVariable long sid) {
        return skuService.listSkus(sid)
                .map(skuList-> ResultVO.success(Map.of("skus", skuList)));
    }
}
