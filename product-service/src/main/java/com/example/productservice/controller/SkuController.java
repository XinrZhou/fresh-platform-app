package com.example.productservice.controller;

import com.example.common.vo.ResultVO;
import com.example.productservice.dto.SkuDTO;
import com.example.productservice.po.Sku;
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

    @GetMapping("/skus/{cid}")
    public Mono<ResultVO> getSkus(@PathVariable long cid) {
        return skuService.listSkus(cid)
                .map(skuList-> ResultVO.success(Map.of("skus", skuList)));
    }

    @GetMapping("/detail/{sid}")
    public Mono<ResultVO> getSkuDTO(@PathVariable long sid) {
        return skuService.getSkuDTO(sid)
                .flatMap(skuDTO -> skuService.listSku(sid)
                        .map(skus ->  ResultVO.success(Map.of("skus", skuDTO , "skuList", skus))));
    }

    @GetMapping("/sku/{sid}")
    public Mono<Sku> getSku(@PathVariable long sid) {
        return skuService.getSku(sid);
    }
}
