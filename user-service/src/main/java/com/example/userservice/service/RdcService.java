package com.example.userservice.service;


import com.example.userservice.po.Rdc;
import com.example.userservice.repository.RdcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RdcService {
    private final RdcRepository rdcRepository;

    public Mono<Rdc> addRdc(Rdc rdc) {
        return rdcRepository.save(rdc);
    }

    public Mono<List<Rdc>> listRdcs(int page, int pageSize) {
        return rdcRepository.findAll((page -1) * pageSize, pageSize).collectList();
    }

    public Mono<List<Rdc>> listRdcs() {
        return rdcRepository.findAll().collectList();
    }

    public Mono<Integer> getRdcCount() {
        return rdcRepository.findCount();
    }

    public Mono<Rdc> getRdc(Long rid) {
        return rdcRepository.findById(rid);
    }

    public Mono<Void> deleteRdc(Long rid) {
        return rdcRepository.deleteById(rid).then();
    }

}
