package com.example.productservice.repository;

import com.example.productservice.po.Sku;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SkuRepository extends ReactiveCrudRepository<Sku, Long> {
    @Query("select * from sku order by update_time desc limit :pageSize offset :offset")
    Flux<Sku> findAll(int offset, int pageSize);

    Flux<Sku> findBySpuId(long sid);
}
