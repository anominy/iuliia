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

import io.github.anominy.uwutils.UwArray;
import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.VoidSupplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jetbrains.annotations.Unmodifiable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A iuliia schema enums.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam", "SameReturnValue"})
public enum EIuliiaSchema implements Serializable {

    /**
     * A schema - ALA-LC.
     */
    ALA_LC,

    /**
     * A schema - ALA-LC-ALT.
     */
    ALA_LC_ALT,

    /**
     * A schema - BGN-PCGN.
     */
    BGN_PCGN,

    /**
     * A schema - BGN-PCGN-ALT.
     */
    BGN_PCGN_ALT,

    /**
     * A schema - BS-2979.
     */
    BS_2979,

    /**
     * A schema - BS-2979-ALT.
     */
    BS_2979_ALT,

    /**
     * A schema - GOST-779.
     */
    GOST_779,

    /**
     * A schema - GOST-779-ALT.
     */
    GOST_779_ALT,

    /**
     * A schema - GOST-7034.
     */
    GOST_7034,

    /**
     * A schema - GOST-16876.
     */
    GOST_16876,

    /**
     * A schema - GOST-16876-ALT.
     */
    GOST_16876_ALT,

    /**
     * A schema - GOST-52290.
     */
    GOST_52290,

    /**
     * A schema - GOST-52535.
     */
    GOST_52535,

    /**
     * A schema - ICAO-DOC-9303.
     */
    ICAO_DOC_9303,

    /**
     * A schema - ISO-9-1954.
     */
    ISO_9_1954,

    /**
     * A schema - ISO-9-1968.
     */
    ISO_9_1968,

    /**
     * A schema - ISO-9-1968-ALT.
     */
    ISO_9_1968_ALT,

    /**
     * A schema - MOSMETRO.
     */
    MOSMETRO,

    /**
     * A schema - MVD-310.
     */
    MVD_310,

    /**
     * A schema - MVD-310-FR.
     */
    MVD_310_FR,

    /**
     * A schema - MVD-782.
     */
    MVD_782,

    /**
     * A schema - SCIENTIFIC.
     */
    SCIENTIFIC,

    /**
     * A schema - TELEGRAM.
     */
    TELEGRAM,

    /**
     * A schema - UNGEGN-1987.
     */
    UNGEGN_1987,

    /**
     * A schema - WIKIPEDIA.
     */
    WIKIPEDIA,

    /**
     * A schema - YANDEX MAPS.
     */
    YANDEX_MAPS,

    /**
     * A schema - YANDEX MONEY.
     */
    YANDEX_MONEY;

    /**
     * An attribute name of this file path.
     */
    @NotNull
    public static final String ATTRIBUTE_NAME_FILE_PATH = "filePath";

    /**
     * A file path.
     */
    @NotNull
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
        this.filePath = UIuliiaSchema.getInternalPathOrNull(this.name());
        if (this.filePath == null) {
            throw new IllegalStateException("Schema file path mustn't be <null>");
        }

