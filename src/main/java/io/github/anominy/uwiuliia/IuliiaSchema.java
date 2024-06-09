package io.github.anominy.uwiuliia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import io.github.anominy.gsonnative.GsonNativeTypeAdapterFactory;
import io.github.anominy.uwutils.UwFile;
import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.UwResource;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A mapping-schema representation.
 */
@SuppressWarnings({"unused", "MethodDoesntCallSuperMethod", "SynchronizeOnNonFinalField"})
public final class IuliiaSchema implements Serializable, Cloneable {

	/**
	 * A simple name of this class.
	 */
	private static final String SIMPLE_NAME = IuliiaSchema.class.getSimpleName();

	/**
	 * A {@link Gson} instance.
	 */
	private static final Gson GSON = new GsonBuilder()
			.registerTypeAdapterFactory(new GsonNativeTypeAdapterFactory())
			.create();

	/**
	 * A mapping-schema cache.
	 */
	private static final Map<String, IuliiaSchema> CACHE = new HashMap<>();

	/**
	 * A schema name.
	 */
	@SerializedName("name")
	private final String name;

	/**
	 * A schema description.
	 */
	@SerializedName("description")
	private final String description;

	/**
	 * A schema explanation-URL.
	 */
	@SerializedName("url")
	private final String url;

	/**
	 * A single letter-map.
	 */
	@SerializedName("mapping")
	@JsonAdapter(value = SingleLetterMapJsonDeserializer.class, nullSafe = false)
	private final Map<String, String> singleLetterMap;

	/**
	 * A previous letter-map.
	 */
	@SerializedName("prev_mapping")
	@JsonAdapter(value = PreviousLetterMapJsonDeserializer.class, nullSafe = false)
	private final Map<String, String> previousLetterMap;

	/**
	 * A next letter-map.
	 */
	@SerializedName("next_mapping")
	@JsonAdapter(value = NextLetterMapJsonDeserializer.class, nullSafe = false)
	private final Map<String, String> nextLetterMap;

	/**
	 * An ending letter-map.
	 */
	@SerializedName("ending_mapping")
	@JsonAdapter(value = EndingLetterMapJsonDeserializer.class, nullSafe = false)
	private final Map<String, String> endingLetterMap;

	/**
	 * A {@link #hashCode()} cache.
	 */
	private transient volatile Integer hashCodeCache;

	/**
	 * A {@link #toString()} cache.
	 */
	private transient volatile String stringCache;

	/**
	 * A {@link #hashCodeCache} mutex.
	 */
	private transient Object hashCodeCacheMutex;

	/**
	 * A {@link #stringCache} mutex.
	 */
	private transient Object stringCacheMutex;

	/**
	 * Initialize this mutex objects.
	 */
	private void initMutexObjects() {
		this.hashCodeCacheMutex = new Object();
		this.stringCacheMutex = new Object();
	}

	/**
	 * Override the {@code #readResolve} method to set up
	 * the object cache mutexes after deserialization.
	 *
	 * @return	this instance
	 */
	private Object readResolve() {
		this.initMutexObjects();

		return this;
	}

	/**
	 * Initialize a {@link IuliiaSchema} instance.
	 *
	 * @param name					schema name
	 * @param description			schema description
	 * @param url					schema explanation-URL
	 * @param singleLetterMap		single letter-map
	 * @param previousLetterMap		previous letter-map
	 * @param nextLetterMap			next letter-map
	 * @param endingLetterMap		ending letter-map
	 */
	private IuliiaSchema(
			String name,
			String description,
			String url,
			Map<String, String> singleLetterMap,
			Map<String, String> previousLetterMap,
			Map<String, String> nextLetterMap,
			Map<String, String> endingLetterMap
	) {
		this.name = name;
		this.description = description;
		this.url = url;
		this.singleLetterMap = singleLetterMap;
		this.previousLetterMap = previousLetterMap;
		this.nextLetterMap = nextLetterMap;
		this.endingLetterMap = endingLetterMap;

		this.initMutexObjects();
	}

