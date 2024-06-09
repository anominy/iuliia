package io.github.anominy.uwiuliia;

import java.util.Map;

/**
 * An ending letter-map deserializer.
 */
@SuppressWarnings("unused")
final class EndingLetterMapJsonDeserializer extends AbstractLetterMapJsonDeserializer {

	/**
	 * Initialize an {@link EndingLetterMapJsonDeserializer} instance.
	 */
	public EndingLetterMapJsonDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void extendLetterMap(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();

			map.put(key.toUpperCase(), val.toUpperCase());
		}
	}
}
