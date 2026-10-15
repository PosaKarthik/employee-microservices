package com.ag.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class ApiKeyGatewayFilterFactory extends AbstractGatewayFilterFactory<ApiKeyGatewayFilterFactory.Config> {

	private final Environment environment;

	public ApiKeyGatewayFilterFactory(Environment environment) {
		super(Config.class);
		this.environment = environment;
	}

	@Override
	public List<String> shortcutFieldOrder(){
		return List.of("apiKey");
	}

	@Override
	public GatewayFilter apply(Config config) {

		String resolvedApiKey = environment.getProperty("API_SECURITY_TOKEN");

		return (exchange, chain) -> {

			ServerHttpRequest serverHttpRequest = exchange.getRequest();

			String apiKey = serverHttpRequest.getHeaders().getFirst("X-API-KEY");

			log.debug("API key filter called");

			if(apiKey == null || !apiKey.equals(resolvedApiKey)) {

				log.warn("Invalid or missing API key");


				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

				return exchange.getResponse().setComplete();

			}

			return chain.filter(exchange);
		};
	}

	@Getter
	@Setter
	public static class Config {

		private String apiKey;


	}

}
