package com.example.productservice.service;

import com.example.productservice.dto.SkuDTO;
import com.example.productservice.po.Sku;
import com.example.productservice.repository.SkuRepository;
import com.example.productservice.repository.SpuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkuService {
    private final SpuRepository spuRepository;
    private final SkuRepository skuRepository;

    public Mono<List<SkuDTO>> listSkus(long cid) {
        return spuRepository.findByCategoryId(cid)
                .flatMap(spu -> skuRepository.findBySpuId(spu.getId())
                        .map(sku -> SkuDTO.builder()
                                .id(sku.getId())
                                .spuName(spu.getName())
                                .name(sku.getName())
                                .imageUrl(sku.getImageUrl())
                                .tags(spu.getTags())
                                .specialSpec(spu.getSpecialSpec())
                                .originPrice(sku.getOriginPrice())
                                .discountPrice(sku.getDiscountPrice())
                                .unit(sku.getUnit())
                                .build()))
                .collectList();

    }

    public Mono<List<Sku>> listSku(long sid) {
        return skuRepository.findById(sid)
                .flatMap(sku -> skuRepository.findBySpuId(sku.getSpuId())
                        .collectList());
    }

    public Mono<SkuDTO> getSkuDTO(long sid) {
        return skuRepository.findById(sid)
                .flatMap(sku -> spuRepository.findById(sku.getSpuId())
                        .map(spu -> SkuDTO.builder()
                                .id(sku.getId())
                                .spuName(spu.getName())
                                .name(sku.getName())
                                .imageUrl(sku.getImageUrl())
                                .detailImageUrl(sku.getDetailImageUrl())
                                .description(sku.getDescription())
                                .unit(sku.getUnit())
                                .originPrice(sku.getOriginPrice())
                                .discountPrice(sku.getDiscountPrice())
                                .tags(spu.getTags())
                                .genericSpec(spu.getGenericSpec())
                                .specialSpec(spu.getSpecialSpec())
                                .build()));
    }

    public Mono<Sku> getSku(long sid) {
        return skuRepository.findById(sid);
    }

}
