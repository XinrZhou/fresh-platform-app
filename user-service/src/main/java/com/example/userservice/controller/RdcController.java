package com.example.userservice.controller;

import com.example.common.vo.ResultVO;
import com.example.userservice.po.Rdc;
import com.example.userservice.service.RdcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/rdc")
@Slf4j
@RequiredArgsConstructor
public class RdcController {
    private final RdcService rdcService;


    @GetMapping("/rdcs")
    public Mono<ResultVO> getRdcs() {
        return rdcService.listRdcs()
                .map(rdcs -> ResultVO.success(Map.of("rdcs", rdcs)));
    }

    @DeleteMapping("/rdcs/{rid}")
    public Mono<ResultVO> deleteRdc(@PathVariable long rid) {
        return rdcService.deleteRdc(rid)
                .then(Mono.just(ResultVO.success(Map.of())));
    }
}