        this.stringCacheMutex = new Object();
    }

    /**
     * Get this file path.
     *
     * @return  file path
     */
    @NotNull
    public String getFilePath() {
        return this.filePath;
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

            final String className = EIuliiaSchema.class.getSimpleName();
            final String instanceName = this.name();

            return (this.stringCache = className
                    + "::"
                    + instanceName
                    + "["
                    + ATTRIBUTE_NAME_FILE_PATH
                    + "="
                    + this.filePath
                    + "]");
        }
    }

    /**
     * Get an unmodifiable list of all iuliia schema enums.
     *
     * @return  unmodifiable list
     */
    @NotNull
    @Unmodifiable
    @Contract(pure = true)
    public static List<@NotNull EIuliiaSchema> getValueList() {
        return SingletonValueList.INSTANCE;
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its file path
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromFilePathNoCheck(String)}.
     *
     * @param filePath      file path of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static EIuliiaSchema fromFilePathOrElse(
            @Nullable
            final String filePath,

            @Nullable
            final EIuliiaSchema defaultValue
    ) {
        return UwObject.ifNotNullNoCheck(filePath, EIuliiaSchema::fromFilePathNoCheck);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its file path
     * or return a default value on failure.
     *
     * @param filePath              file path of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static EIuliiaSchema fromFilePathOrElse(
            @Nullable
            final String filePath,

            @Nullable
            final Supplier<@UnknownNullability EIuliiaSchema> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromFilePathOrNull(filePath), defaultValueSupplier);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its file path
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromFilePathOrElse(String, Supplier)}.
     *
     * @param filePath              file path of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static EIuliiaSchema fromFilePathOrElse(
            @Nullable
            final String filePath,

            @Nullable
            final VoidSupplier<@UnknownNullability EIuliiaSchema> defaultValueSupplier
    ) {
        return fromFilePathOrElse(filePath, (@Nullable Supplier<@UnknownNullability EIuliiaSchema>) defaultValueSupplier);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its file path
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #fromFilePathOrElse(String, EIuliiaSchema)}
     * w/ {@code null} as the default value.
     *
     * @param filePath  file path of the instance, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static EIuliiaSchema fromFilePathOrNull(
            @Nullable
            final String filePath
    ) {
        return fromFilePathOrElse(filePath, (@Nullable EIuliiaSchema) null);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its file path.
     *
     * @param filePath  file path of the instance, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance
     */
    @UnknownNullability
    @Contract(pure = false)
    public static EIuliiaSchema fromFilePathNoCheck(
            @UnknownNullability
            final String filePath
    ) {
        return SingletonMapByFilePath.INSTANCE.get(filePath);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexNoCheck(int)}.
     *
     * @param index         index of the instance, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static EIuliiaSchema fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final EIuliiaSchema defaultValue
    ) {
        final EIuliiaSchema result = UwObject.ifNotNullNoCheck(index, EIuliiaSchema::fromIndexNoCheck, defaultValue);
        return UwObject.ifNull(result, defaultValue);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrNull(Integer)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static EIuliiaSchema fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability EIuliiaSchema> defaultValueSupplier
    ) {
        return UwObject.ifNull(fromIndexOrNull(index), defaultValueSupplier);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its index
     * or return a default value on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, Supplier)}.
     *
     * @param index                 index of the instance, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static EIuliiaSchema fromIndexOrElse(
            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability EIuliiaSchema> defaultValueSupplier
    ) {
        return fromIndexOrElse(index, (@Nullable Supplier<@UnknownNullability EIuliiaSchema>) defaultValueSupplier);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its index
     * or return {@code null} instance on failure.
     *
     * <p>Wraps {@link #fromIndexOrElse(Integer, EIuliiaSchema)}
     * w/ {@code null} as the default value.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static EIuliiaSchema fromIndexOrNull(
            @Nullable
            final Integer index
    ) {
        return fromIndexOrElse(index, (@Nullable EIuliiaSchema) null);
    }

    /**
     * Get an {@link EIuliiaSchema} instance by its index.
     *
     * <p>Wraps {@link UwArray#getNoCheck(Object[], int)}.
     *
     * @param index     index of the instance, may be null
     *
     * @return  associated {@link EIuliiaSchema} instance
     */
    @UnknownNullability
    @Contract(pure = true)
    public static EIuliiaSchema fromIndexNoCheck(
            final int index
    ) {
        return UwArray.getNoCheck(SingletonValues.INSTANCE, index);
    }

    @SuppressWarnings("DefaultAnnotationParam")
    private static final class SingletonValues {
        @NotNull
        public static final EIuliiaSchema @NotNull [] INSTANCE = EIuliiaSchema.values();

        @Contract(value = "-> fail", pure = false)
        private SingletonValues() {
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("DefaultAnnotationParam")
    private static final class SingletonValueList {
        @NotNull
        @Unmodifiable
        public static final List<@NotNull EIuliiaSchema> INSTANCE;

        static {
            final EIuliiaSchema[] values = SingletonValues.INSTANCE;
            final List<EIuliiaSchema> list = new ArrayList<>(values.length);

            //noinspection ManualArrayToCollectionCopy
            for (final EIuliiaSchema value : values) {
                //noinspection UseBulkOperation
                list.add(value);
            }

            INSTANCE = Collections.unmodifiableList(list);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonValueList() {
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("DefaultAnnotationParam")
    private static final class SingletonMapByFilePath {
        @NotNull
        @Unmodifiable
        public static final Map<@NotNull String, @NotNull EIuliiaSchema> INSTANCE;

        static {
            final EIuliiaSchema[] values = SingletonValues.INSTANCE;
            final Map<String, EIuliiaSchema> map = new HashMap<>();

            for (final EIuliiaSchema value : values) {
                if (map.containsKey(value.filePath)) {
                    throw new IllegalStateException("File path field of EIuliiaSchema class must be unique");
                }

                map.put(value.filePath, value);
            }

            INSTANCE = Collections.unmodifiableMap(map);
        }

        @Contract(value = "-> fail", pure = false)
        private SingletonMapByFilePath() {
            throw new UnsupportedOperationException();
        }
    }
}
