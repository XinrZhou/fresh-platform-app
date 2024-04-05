package com.example.pageservice.controller;

import com.example.common.vo.ResultVO;
import com.example.pageservice.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/pages/{name}")
    public Mono<ResultVO> getPage(@PathVariable String name) {
        return pageService.getPage(name)
                .map(page -> ResultVO.success(Map.of("pages", page)));
    }
}
