package com.example.cartservice.repository;

import com.example.cartservice.po.SaleOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleOrderRepository extends ReactiveCrudRepository<SaleOrder, Long> {
}
