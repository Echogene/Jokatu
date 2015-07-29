package jokatu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.IOException;

public class Json {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static String serialise(Object object) throws JsonProcessingException {
		return OBJECT_MAPPER.writeValueAsString(object);
	}

	public static class JacksonConverterFactory implements ConverterFactory<String, Object> {

		@Override
		public <T> Converter<String, T> getConverter(Class<T> targetType) {
			return source -> {
				try {
					return OBJECT_MAPPER.readValue(source.getBytes(), targetType);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			};
		}
	}
}
