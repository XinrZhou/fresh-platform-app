package com.example.userservice.repository;

import com.example.userservice.po.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {
}
