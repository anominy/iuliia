package io.github.anominy.uwiuliia;

import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.UwString;

/**
 * A mapping-word representation.
 */
final class IuliiaWord {

	/**
	 * A word ending length.
	 */
	private static final int ENDING_LENGTH = 2;

	/**
	 * A word stem.
	 */
	private final String stem;

	/**
	 * A word ending.
	 */
	private final String ending;

	/**
	 * Initialize a {@link IuliiaWord} instance.
	 *
	 * @param word	string-word, default to {@link UwString#EMPTY} if {@code null}
	 */
	public IuliiaWord(String word) {
		word = UwObject.ifNull(word, UwString.EMPTY);

		String stem = word;
		String ending = UwString.EMPTY;

		int stemLength = stem.length();

		if (stemLength > ENDING_LENGTH) {
			stemLength -= ENDING_LENGTH;

			stem = word.substring(0, stemLength);
			ending = word.substring(stemLength);
		}

		this.stem = stem;
		this.ending = ending;
	}

	/**
	 * Get this word stem.
	 *
	 * @return	word stem
	 */
	public String getStem() {
		return this.stem;
	}

	/**
	 * Get this word ending.
	 *
	 * @return	word ending
	 */
	public String getEnding() {
		return this.ending;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.stem + this.ending;
	}
}
