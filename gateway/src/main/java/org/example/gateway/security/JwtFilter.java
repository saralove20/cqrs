package org.example.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.web.server.ServerWebExchange;

@RequiredArgsConstructor
@Component
@Slf4j
// Spring Cloud Gateway에서 JWT 인증을 처리하는 필터
// Gateway에서 쿠키에 있는 JWT를 검사 -> 유저 정보를 꺼냄 -> 내부 서비스로 전달하는 역할
public class JwtFilter extends AbstractGatewayFilterFactory {
    private final JwtUtil jwtUtil;

    @Override
    // gateway에서 요청이 들어올 때 실행되는 필터 로직
    public GatewayFilter apply(Object config) {
        return((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpCookie cookie = request.getCookies().getFirst("ATOKEN");    // 쿠키에서 JWT 가져오기

            if (cookie == null) {
                log.error("토큰 없음");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }

            String token = cookie.getValue();

            Long userIdx = jwtUtil.getUserIdx(token);
            String userName = jwtUtil.getUsername(token);

            // 헤더에 유저 정보 추가
            ServerHttpRequest newRequest = exchange.getRequest().mutate()
                    .header("X-User-Idx", ""+userIdx)
                    .header("X-User-Name", ""+userName)
                    .build();

            // 요청을 새로운 request로 교체함
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

            // 새로운 요청을 다음 서비스로 전달
            return chain.filter(newExchange);
        });
    }
}
