package com.example.gatewayservice.filter;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.common.exception.Code;
import com.example.common.vo.RequestAttributeConstant;
import com.example.gatewayservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter {
    private final JwtUtils jwtUtils;
    private final ResponseHelper responseHelper;

    private final List<String> excludes = List.of(
            "/users/login",
            "/category/categories/**"
    );
    private final List<String> includes = List.of("/");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        for (String excludeP : excludes) {
            if (request.getPath().pathWithinApplication().value().equals(excludeP)) {
                return chain.filter(exchange);
            }
        }

        for (String p : includes) {
            if (!exchange.getRequest().getPath().pathWithinApplication().value().startsWith(p)) {
                return chain.filter(exchange);
            }
        }

        String token = request.getHeaders().getFirst(RequestAttributeConstant.TOKEN);
        if (token == null) {
            return responseHelper.response(Code.UNAUTHORIZED, exchange);
        }

        DecodedJWT decode = jwtUtils.decode(token);
        exchange.getResponse().getHeaders().add(RequestAttributeConstant.UID, decode.getClaim(RequestAttributeConstant.UID).asLong().toString());
        return chain.filter(exchange);
    }

}
