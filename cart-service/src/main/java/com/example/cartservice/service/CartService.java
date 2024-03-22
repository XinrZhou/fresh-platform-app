package com.example.cartservice.service;

import com.example.cartservice.dto.CartDTO;
import com.example.cartservice.po.Cart;
import com.example.cartservice.repository.CartRepository;
import com.example.feignapi.client.ProductClient;
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
public class CartService {
    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Transactional
    public Mono<Cart> addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Mono<List<CartDTO>> listCarts(long uid) {
        return cartRepository.getCartByUserIdAndType(uid, Cart.UNSETTLED).collectList()
                .flatMapMany(Flux::fromIterable)
                .flatMap(cart -> productClient.getSku(cart.getSkuId())
                        .map(sku -> CartDTO.builder()
                                .id(cart.getId())
                                .userId(uid)
                                .skuId(cart.getSkuId())
                                .count(cart.getCount())
                                .unit(sku.getUnit())
                                .originPrice(sku.getOriginPrice())
                                .discountPrice(sku.getDiscountPrice())
                                .imageUrl(sku.getImageUrl())
                                .skuName(sku.getName())
                                .build())
                ).collectList();

    }
}
