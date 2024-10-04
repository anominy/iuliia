/*
 * Copyright 2024 anominy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.anominy.iuliia;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.UwString;
import io.github.anominy.uwutils.VoidSupplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jetbrains.annotations.Unmodifiable;

import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * An iuliia schema.
 */
@SuppressWarnings({"unused", "SynchronizeOnNonFinalField", "MethodDoesntCallSuperMethod", "DefaultAnnotationParam"})
public final class IuliiaSchema implements Serializable, Cloneable {
    /**
     * An attribute name of this name.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_NAME = "name";

    /**
     * An attribute name of this description.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_DESCRIPTION = "description";

    /**
     * An attribute name of this URL.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_URL = "url";

    /**
     * An attribute name of this single letter map.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_SINGLE_LETTER_MAP = "singleLetterMap";

    /**
     * An attribute name of this previous letter map.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_PREVIOUS_LETTER_MAP = "previousLetterMap";

    /**
     * An attribute name of this next letter map.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_NEXT_LETTER_MAP = "nextLetterMap";

    /**
     * An attribute name of this ending letter map.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_ENDING_LETTER_MAP = "endingLetterMap";

    /**
     * A name.
     */
    @UnknownNullability
    private final String name;

    /**
     * A description.
     */
    @UnknownNullability
    private final String description;

    /**
     * A URL.
     */
    @UnknownNullability
    private final String url;

    /**
     * A single letter map.
     */
    @Unmodifiable
    @UnknownNullability
    private final Map<@NotNull String, @Nullable String> singleLetterMap;

    /**
     * A previous letter map.
     */
    @Unmodifiable
    @UnknownNullability
    private final Map<@NotNull String, @Nullable String> previousLetterMap;

    /**
     * A next letter map.
     */
    @Unmodifiable
    @UnknownNullability
    private final Map<@NotNull String, @Nullable String> nextLetterMap;

    /**
     * An ending letter map.
     */
    @Unmodifiable
    @UnknownNullability
    private final Map<@NotNull String, @Nullable String> endingLetterMap;

    /**
     * A {@link #hashCode()} cache.
     */
    @UnknownNullability
    private transient volatile Integer hashCodeCache;

    /**
     * A {@link #toString()} cache.
     */
    @UnknownNullability
    private transient volatile String stringCache;

    /**
     * A {@link #hashCodeCache} mutex.
     */
    @UnknownNullability
    private transient Object hashCodeCacheMutex;

    /**
     * A {@link #stringCache} mutex.
     */
    @UnknownNullability
    private transient Object stringCacheMutex;

    /**
     * Initialize this mutex objects.
     */
    @Contract(pure = true)
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
    @Contract(value = "-> this", pure = true)
    private Object readResolve() {
        this.initMutexObjects();
        return this;
    }

