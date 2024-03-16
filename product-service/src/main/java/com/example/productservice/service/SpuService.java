package com.example.productservice.service;

import com.alibaba.fastjson.JSON;
import com.example.productservice.dto.SpuDTO;
import com.example.productservice.po.Brand;
import com.example.productservice.po.Category;
import com.example.productservice.po.Spu;
import com.example.productservice.repository.BrandRepository;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.SkuRepository;
import com.example.productservice.repository.SpuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpuService {
    private final SpuRepository spuRepository;

    public Mono<List<Spu>> listSpus(long cid) {
        return spuRepository.findByCategoryId(cid).collectList();
    }
}