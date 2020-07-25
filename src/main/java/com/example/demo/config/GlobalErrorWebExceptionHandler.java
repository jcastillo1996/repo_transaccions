package com.example.demo.config;

import static org.springframework.boot.web.error.ErrorAttributeOptions.defaults;
import static org.springframework.boot.web.error.ErrorAttributeOptions.of;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
	 public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
		        ResourceProperties resourceProperties,
		        ApplicationContext applicationContext,
		        ServerCodecConfigurer codecConfigurer) {
		        super(errorAttributes, resourceProperties, applicationContext);
		        this.setMessageWriters(codecConfigurer.getWriters());
		    }
	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
		String query = request.uri().getQuery();
		ErrorAttributeOptions errorAttributeOptions = isTraceEnabled(query) ? of(Include.STACK_TRACE) : defaults();
		final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, errorAttributeOptions);
		Integer status = (int) Optional.ofNullable(errorPropertiesMap.get("status")).orElse(500);
		return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(errorPropertiesMap));
	}

	private boolean isTraceEnabled(String query) {
		return !StringUtils.isEmpty(query) && query.contains("trace=true");
	}
}
