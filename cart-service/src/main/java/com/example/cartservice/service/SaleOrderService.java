package com.example.cartservice.service;

import com.example.cartservice.config.SnowFlakeGenerator;
import com.example.cartservice.po.OrderSku;
import com.example.cartservice.po.OrderSpec;
import com.example.cartservice.po.SaleOrder;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.repository.OrderSkuRepository;
import com.example.cartservice.repository.SaleOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SaleOrderService {
    private final SaleOrderRepository saleOrderRepository;
    private final OrderSkuRepository orderSkuRepository;
    private final ObjectMapper objectMapper;
    private final SnowFlakeGenerator snowFlakeGenerator;

    @Transactional
    public Mono<SaleOrder> addOrder(SaleOrder saleOrder) {
        return saleOrderRepository.save(saleOrder)
                .flatMap(savedOrder -> {
                    List<OrderSpec> orderSpecs = parseOrderSpecs(savedOrder.getOrderSpec());
                    return saveOrderSkus(savedOrder.getId(), orderSpecs)
                            .thenReturn(savedOrder);
                });
    }

    private List<OrderSpec> parseOrderSpecs(String orderSpecs) {
        try {
            return objectMapper.readValue(orderSpecs, new TypeReference<List<OrderSpec>>() {});
        } catch (IOException e) {
            log.error("Error parsing orderSpecs: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private Mono<Void> saveOrderSkus(Long orderId, List<OrderSpec> orderSpecs) {
        return Flux.fromIterable(orderSpecs)
                .map(orderSpec -> OrderSku.builder()
//                        .id(snowFlakeGenerator.getNextId())
                        .orderId(orderId)
                        .skuId(orderSpec.getSkuId())
                        .extSpec(buildExtSpec(orderSpec))
                        .build())
                .doOnNext(orderSku -> log.info("OrderSku object: {}", orderSku)) // 打印 OrderSku 对象信息
                .flatMap(orderSkuRepository::save)
                .then();
    }

    private String buildExtSpec(OrderSpec orderSpec) {
        try {
            // 创建一个新的 OrderSpec 对象，只包含 extSpec 所需的字段
            OrderSpec extSpecOrderSpec = OrderSpec.builder()
                    .id(null) // 设置为 null，因为不需要保存 id
                    .userId(orderSpec.getUserId()) // 设置为 null，因为不需要保存 userId
                    .skuId(null) // 设置为 null，因为不需要保存 skuId
                    .count(orderSpec.getCount())
                    .skuName(orderSpec.getSkuName())
                    .imageUrl(orderSpec.getImageUrl())
                    .originPrice(orderSpec.getOriginPrice())
                    .discountPrice(orderSpec.getDiscountPrice())
                    .unit(orderSpec.getUnit())
                    .build();

            // 构建 extSpec 字段的 JSON 字符串
            return objectMapper.writeValueAsString(extSpecOrderSpec);
        } catch (JsonProcessingException e) {
            log.error("Error building extSpec: {}", e.getMessage());
            return "{}"; // 返回空对象 JSON 字符串
        }
    }
}
