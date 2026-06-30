package com.ag.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter,Ordered{
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		String token=exchange.getRequest().getHeaders().getFirst("token");
		
		if(token==null || !token.equals("Karthik123")) {
			 exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			 
			 return exchange.getResponse().setComplete();
		}
		
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		
		return 1;
	}

	

}
