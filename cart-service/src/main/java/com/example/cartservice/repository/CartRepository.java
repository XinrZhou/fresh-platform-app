package com.example.cartservice.repository;

import com.example.cartservice.po.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {
    Flux<Cart> getCartByUserIdAndType(long uid, int type);
}
