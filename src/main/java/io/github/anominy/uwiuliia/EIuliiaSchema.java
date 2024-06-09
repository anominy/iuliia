package io.github.anominy.uwiuliia;

import io.github.anominy.uwutils.*;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An enum type to enumerate all internal mapping-schemas.
 */
@SuppressWarnings("unused")
public enum EIuliiaSchema implements Serializable {
	ALA_LC,
	ALA_LC_ALT,
	BGN_PCGN,
	BGN_PCGN_ALT,
	BS_2979,
	BS_2979_ALT,
	GOST_779,
	GOST_779_ALT,
	GOST_7034,
	GOST_16876,
	GOST_16876_ALT,
	GOST_52290,
	GOST_52535,
	ICAO_DOC_9303,
	ISO_9_1954,
	ISO_9_1968,
	ISO_9_1968_ALT,
	MOSMETRO,
	MVD_310,
	MVD_310_FR,
	MVD_782,
	SCIENTIFIC,
	TELEGRAM,
	UNGEGN_1987,
	WIKIPEDIA,
	YANDEX_MAPS,
	YANDEX_MONEY;

	/**
	 * A class instance of this class.
	 */
	private static final Class<EIuliiaSchema> CLASS = EIuliiaSchema.class;

	/**
	 * A simple name of this class.
	 */
	private static final String SIMPLE_NAME = CLASS.getSimpleName();

	/**
	 * An array of {@link EIuliiaSchema} instances.
	 */
	private static final EIuliiaSchema[] VALUES = UwEnum.values(CLASS);

	/**
	 * A map of {@link EIuliiaSchema} instances by their schema file path.
	 */
	private static final Map<String, EIuliiaSchema> MAP_BY_FILE_PATH = UwMap.createByFieldOrNull(
			entry -> entry.filePath, VALUES
	);

	/**
	 * A schema file path.
	 */
	private final String filePath;

	/**
	 * A {@link #toString()} cache.
	 */
	private volatile String stringCache;

	/**
	 * A {@link #stringCache} mutex.
	 */
	private final Object stringCacheMutex;

	/**
	 * Initialize an {@link EIuliiaSchema} instance.
	 */
	EIuliiaSchema() {
		this.filePath = UIuliiaSchema.getInternalPath(this.name());
		if (this.filePath == null) {
			throw new IllegalStateException("Schema file path mustn't be <null>");
		}

		this.stringCacheMutex = new Object();
	}

	/**
	 * Get this schema file path.
	 *
	 * @return	schema file path
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (this.stringCache != null) {
			return this.stringCache;
		}

		synchronized (this.stringCacheMutex) {
			if (this.stringCache != null) {
				return this.stringCache;
			}

			return (this.stringCache = SIMPLE_NAME + "["
					+ "filePath=\"" + this.filePath + "\""
					+ "]");
		}
	}

	/**
	 * Get the schema file path from the provided {@link EIuliiaSchema} instance
	 * or return a default value if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link EIuliiaSchema} instance is {@code null}.
	 * </ul>
	 *
	 * @param schema		enum value of the schema type from which get the file path
	 * @param defaultValue	default value to return on failure
	 * @return				schema file path or the default value
	 */
	public static String getFilePathOrElse(EIuliiaSchema schema, String defaultValue) {
		return UwObject.ifNotNull(schema, EIuliiaSchema::getFilePath, defaultValue);
	}

	/**
	 * Get the schema file path from the provided {@link EIuliiaSchema} instance
	 * or return a default value if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link EIuliiaSchema} instance is {@code null}.
	 * </ul>
	 *
	 * @param schema				enum value of the schema type from which get the file path
	 * @param defaultValueSupplier	supplier from which get the default value
	 * @return						schema file path or the default value
	 */
	public static String getFilePathOrElse(EIuliiaSchema schema, Supplier<String> defaultValueSupplier) {
		return UwObject.ifNull(getFilePathOrNull(schema), defaultValueSupplier);
	}

