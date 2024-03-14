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
    private final SkuRepository skuRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    final int DEFAULT = 1;

    public Mono<List<SpuDTO>> listSpus(long cid) {
        return spuRepository.findByCategoryId(cid)
                .flatMap(spu -> skuRepository.findFirstByIsDefault(DEFAULT)
                        .map(sku -> SpuDTO.builder()
                                .id(spu.getId())
                                .name(spu.getName())
                                .imageUrl(spu.getImageUrl())
                                .tags(spu.getTags())
                                .specialSpec(spu.getSpecialSpec())
                                .genericSpec(spu.getGenericSpec())
                                .defaultSku(JSON.toJSONString(sku))
                                .build()))
                .collectList();
    }

    public Mono<SpuDTO> getSpu(long sid) {
        return spuRepository.findById(sid)
                .flatMap(spu -> skuRepository.findFirstByIsDefault(DEFAULT)
                        .map(sku -> SpuDTO.builder()
                                .id(spu.getId())
                                .name(spu.getName())
                                .imageUrl(spu.getImageUrl())
                                .detailImageUrl(spu.getDetailImageUrl())
                                .defaultSku(JSON.toJSONString(sku))
                                .description(spu.getDescription())
                                .tags(spu.getTags())
                                .genericSpec(spu.getGenericSpec())
                                .specialSpec(spu.getSpecialSpec())
                                .build()));
    }

//    private Mono<SpuDTO> mapSpuToSpuDTO(Spu spu) {
//        Mono<Category> categoryMono = categoryRepository.findById(spu.getCategoryId());
//        Mono<Brand> brandMono = spu.getBrandId() != null ?
//                brandRepository.findById(spu.getBrandId()) :
//                Mono.just(Brand.builder().name("").build());
//
//        return Mono.zip(categoryMono, brandMono)
//                .map(tuple -> {
//                    Category category = tuple.getT1();
//                    Brand brand = tuple.getT2();
//                    return SpuDTO.builder()
//                            .id(spu.getId())
//                            .name(spu.getName())
//                            .categoryId(spu.getCategoryId())
//                            .categoryName(category.getName())
//                            .brandId(spu.getBrandId())
//                            .brandName(brand.getName())
//                            .imageUrl(spu.getImageUrl())
//                            .genericSpec(spu.getGenericSpec())
//                            .detailImageUrl(spu.getDetailImageUrl())
//                            .saleStatus(spu.getSaleStatus())
//                            .build();
//                });
//    }
//
//    public Mono<List<SpuDTO>> listSpus(int page, int pageSize) {
//        return spuRepository.findAll((page - 1) * pageSize, pageSize)
//                .flatMap(this::mapSpuToSpuDTO)
//                .collectList();
//    }
//
//    public Mono<List<SpuDTO>> listSpus(long cid) {
//        return categoryRepository.findAll().collectList()
//                .flatMap(categories -> {
//                    List<Long> subcategoryIds = new ArrayList<>();
//                    collectSubcategoryIds(categories, cid, subcategoryIds);
//                    return spuRepository.findByCategoryIdIn(subcategoryIds)
//                            .map(spu -> SpuDTO.builder()
//                                    .id(spu.getId())
//                                    .name(spu.getName())
//                                    .build())
//                            .collectList();
//                });
//    }
//
//    // 递归获取类目的所有子类目ID
//    private void collectSubcategoryIds(List<Category> categories, long cid, List<Long> subcategoryIds) {
//        for (Category category : categories) {
//            if (category.getParentId() == cid) {
//                subcategoryIds.add(category.getId());
//                collectSubcategoryIds(categories, category.getId(), subcategoryIds);
//            }
//        }
//    }

}