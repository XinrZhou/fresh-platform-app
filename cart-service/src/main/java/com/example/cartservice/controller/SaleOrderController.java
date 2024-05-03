package com.example.cartservice.controller;

import com.example.cartservice.po.SaleOrder;
import com.example.cartservice.service.CartService;
import com.example.cartservice.service.SaleOrderService;
import com.example.common.vo.ResultVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class SaleOrderController {
    private final SaleOrderService saleOrderService;
    private final CartService cartService;

    @PostMapping("/orders")
    public Mono<ResultVO> postOrder(@RequestBody SaleOrder saleOrder) {
        return saleOrderService.addOrder(saleOrder)
                .flatMap(savedOrder -> {
                    // Extract cart IDs from the addressSpec JSON string
                    List<Long> cartIds = extractCartIdsFromAddressSpec(savedOrder.getOrderSpec());
                    // Update cart status
                    return cartService.updateStatus(cartIds)
                            .then(Mono.just(ResultVO.success(Map.of())));
                });
    }


    // Method to extract cart IDs from addressSpec JSON string
    private List<Long> extractCartIdsFromAddressSpec(String orderSpec) {
        if (orderSpec == null || orderSpec.isEmpty()) {
            return Collections.emptyList();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> cartIds = new ArrayList<>();
        try {
            TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<>() {};
            List<Map<String, Object>> orderSpecList = objectMapper.readValue(orderSpec, typeReference);

            for (Map<String, Object> addressSpecItem : orderSpecList) {
                Object idObject = addressSpecItem.get("id");
                if (idObject != null) {
                    if (idObject instanceof Long) {
                        cartIds.add((Long) idObject);
                    } else if (idObject instanceof String) {
                        try {
                            cartIds.add(Long.parseLong((String) idObject));
                        } catch (NumberFormatException e) {
                            // 处理无法转换为Long的情况，例如记录日志或抛出异常
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 处理异常，例如记录日志或者抛出自定义异常
        }
        return cartIds;
    }
}