	/**
	 * Get the schema file path from the provided {@link EIuliiaSchema} instance
	 * or return an empty string if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link EIuliiaSchema} instance is {@code null}.
	 * </ul>
	 *
	 * <p>Wraps {@link #getFilePathOrElse(EIuliiaSchema, String)}
	 * w/ {@link UwString#EMPTY} as the default value.
	 *
	 * @param schema	enum value of the schema type from which get the file path
	 * @return			schema file path or the empty string
	 */
	public static String getFilePathOrEmpty(EIuliiaSchema schema) {
		return getFilePathOrElse(schema, UwString.EMPTY);
	}

	/**
	 * Get the schema file path from the provided {@link EIuliiaSchema} instance
	 * or return {@code null} if failed.
	 *
	 * <p>Possible failure cases:
	 * <ul>
	 *     <li>{@link EIuliiaSchema} instance is {@code null}.
	 * </ul>
	 *
	 * <p>Wraps {@link #getFilePathOrElse(EIuliiaSchema, String)}
	 * w/ {@code null} as the default value.
	 *
	 * @param schema	enum value of the schema type from which get the file path
	 * @return			schema file path or {@code null}
	 */
	public static String getFilePathOrNull(EIuliiaSchema schema) {
		return getFilePathOrElse(schema, (String) null);
	}

	/**
	 * Get an {@link EIuliiaSchema} instance by its schema file path
	 * or return a default value if failed.
	 *
	 * @param filePath		schema file path of the instance
	 * @param defaultValue	default value to return on failure
	 * @return				associated {@link EIuliiaSchema} instance or the default value
	 */
	public static EIuliiaSchema fromFilePathOrElse(String filePath, EIuliiaSchema defaultValue) {
		return UwMap.getOrElse(filePath, MAP_BY_FILE_PATH, defaultValue);
	}

	/**
	 * Get an {@link EIuliiaSchema} instance by its schema file path
	 * or return a default value if failed.
	 *
	 * @param filePath				schema file path of the instance
	 * @param defaultValueSupplier	supplier from which get the default value
	 * @return						associated {@link EIuliiaSchema} instance or the default value
	 */
	public static EIuliiaSchema fromFilePathOrElse(String filePath, Supplier<EIuliiaSchema> defaultValueSupplier) {
		return UwObject.ifNull(fromFilePathOrNull(filePath), defaultValueSupplier);
	}

	/**
	 * Get an {@link EIuliiaSchema} instance by its schema file path
	 * or return {@code null} if failed.
	 *
	 * <p>Wraps {@link #fromFilePathOrElse(String, EIuliiaSchema)}
	 * w/ {@code null} as the default value.
	 *
	 * @param filePath	schema file path of the instance
	 * @return			associated {@link EIuliiaSchema} instance or {@code null}
	 */
	public static EIuliiaSchema fromFilePathOrNull(String filePath) {
		return fromFilePathOrElse(filePath, (EIuliiaSchema) null);
	}

	/**
	 * Get an {@link EIuliiaSchema} instance by its index
	 * or return a default value if failed.
	 *
	 * @param index			index of the instance
	 * @param defaultValue	default value to return on failure
	 * @return				associated {@link EIuliiaSchema} instance or the default value
	 */
	public static EIuliiaSchema fromIndexOrElse(Integer index, EIuliiaSchema defaultValue) {
		return UwArray.getOrElse(index, VALUES, defaultValue);
	}

	/**
	 * Get an {@link EIuliiaSchema} instance by its index
	 * or return a default value if failed.
	 *
	 * @param index					index of the instance
	 * @param defaultValueSupplier	supplier from which get the default value
	 * @return						associated {@link EIuliiaSchema} instance or the default value
	 */
	public static EIuliiaSchema fromIndexOrElse(Integer index, Supplier<EIuliiaSchema> defaultValueSupplier) {
		return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
	}

	/**
	 * Get an {@link EIuliiaSchema} instance by its index
	 * or return {@code null} if failed.
	 *
	 * <p>Wraps {@link #fromIndexOrElse(Integer, EIuliiaSchema)}
	 * w/ {@code null} as the default value.
	 *
	 * @param index		index of the instance
	 * @return			associated {@link EIuliiaSchema} instance or {@code null}
	 */
	public static EIuliiaSchema fromIndexOrNull(Integer index) {
		return fromIndexOrElse(index, (EIuliiaSchema) null);
	}
}
