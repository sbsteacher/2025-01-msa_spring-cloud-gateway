package com.green.scg2.configuration.jwt;

import com.green.scg2.configuration.constants.ConstJwt;
import com.green.scg2.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements WebFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final ConstJwt constJwt;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 요청 정보
        ServerHttpRequest request = exchange.getRequest();
        Authentication authentication = jwtTokenProvider.getAuthentication(request);

        if(authentication != null) {
            try {
                //String signedUserJson = objectMapper.writeValueAsString(authentication.getPrincipal());
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                ServerHttpRequest modifiedRequest = request.mutate()
                                                            .header(constJwt.getClaimKey(), String.valueOf(userPrincipal.getSignedUserId()))
                                                            .build();

                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(modifiedRequest)
                        .build();

                SecurityContext context = new SecurityContextImpl(authentication);

                return chain.filter(modifiedExchange)
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)))                        ;
            } catch (Exception e) {
                //request.setAttribute("exception", e);
            }
        }

        return chain.filter(exchange);
    }

}
