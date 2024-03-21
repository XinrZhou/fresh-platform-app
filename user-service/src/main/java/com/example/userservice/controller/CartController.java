package com.example.userservice.controller;

import com.example.common.vo.ResultVO;
import com.example.userservice.po.Cart;
import com.example.userservice.service.CartService;
import com.example.userservice.utils.DecodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/carts")
    public Mono<ResultVO> postCart(@RequestBody Cart cart) {
        return cartService.addCart(cart).map(cart1 -> ResultVO.success(Map.of()));
    }

    @GetMapping("/carts/{uid}")
    public Mono<ResultVO> getCarts(@PathVariable long uid) {
    }
}
