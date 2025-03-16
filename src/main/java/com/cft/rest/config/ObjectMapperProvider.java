package com.cft.rest.config;

import static com.fasterxml.jackson.databind.MapperFeature.ALLOW_COERCION_OF_SCALARS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.ws.rs.ext.ContextResolver;

public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

	
	
	private final ObjectMapper objectMapper;
	
	
	public ObjectMapperProvider() {
		this.objectMapper = JsonMapper.builder().disable(ALLOW_COERCION_OF_SCALARS).build();
		this.objectMapper.registerModule(new JavaTimeModule());
		this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return this.objectMapper;
	}
	
}
