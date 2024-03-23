package com.example.cartservice.service;

import com.example.cartservice.po.SaleOrder;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.repository.SaleOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class SaleOrderService {
    private final SaleOrderRepository saleOrderRepository;

    @Transactional
    public Mono<SaleOrder> addOrder(SaleOrder saleOrder) {
        System.out.println(saleOrder);
        return saleOrderRepository.save(saleOrder);
    }
}
