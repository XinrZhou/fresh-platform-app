package com.example.productservice.service;

import com.example.productservice.dto.SkuDTO;
import com.example.productservice.po.Sku;
import com.example.productservice.repository.SkuRepository;
import com.example.productservice.repository.SpuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
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

//    public Mono<List<SkuDTO>> listSkus(int page, int pageSize) {
//        return skuRepository.findAll((page - 1) * pageSize, pageSize).collectList()
//                .flatMap(skus -> Flux.fromIterable(skus)
//                        .flatMap(sku -> spuRepository.findById(sku.getSpuId())
//                                .map(spu -> SkuDTO.builder()
//                                        .id(sku.getId())
//                                        .name(sku.getName())
//                                        .spuId(sku.getSpuId())
//                                        .spuName(spu.getName())
//                                        .stock(sku.getStock())
//                                        .enable(sku.getEnable())
//                                        .originPrice(sku.getOriginPrice())
//                                        .discountPrice(sku.getDiscountPrice())
//                                        .build()))
//                        .collectList()
//                );
//    }
}
