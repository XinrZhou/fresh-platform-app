package com.example.cartservice.repository;

import com.example.cartservice.po.OrderSku;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSkuRepository extends ReactiveCrudRepository<OrderSku, Long> {
}
