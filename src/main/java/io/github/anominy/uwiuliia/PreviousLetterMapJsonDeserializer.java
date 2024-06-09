package io.github.anominy.uwiuliia;

import io.github.anominy.uwutils.UwString;

import java.util.Map;

/**
 * A previous letter-map deserializer.
 */
@SuppressWarnings("unused")
final class PreviousLetterMapJsonDeserializer extends AbstractLetterMapJsonDeserializer {

	/**
	 * Initialize a {@link PreviousLetterMapJsonDeserializer} instance.
	 */
	public PreviousLetterMapJsonDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void extendLetterMap(Map<String, String> map) {
		//noinspection DuplicatedCode
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();

			String capitalizedKey = UwString.capitalizeOrNull(key);
			String capitalizedValue = UwString.capitalizeOrNull(val);

			map.put(capitalizedKey, val);
			map.put(key.toUpperCase(), capitalizedValue);
		}
	}
}
