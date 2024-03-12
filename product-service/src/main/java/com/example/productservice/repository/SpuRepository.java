package com.example.productservice.repository;

import com.example.productservice.po.Spu;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface SpuRepository extends ReactiveCrudRepository<Spu, Long> {
    @Query("select * from spu order by update_time desc limit :pageSize offset :offset")
    Flux<Spu> findAll(int offset, int pageSize);

    Flux<Spu> findByCategoryId(long cid);

    Flux<Spu> findByCategoryIdIn(List<Long> cids);
}
