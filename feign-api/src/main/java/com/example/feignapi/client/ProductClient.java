package com.example.feignapi.client;

import com.example.feignapi.config.CustomizedConfig;
import com.example.feignapi.po.Sku;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(name = "product-service", configuration = CustomizedConfig.class)
public interface ProductClient {

    @GetMapping("/sku/sku/{sid}")
    Mono<Sku> getSku(@PathVariable long sid);

}
