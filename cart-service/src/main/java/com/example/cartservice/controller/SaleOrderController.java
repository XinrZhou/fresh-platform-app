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
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
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
                    List<Long> cartIds = extractCartIdsFromAddressSpec(savedOrder.getAddressSpec());
                    // Update cart status
                    return cartService.updateStatus(cartIds)
                            .thenReturn(ResultVO.success(Map.of())); // Return success response
                });
    }

    // Method to extract cart IDs from addressSpec JSON string
    private List<Long> extractCartIdsFromAddressSpec(String addressSpec) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> cartIds = new ArrayList<>();
        try {
            // 使用 TypeFactory 构建 TypeReference
            TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<>() {};
            List<Map<String, Object>> addressSpecList = objectMapper.readValue(addressSpec, typeReference);

            // 遍历解析得到的列表，提取购物车ID
            for (Map<String, Object> addressSpecItem : addressSpecList) {
                Long cartId = (Long) addressSpecItem.get("cartId");
                if (cartId != null) {
                    cartIds.add(cartId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartIds;
    }

}
