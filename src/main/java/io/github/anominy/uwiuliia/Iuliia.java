package io.github.anominy.uwiuliia;

import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.UwString;

/**
 * A transliteration utility.
 */
@SuppressWarnings("unused")
public final class Iuliia {

	/**
	 * A default word separator.
	 */
	private static final String DEFAULT_SEPARATOR = "\\b";

	/**
	 * A word separator format string.
	 */
	private static final String SEPARATOR_FORMAT = "((?<=%1$s)|(?=%1%s))";

	/**
	 * Transliterate text using provided word separator and mapping-schema.
	 *
	 * @param text			text to transliterate
	 * @param separator		word separator
	 * @param schema		transliteration schema
	 * @return				transliterated text or the same string
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema is {@code null}
	 */
	public static String transliterate(String text, String separator, IuliiaSchema schema) {
		if (text == null || text.isEmpty()) {
			return text;
		}

		separator = UwObject.ifNull(separator, DEFAULT_SEPARATOR);
		separator = String.format(SEPARATOR_FORMAT, separator);

		StringBuilder sb = new StringBuilder();
		for (String word : text.split(separator)) {
			sb.append(transliterateWord(word, schema));
		}

		return sb.toString();
	}

	/**
	 * Transliterate text using provided word separator and mapping-schema.
	 *
	 * <p>Wraps {@link Iuliia#transliterate(String, String, IuliiaSchema)}
	 * w/ {@link IuliiaSchema#fromFilePath(String)} as the transliteration schema.
	 *
	 * @param text			text to transliterate
	 * @param separator		word separator
	 * @param schemaPath	path to the transliteration schema file
	 * @return				transliterated text
	 *
	 * @throws IllegalArgumentException		if the mapping-schema cannot be found
	 */
	public static String transliterate(String text, String separator, String schemaPath) {
		return transliterate(text, separator, IuliiaSchema.fromFilePath(schemaPath));
	}

	/**
	 * Transliterate text using provided word separator and mapping-schema.
	 *
	 * <p>Wraps {@link Iuliia#transliterate(String, String, String)}
	 * w/ {@link EIuliiaSchema#getFilePath()} as the path to the transliteration schema file.
	 *
	 * @param text			text to transliterate
	 * @param separator		word separator, default to {@link #DEFAULT_SEPARATOR} if {@code null}
	 * @param schemaType	transliteration schema enum type
	 * @return				transliterated text
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema type is {@code null}
	 */
	public static String transliterate(String text, String separator, EIuliiaSchema schemaType) {
		return transliterate(text, separator, IuliiaSchema.fromEnumType(schemaType));
	}

	/**
	 * Transliterate text using provided mapping-schema.
	 *
	 * <p>Wraps {@link Iuliia#transliterate(String, String, IuliiaSchema)}
	 * w/ {@code null} as the word separator.
	 *
	 * @param text		text to transliterate
	 * @param schema	transliteration schema
	 * @return			transliterated text
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema type is {@code null}
	 */
	public static String transliterate(String text, IuliiaSchema schema) {
		return transliterate(text, null, schema);
	}

	/**
	 * Transliterate text using provided mapping-schema.
	 *
	 * <p>Wraps {@link Iuliia#transliterate(String, String, String)}
	 * w/ {@code null} as the word separator.
	 *
	 * @param text			text to transliterate
	 * @param schemaPath	path to the transliteration schema file
	 * @return				transliterated text
	 *
	 * @throws IllegalArgumentException		if the mapping-schema cannot be found
	 */
	public static String transliterate(String text, String schemaPath) {
		return transliterate(text, null, schemaPath);
	}

	/**
	 * Transliterate text using provided mapping-schema.
	 *
	 * <p>Wraps {@link Iuliia#transliterate(String, String, EIuliiaSchema)}
	 * w/ {@code null} as the word separator.
	 *
	 * @param text			text to transliterate
	 * @param schemaType	transliteration schema enum type
	 * @return				transliterated text
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema type is {@code null}
	 */
	public static String transliterate(String text, EIuliiaSchema schemaType) {
		return transliterate(text, null, schemaType);
	}


