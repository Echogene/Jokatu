package jokatu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static String serialise(Object object) throws JsonProcessingException {
		return OBJECT_MAPPER.writeValueAsString(object);
	}
}
