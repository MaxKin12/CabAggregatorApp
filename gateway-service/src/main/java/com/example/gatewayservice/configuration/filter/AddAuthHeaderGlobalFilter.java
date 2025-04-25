package com.example.gatewayservice.configuration.filter;

import static com.example.gatewayservice.utility.constants.LogMessagesTemplate.USER_ID_HEADER_ADDED;
import static com.example.gatewayservice.utility.constants.LogMessagesTemplate.USER_ID_HEADER_SKIPPED;
import static com.example.gatewayservice.utility.constants.UtilConstants.USER_ID_HEADER_NAME;

import com.example.gatewayservice.configuration.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddAuthHeaderGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String subject = JwtUtils.extractSubjectFromRequest(exchange.getRequest());
        if (subject != null) {
            ServerHttpRequest modifiedRequest = exchange
                    .getRequest()
                    .mutate()
                    .header(USER_ID_HEADER_NAME, subject)
                    .build();

            log.info(USER_ID_HEADER_ADDED, subject);
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        }
        log.info(USER_ID_HEADER_SKIPPED);
        return chain.filter(exchange);
    }

}