	/**
	 * Transliterate word using provided mapping-schema.
	 *
	 * @param word		word to transliterate
	 * @param schema	transliteration schema
	 * @return			transliterated string-word
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema is {@code null}
	 */
	private static String transliterateWord(IuliiaWord word, IuliiaSchema schema) {
		if (schema == null) {
			throw new IllegalArgumentException("Schema mustn't be <null>");
		}

		String stem = word.getStem();
		String ending = word.getEnding();

		String transliteratedEnding = transliterateEnding(ending, schema);
		if (transliteratedEnding == null) {
			return transliterateStem(stem + ending, schema);
		}

		return transliterateStem(stem, schema) + transliteratedEnding;
	}

	/**
	 * Transliterate string-word using provided mapping-schema.
	 *
	 * <p>Wraps {@link Iuliia#transliterateWord(IuliiaWord, IuliiaSchema)}
	 * w/ {@link IuliiaWord#IuliiaWord(String)} as the word.
	 *
	 * @param word		string-word to transliterate
	 * @param schema	transliteration schema
	 * @return			transliterated string-word
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema is {@code null}
	 */
	private static String transliterateWord(String word, IuliiaSchema schema) {
		return transliterateWord(new IuliiaWord(word), schema);
	}

	/**
	 * Transliterate word stem using provided mapping-schema.
	 *
	 * @param stem		word stem
	 * @param schema	transliteration schema
	 * @return			transliterated word stem
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema is {@code null}
	 */
	private static String transliterateStem(String stem, IuliiaSchema schema) {
		StringBuilder sb = new StringBuilder();

		int stemLength = stem.length();

		for (int i = 0; i < stemLength; i++) {
			Character prev = i > 0 ? stem.charAt(i - 1) : null;
			Character curr = stem.charAt(i);
			Character next = i < stemLength - 1 ? stem.charAt(i + 1) : null;

			sb.append(transliterateLetter(prev, curr, next, schema));
		}

		return sb.toString();
	}

	/**
	 * Transliterate word ending using provided mapping-schema.
	 *
	 * @param ending	word ending
	 * @param schema	transliteration schema
	 * @return			transliterated word ending
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema is {@code null}
	 */
	private static String transliterateEnding(String ending, IuliiaSchema schema) {
		if (schema == null) {
			throw new IllegalArgumentException("Schema mustn't be <null>");
		}

		return schema.getEndingLetterMap()
				.get(ending);
	}

	/**
	 * Transliterate single letter of the word using provided mapping-schema.
	 *
	 * @param prevChar	previous character, default to {@code empty} if {@code null}
	 * @param currChar	current character, default to {@code empty} if {@code null}
	 * @param nextChar	next character, default to {@code empty} if {@code null}
	 * @param schema	transliteration schema
	 * @return			transliterated single letter as a string
	 *
	 * @throws IllegalArgumentException		if the provided mapping-schema is {@code null}
	 */
	private static String transliterateLetter(Character prevChar, Character currChar, Character nextChar, IuliiaSchema schema) {
		if (schema == null) {
			throw new IllegalArgumentException("Schema mustn't be <null>");
		}

		String prev = UwObject.ifNotNull(prevChar, Object::toString, UwString.EMPTY);
		String curr = UwObject.ifNotNull(currChar, Object::toString, UwString.EMPTY);
		String next = UwObject.ifNotNull(nextChar, Object::toString, UwString.EMPTY);

		String result = schema.getPreviousLetterMap()
				.get(prev + curr);

		if (result == null) {
			result = schema.getNextLetterMap()
					.get(curr + next);
		}

		if (result == null) {
			result = schema.getSingleLetterMap()
					.getOrDefault(curr, curr);
		}

		return result;
	}

	private Iuliia() {
		throw new UnsupportedOperationException();
	}
}
