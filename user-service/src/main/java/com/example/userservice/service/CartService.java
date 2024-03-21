package com.example.userservice.service;

import com.example.userservice.po.Cart;
import com.example.userservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    @Transactional
    public Mono<Cart> addCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
