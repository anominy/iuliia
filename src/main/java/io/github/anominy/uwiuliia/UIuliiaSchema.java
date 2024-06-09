package io.github.anominy.uwiuliia;

import io.github.anominy.uwutils.UwFile;

/**
 * A mapping-schema utility.
 */
public final class UIuliiaSchema {

	/**
	 * An internal mapping-schema file extension.
	 */
	public static final String INTERNAL_FILE_EXT = ".json";

	/**
	 * An internal path to mapping-schema files.
	 */
	public static final String INTERNAL_FILE_PATH = "schemas/";

	/**
	 * Get an internal mapping-schema name by its path and normalize it.
	 *
	 * @param path	path to the schema file
	 * @return		normalized schema file name
	 */
	public static String getInternalName(String path) {
		String name = UwFile.getNameOrNull(path);

		if (name == null) {
			return null;
		}

		return name.replaceAll("[\\s_]", "-")
				.toLowerCase();
	}

	/**
	 * Get an internal mapping-schema path by its name or path.
	 *
	 * @param path	name or path to/of the schema file
	 * @return		internal path to the schema file
	 */
	public static String getInternalPath(String path) {
		String name = getInternalName(path);

		if (name == null || name.isEmpty()) {
			return null;
		}

		return INTERNAL_FILE_PATH + name + INTERNAL_FILE_EXT;
	}

	private UIuliiaSchema() {
		throw new UnsupportedOperationException();
	}
}