    /**
     * Initialize an {@link IuliiaSchema} instance.
     *
     * @param name                  name
     * @param description           description
     * @param url                   URL
     * @param singleLetterMap       single letter  map
     * @param previousLetterMap     previous letter map
     * @param nextLetterMap         next letter map
     * @param endingLetterMap       ending letter map
     */
    @Contract(pure = true)
    private IuliiaSchema(
            @Nullable
            final String name,

            @Nullable
            final String description,

            @Nullable
            final String url,

            @Nullable
            final Map<@NotNull String, @Nullable String> singleLetterMap,

            @Nullable
            final Map<@NotNull String, @Nullable String> previousLetterMap,

            @Nullable
            final Map<@NotNull String, @Nullable String> nextLetterMap,

            @Nullable
            final Map<@NotNull String, @Nullable String> endingLetterMap
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
     * Initialize an {@link IuliiaSchema} instance.
     *
     * <p>Defines a copy constructor.
     *
     * @param that  instance to copy field values from
     */
    @Contract(value = "null -> fail", pure = true)
    private IuliiaSchema(
            @UnknownNullability
            final IuliiaSchema that
    ) {
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
     * Get this name.
     *
     * @return  name
     */
    @UnknownNullability
    public String getName() {
        return this.name;
    }

    /**
     * Get this description.
     *
     * @return  description
     */
    @UnknownNullability
    public String getDescription() {
        return this.description;
    }

    /**
     * Get this URL.
     *
     * @return  URL
     */
    @UnknownNullability
    public String getUrl() {
        return this.url;
    }

    /**
     * Get this single letter map.
     *
     * @return  single letter map
     */
    @Unmodifiable
    @UnknownNullability
    public Map<@NotNull String, @Nullable String> getSingleLetterMap() {
        return this.singleLetterMap;
    }

    /**
     * Get this previous letter map.
     *
     * @return  previous letter map
     */
    @Unmodifiable
    @UnknownNullability
    public Map<@NotNull String, @Nullable String> getPreviousLetterMap() {
        return this.previousLetterMap;
    }

    /**
     * Get this next letter map.
     *
     * @return  next letter map
     */
    @Unmodifiable
    @UnknownNullability
    public Map<@NotNull String, @Nullable String> getNextLetterMap() {
        return this.nextLetterMap;
    }

    /**
     * Get this ending letter map.
     *
     * @return  ending letter map
     */
    @Unmodifiable
    @UnknownNullability
    public Map<@NotNull String, @Nullable String> getEndingLetterMap() {
        return this.endingLetterMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Contract(pure = true)
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
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
    @Contract(pure = true)
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
    @NotNull
    @Contract(pure = true)
    public String toString() {
        if (this.stringCache != null) {
            return this.stringCache;
        }

        synchronized (this.stringCacheMutex) {
            if (this.stringCache != null) {
                return this.stringCache;
            }

            final String simpleName = IuliiaSchema.class.getSimpleName();

            return (this.stringCache = simpleName
                    + "["
                    + ATTRIBUTE_NAME_NAME
                    + "="
                    + this.name
                    + ", "
                    + ATTRIBUTE_NAME_DESCRIPTION
                    + "="
                    + this.description
                    + ", "
                    + ATTRIBUTE_NAME_URL
                    + "="
                    + this.url
                    + ", "
                    + ATTRIBUTE_NAME_SINGLE_LETTER_MAP
                    + "="
                    + this.singleLetterMap
                    + ", "
                    + ATTRIBUTE_NAME_PREVIOUS_LETTER_MAP
                    + "="
                    + this.previousLetterMap
                    + ", "
                    + ATTRIBUTE_NAME_NEXT_LETTER_MAP
                    + "="
                    + this.nextLetterMap
                    + ", "
                    + ATTRIBUTE_NAME_ENDING_LETTER_MAP
                    + "="
                    + this.endingLetterMap
                    + "]");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    @Contract(value = "-> new", pure = true)
    public IuliiaSchema clone() {
        return new IuliiaSchema(this);
    }

    /**
     * Create a new {@link IuliiaSchema} instance from a file path
     * or return a default value on failure.
     *
     * @param path          path to get the schema from, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static IuliiaSchema fromFilePathOrElse(
            @Nullable
            final String path,

            @Nullable
            final IuliiaSchema defaultValue
    ) {
        try {
            return fromFilePathNoCheck(path);
        } catch (final NullPointerException
                       | IllegalArgumentException ignored) {
        }

        return defaultValue;
    }

    /**
     * Create a new {@link IuliiaSchema} instance from a file path
     * or return a default value on failure.
     *
     * @param path                  path to get the schema from, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static IuliiaSchema fromFilePathOrElse(
            @Nullable
            final String path,

            @Nullable
            final Supplier<@UnknownNullability IuliiaSchema> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromFilePathOrNull(path), defaultValueSupplier);
    }

    /**
     * Create a new {@link IuliiaSchema} instance from a file path
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromFilePathOrElse(String, Supplier)}.
     *
     * @param path                  path to get the schema from, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static IuliiaSchema fromFilePathOrElse(
            @Nullable
            final String path,

            @Nullable
            final VoidSupplier<@UnknownNullability IuliiaSchema> defaultValueSupplier
    ) {
        return fromFilePathOrElse(path, (@Nullable Supplier<@UnknownNullability IuliiaSchema>) defaultValueSupplier);
    }

    /**
     * Create a new {@link IuliiaSchema} instance from a file path
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromFilePathOrElse(String, IuliiaSchema)}
     * w/ {@code null} as the default value.
     *
     * @param path  path to get the schema from, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static IuliiaSchema fromFilePathOrNull(
            @Nullable
            final String path
    ) {
        return fromFilePathOrElse(path, (@Nullable IuliiaSchema) null);
    }

    /**
     * Create a new {@link IuliiaSchema} instance from a file path.
     *
     * @param path  path to get the schema from, mustn't be null
     *
     * @return  new instance
     *
     * @throws NullPointerException if provided path is {@code null}
     */
    @NotNull
    @Contract(value = "null -> fail", pure = false)
    public static IuliiaSchema fromFilePathNoCheck(
            @UnknownNullability
            String path
    ) {
        path = path.trim()
                .replace("\\", "/")
                .replaceAll("/+", "/")
                .replaceAll("^/|/$", "");

        IuliiaSchema schema = SingletonCache.INSTANCE.get(path);
        if (schema != null) {
            return schema;
        }

        synchronized (SingletonCache.INSTANCE) {
            schema = SingletonCache.INSTANCE.get(path);
            if (schema != null) {
                return schema;
            }

            ClassLoader classLoader = Thread.currentThread()
                    .getContextClassLoader();
            if (classLoader == null) {
                classLoader = IuliiaSchema.class.getClassLoader();
            }

            final URL schemaUrl = classLoader.getResource(path);
            if (schemaUrl == null) {
                throw new IllegalArgumentException();
            }

            final JsonObject jsonObject;
            try {
                jsonObject = JsonParser.object()
                        .from(schemaUrl);
            } catch (JsonParserException e) {
                throw new IllegalArgumentException(e);
            }

            final String name = jsonObject.getString("name");
            final String description = jsonObject.getString("description");
            final String url = jsonObject.getString("url");

            final JsonObject singleLetterMapJson = jsonObject.getObject("mapping");
            final JsonObject previousLetterMapJson = jsonObject.getObject("prev_mapping");
            final JsonObject nextLetterMapJson = jsonObject.getObject("next_mapping");
            final JsonObject endingLetterMapJson = jsonObject.getObject("ending_mapping");

            //noinspection ExtractMethodRecommender
            Map<String, String> singleLetterMap = new HashMap<>();
            if (singleLetterMapJson != null) {
                for (Map.Entry<String, Object> entry : singleLetterMapJson.entrySet()) {
                    //noinspection DuplicatedCode
                    final String key = entry.getKey();
                    final String val = (String) entry.getValue();

                    final String capitalizedKey = UwString.capitalizeOrNull(key);
                    final String capitalizedValue = UwString.capitalizeOrNull(val);

                    singleLetterMap.put(key, val);
                    singleLetterMap.put(capitalizedKey, capitalizedValue);
                }
            }
            //noinspection DuplicatedCode
            singleLetterMap = Collections.unmodifiableMap(singleLetterMap);


            Map<String, String> previousLetterMap = new HashMap<>();
            if (previousLetterMapJson != null) {
                for (Map.Entry<String, Object> entry : previousLetterMapJson .entrySet()) {
                    final String key = entry.getKey();
                    final String val = (String) entry.getValue();

                    final String capitalizedKey = UwString.capitalizeOrNull(key);
                    final String capitalizedValue = UwString.capitalizeOrNull(val);

                    previousLetterMap.put(key, val);
                    previousLetterMap.put(capitalizedKey, val);
                    previousLetterMap.put(key.toUpperCase(Locale.ROOT), capitalizedValue);
                }
            }
            //noinspection DuplicatedCode
            previousLetterMap = Collections.unmodifiableMap(previousLetterMap);

            Map<String, String> nextLetterMap = new HashMap<>();
            if (nextLetterMapJson != null) {
                for (Map.Entry<String, Object> entry : nextLetterMapJson.entrySet()) {
                    final String key = entry.getKey();
                    final String val = (String) entry.getValue();

                    final String capitalizedKey = UwString.capitalizeOrNull(key);
                    final String capitalizedValue = UwString.capitalizeOrNull(val);

                    nextLetterMap.put(key, val);
                    nextLetterMap.put(capitalizedKey, capitalizedValue);
                    nextLetterMap.put(key.toUpperCase(Locale.ROOT), capitalizedValue);
                }
            }
            nextLetterMap = Collections.unmodifiableMap(nextLetterMap);

            Map<String, String> endingLetterMap = new HashMap<>();
            if (endingLetterMapJson != null) {
                for (Map.Entry<String, Object> entry : endingLetterMapJson.entrySet()) {
                    final String key = entry.getKey();
                    final String val = (String) entry.getValue();

                    endingLetterMap.put(key, val);
                    endingLetterMap.put(key.toUpperCase(Locale.ROOT), val.toUpperCase(Locale.ROOT));
                }
            }
            endingLetterMap = Collections.unmodifiableMap(endingLetterMap);

            schema = new IuliiaSchema(name, description, url, singleLetterMap,
                    previousLetterMap, nextLetterMap, endingLetterMap);

            SingletonCache.INSTANCE.put(path, schema);
        }

        return schema;
    }

    /**
     * Create a new {@link IuliiaSchema} instance from an enum type
     * or return a default value on failure.
     *
     * @param type          enum type to get the schema from, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static IuliiaSchema fromEnumTypeOrElse(
            @Nullable
            final EIuliiaSchema type,

            @Nullable
            final IuliiaSchema defaultValue
    ) {
        try {
            return fromEnumTypeNoCheck(type);
        } catch (final IllegalArgumentException
                       | NullPointerException ignored) {
        }

        return defaultValue;
    }

    /**
     * Create a new {@link IuliiaSchema} instance from an enum type
     * or return a default value on failure.
     *
     * @param type                  enum type to get the schema from, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static IuliiaSchema fromEnumTypeOrElse(
            @Nullable
            final EIuliiaSchema type,

            @Nullable
            final Supplier<@UnknownNullability IuliiaSchema> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromEnumTypeOrNull(type), defaultValueSupplier);
    }

    /**
     * Create a new {@link IuliiaSchema} instance from an enum type
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromEnumTypeOrElse(EIuliiaSchema, Supplier)}.
     *
     * @param type                  enum type to get the schema from, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  new instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static IuliiaSchema fromEnumTypeOrElse(
            @Nullable
            final EIuliiaSchema type,

            @Nullable
            final VoidSupplier<@UnknownNullability IuliiaSchema> defaultValueSupplier
    ) {
        return fromEnumTypeOrElse(type, (@Nullable Supplier<@UnknownNullability IuliiaSchema>) defaultValueSupplier);
    }

    /**
     * Create a new {@link IuliiaSchema} instance from an enum type
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromEnumTypeOrElse(EIuliiaSchema, IuliiaSchema)}
     * w/ {@code null} as the default value.
     *
     * @param type  enum type to get the schema from, may be null
     *
     * @return  new instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static IuliiaSchema fromEnumTypeOrNull(
            @Nullable
            final EIuliiaSchema type
    ) {
        return fromEnumTypeOrElse(type, (@Nullable IuliiaSchema) null);
    }

    /**
     * Create a new {@link IuliiaSchema} instance from an enum type.
     *
     * <p>Wraps {@link #fromFilePathNoCheck(String)}.
     *
     * @param type  enum type to get the schema from, mustn't be null
     *
     * @return  new instance
     *
     * @throws NullPointerException if provided enum type is {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> fail", pure = false)
    public static IuliiaSchema fromEnumTypeNoCheck(
            @UnknownNullability
            final EIuliiaSchema type
    ) {
        return fromFilePathNoCheck(type.getFilePath());
    }

    @SuppressWarnings("DefaultAnnotationParam")
    private static final class SingletonCache {
        @NotNull
        public static final Map<@NotNull String, @NotNull IuliiaSchema> INSTANCE = new HashMap<>();

        @Contract(value = "-> fail", pure = false)
        private SingletonCache() {
            throw new UnsupportedOperationException();
        }
    }
}
