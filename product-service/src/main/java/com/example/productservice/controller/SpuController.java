package com.example.productservice.controller;

import com.example.common.vo.ResultVO;
import com.example.productservice.dto.SpuDTO;
import com.example.productservice.po.Spu;
import com.example.productservice.service.SkuService;
import com.example.productservice.service.SpuService;
import com.example.productservice.utils.DecodeUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Api(tags = "商品Spu接口")
@RestController
@RequestMapping("/spu")
@RequiredArgsConstructor
public class SpuController {
    private final SpuService spuService;
    private final SkuService skuService;

    @GetMapping("/spus/{cid}")
    public Mono<ResultVO> getSpus(@PathVariable long cid) {
        return spuService.listSpus(cid)
                .map(spuDTOs -> ResultVO.success(Map.of("spus", spuDTOs)));
    }
}
