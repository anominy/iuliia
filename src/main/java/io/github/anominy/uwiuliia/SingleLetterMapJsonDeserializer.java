package io.github.anominy.uwiuliia;

import io.github.anominy.uwutils.UwString;

import java.util.Map;

/**
 * A single letter-map deserializer.
 */
@SuppressWarnings("unused")
final class SingleLetterMapJsonDeserializer extends AbstractLetterMapJsonDeserializer {

	/**
	 * Initialize a {@link SingleLetterMapJsonDeserializer} instance.
	 */
	public SingleLetterMapJsonDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void extendLetterMap(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();

			String capitalizedKey = UwString.capitalizeOrNull(key);
			String capitalizedValue = UwString.capitalizeOrNull(val);

			map.put(capitalizedKey, capitalizedValue);
		}
	}
}
