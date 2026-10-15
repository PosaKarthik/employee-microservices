package com.ag.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered{


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest httpServerRequest=exchange.getRequest();

		log.info("Incoming Request - Method: {}, URI: {}",
				httpServerRequest.getMethod(),httpServerRequest.getURI());

		log.info("Path: {}",httpServerRequest.getPath());

		log.info("Host: {}",httpServerRequest.getHeaders().getHost());

		log.info("Client IP: {}",httpServerRequest.getRemoteAddress());

		long startTime=System.currentTimeMillis();


		return chain.filter(exchange).then(Mono.fromRunnable( () -> {

			ServerHttpResponse httpServerResponse=exchange.getResponse();

			long endTime=System.currentTimeMillis();

			log.info("Outgoing Response - Status: {}, Execution Time: {} ms",
					httpServerResponse.getStatusCode(),endTime-startTime);
		}));
	}

	@Override
	public int getOrder() {
		return 0;
	}



}
