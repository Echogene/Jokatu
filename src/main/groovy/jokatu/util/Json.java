package jokatu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for handling JSON.
 */
public class Json {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@SuppressWarnings("unused") // This is called from the Groovy Templates
	public static String serialise(Object object) throws JsonProcessingException {
		return OBJECT_MAPPER.writeValueAsString(object);
	}

	public static String serialiseAndEscape(Object object) throws JsonProcessingException {
		return serialise(object).replaceAll("'", "&#39;");
	}

	public static Map<String, Object> deserialise(String json) throws IOException {
		MapType typeRef = OBJECT_MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
		return OBJECT_MAPPER.readValue(json, typeRef);
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
