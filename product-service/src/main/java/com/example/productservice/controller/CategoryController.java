package com.example.productservice.controller;


import com.example.common.vo.ResultVO;
import com.example.productservice.po.Category;
import com.example.productservice.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Api(tags = "商品类目接口")
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories/{level}")
    public Mono<ResultVO> getCategories(@PathVariable int level) {
        return categoryService.listCategories(level)
                .map(categories -> ResultVO.success(Map.of("categories", categories)));
    }

    @GetMapping("/category/{pid}")
    public Mono<ResultVO> getCategories(@PathVariable long pid) {
        return categoryService.listCategories(pid)
                .map(categories -> ResultVO.success(Map.of("categories", categories)));
    }
}
