package io.github.anominy.uwiuliia;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import io.github.anominy.uwutils.UwMap;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * An abstract letter-map deserializer.
 */
@SuppressWarnings({"unchecked", "unused"})
abstract class AbstractLetterMapJsonDeserializer implements JsonDeserializer<Map<String, String>> {

	/**
	 * Initialize an {@link AbstractLetterMapJsonDeserializer} instance.
	 */
	protected AbstractLetterMapJsonDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Map<String, String> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		Map<String, String> map = context.deserialize(json, ConcurrentMap.class);

		if (map == null) {
			return UwMap.EMPTY;
		}

		extendLetterMap(map);

		return Collections.unmodifiableMap(map);
	}

	/**
	 * Extend provided letter-map w/ a custom rule-set.
	 *
	 * @param map	letter-map
	 */
	protected abstract void extendLetterMap(Map<String, String> map);
}