	/**
	 * Initialize a {@link IuliiaSchema} instance.
	 *
	 * <p>Defines a copy constructor.
	 *
	 * @param that	instance to copy field values from
	 */
	private IuliiaSchema(IuliiaSchema that) {
		this(
				that.name,
				that.description,
				that.url,
				that.singleLetterMap,
				that.previousLetterMap,
				that.nextLetterMap,
				that.endingLetterMap
		);

		this.hashCodeCache = that.hashCodeCache;
		this.stringCache = that.stringCache;
	}

	/**
	 * Get this schema name.
	 *
	 * @return	schema name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get this schema description.
	 *
	 * @return	schema description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Get this schema explanation-URL.
	 *
	 * @return	schema explanation-URL
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Get this single letter-map.
	 *
	 * @return	schema single letter-map
	 */
	public Map<String, String> getSingleLetterMap() {
		return this.singleLetterMap;
	}

	/**
	 * Get this previous letter-map.
	 *
	 * @return	schema previous letter-map
	 */
	public Map<String, String> getPreviousLetterMap() {
		return this.previousLetterMap;
	}

	/**
	 * Get this next letter-map.
	 *
	 * @return	schema next letter-map
	 */
	public Map<String, String> getNextLetterMap() {
		return this.nextLetterMap;
	}

	/**
	 * Get this ending letter-map.
	 *
	 * @return	schema ending letter-map
	 */
	public Map<String, String> getEndingLetterMap() {
		return this.endingLetterMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		IuliiaSchema that = (IuliiaSchema) obj;

		return Objects.equals(this.name, that.name)
				&& Objects.equals(this.description, that.description)
				&& Objects.equals(this.url, that.url)
				&& Objects.equals(this.singleLetterMap, that.singleLetterMap)
				&& Objects.equals(this.previousLetterMap, that.previousLetterMap)
				&& Objects.equals(this.nextLetterMap, that.nextLetterMap)
				&& Objects.equals(this.endingLetterMap, that.endingLetterMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		if (this.hashCodeCache != null) {
			return this.hashCodeCache;
		}

		synchronized (this.hashCodeCacheMutex) {
			if (this.hashCodeCache != null) {
				return this.hashCodeCache;
			}

			return (this.hashCodeCache
					= Objects.hash(
							this.name,
							this.description,
							this.url,
							this.singleLetterMap,
							this.previousLetterMap,
							this.nextLetterMap,
							this.endingLetterMap
					)
			);
		}
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
					+ "name=\"" + this.name + "\""
					+ ", description=\"" + this.description + "\""
					+ ", url=\"" + this.url + "\""
					+ ", singleLetterMap=" + this.singleLetterMap
					+ ", previousLetterMap=" + this.previousLetterMap
					+ ", nextLetterMap=" + this.nextLetterMap
					+ ", endingLetterMap=" + this.endingLetterMap
					+ "]");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IuliiaSchema clone() {
		return new IuliiaSchema(this);
	}

	/**
	 * Get a mapping-schema by its file path or return {@code null}.
	 *
	 * @param path	path to the schema file
	 * @return		mapping-schema instance or {@code null}
	 */
	public static IuliiaSchema fromFilePath(String path) {
		path = UwFile.getPathOrNull(path);
		if (path == null) {
			return null;
		}

		IuliiaSchema schema = CACHE.get(path);
		if (schema != null) {
			return schema;
		}

		synchronized (CACHE) {
			schema = CACHE.get(path);
			if (schema != null) {
				return schema;
			}

			String schemaJson = UwResource.getAsStringOrNull(path);
			schema = GSON.fromJson(schemaJson, IuliiaSchema.class);

			CACHE.put(path, schema);
		}

		return schema;
	}

	/**
	 * Get a mapping-schema by its enum type or return {@code null}
	 *
	 * @param type	enum type of the schema
	 * @return		mapping-schema instance or {@code null}
	 */
	public static IuliiaSchema fromEnumType(EIuliiaSchema type) {
		return fromFilePath(UwObject.ifNotNull(type, EIuliiaSchema::getFilePath));
	}
}
