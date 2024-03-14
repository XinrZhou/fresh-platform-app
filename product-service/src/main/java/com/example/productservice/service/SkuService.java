package com.example.productservice.service;

import com.example.productservice.po.Sku;
import com.example.productservice.repository.SkuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkuService {
    private final SkuRepository skuRepository;

    public Mono<List<Sku>> listSkus(long sid) {
        return skuRepository.findBySpuId(sid).collectList();
    }
}
