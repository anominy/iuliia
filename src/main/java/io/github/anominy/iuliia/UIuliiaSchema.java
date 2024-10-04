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

import io.github.anominy.uwutils.UwObject;
import io.github.anominy.uwutils.UwString;
import io.github.anominy.uwutils.VoidSupplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * An iuliia schema utility.
 */
@SuppressWarnings({"DefaultAnnotationParam", "unused"})
public final class UIuliiaSchema {

    /**
     * An internal schema file extension.
     */
    @NotNull
    public static final String INTERNAL_FILE_EXT = ".json";

    /**
     * An internal schema file path.
     */
    @NotNull
    public static final String INTERNAL_FILE_PATH = "schemas/";


    /**
     * Get a schema internal name by its file path
     * or return a default value on failure.
     *
     * @param path          path to get the name from, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  internal schema name or the default value
     */
    @Contract(value = "null, _ -> param2", pure = true)
    public static String getInternalNameOrElse(
            @Nullable
            final String path,

            @Nullable
            final String defaultValue
    ) {
        if (path == null) {
            return defaultValue;
        }

        try {
            return getInternalNameNoCheck(path);
        } catch (final NullPointerException
                       | IllegalArgumentException ignored) {
        }

        return defaultValue;
    }

    /**
     * Get a schema internal name by its file path
     * or return a default value on failure.
     *
     * @param path                  path to get the name from, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  internal schema name or the default value
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static String getInternalNameOrElse(
            @Nullable
            final String path,

            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(getInternalNameOrNull(path), defaultValueSupplier);
    }

    /**
     * Get a schema internal name by its file path
     * or return a default value on failure.
     *
     * <p>Wraps {@link #getInternalNameOrElse(String, Supplier)}.
     *
     * @param path                  path to get the name from, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  internal schema name or the default value
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static String getInternalNameOrElse(
            @Nullable
            final String path,

            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return getInternalNameOrElse(path, (@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get a schema internal name by its file path
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #getInternalNameOrElse(String, String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * @param path  path to get the name from, may be null
     *
     * @return  internal schema name or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public static String getInternalNameOrEmpty(
            @Nullable
            final String path
    ) {
        return getInternalNameOrElse(path, UwString.EMPTY);
    }

    /**
     * Get a schema internal name by its file path
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #getInternalNameOrElse(String, String)}
     * w/ {@code null} as the default value.
     *
     * @param path  path to get the name from, may be null
     *
     * @return  internal schema name or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static String getInternalNameOrNull(
            @Nullable
            final String path
    ) {
        return getInternalNameOrElse(path, (@Nullable String) null);
    }

    /**
     * Get a schema internal name by its file path.
     *
     * @param path  path to get the name from, mustn't be null
     *
     * @return  internal schema name
     *
     * @throws NullPointerException if provided path is {@code null}
     */
    @NotNull
    @Contract(value = "null -> fail", pure = false)
    public static String getInternalNameNoCheck(
            @UnknownNullability
            String path
    ) {
        path = path.trim()
                .replace("\\", "/")
                .replaceAll("/+", "/")
                .replaceAll("^/|/$", "");

        if (path.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String name = path.substring(path.lastIndexOf('.') + 1)
                .replaceAll("[\\s_]", "-")
                .toLowerCase(Locale.ROOT);

        if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return name;
    }

    /**
     * Get a schema internal path by its name
     * or return a default value on failure
     *
     * @param path          path to get the name from, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  internal schema path
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2", pure = true)
    public static String getInternalPathOrElse(
            @Nullable
            final String path,

            @Nullable
            final String defaultValue
    ) {
        try {
            return getInternalPathNoCheck(path);
        } catch (final NullPointerException
                       | IllegalArgumentException ignored) {
        }

        return defaultValue;
    }

    /**
     * Get a schema internal path by its name
     * or return a default value on failure
     *
     * @param path                  path to get the name from, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  internal schema path
     */
    @UnknownNullability
    @Contract(value = "null, null -> null", pure = false)
    public static String getInternalPathOrElse(
            @Nullable
            final String path,

            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(getInternalPathOrNull(path), defaultValueSupplier);
    }

    /**
     * Get a schema internal path by its name
     * or return a default value on failure
     *
     * <p>Wraps {@link #getInternalPathOrElse(String, Supplier)}.
     *
     * @param path                  path to get the name from, may be null
     * @param defaultValueSupplier  supplier to get the default value from, may be null
     *
     * @return  internal schema path
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public static String getInternalPathOrElse(
            @Nullable
            final String path,

            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return getInternalPathOrElse(path, (@Nullable Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    /**
     * Get a schema internal path by its name
     * or return an empty string on failure.
     *
     * <p>Wraps {@link #getInternalPathOrElse(String, String)}
     * w/ {@link UwString#EMPTY} as the default value.
     *
     * @param path  path to get the name from, may be null
     *
     * @return  internal schema path or the empty string
     */
    @NotNull
    @Contract(pure = true)
    public static String getInternalPathOrEmpty(
            @Nullable
            final String path
    ) {
        return getInternalPathOrElse(path, UwString.EMPTY);
    }

    /**
     * Get a schema internal path by its name
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #getInternalPathOrElse(String, String)}
     * w/ {@code null} as the default value.
     *
     * @param path  path to get the name from, may be null
     *
     * @return  internal schema path or {@code null}
     */
    @UnknownNullability
    @Contract(value = "null -> null", pure = true)
    public static String getInternalPathOrNull(
            @Nullable
            final String path
    ) {
        return getInternalPathOrElse(path, (@Nullable String) null);
    }

    /**
     * Get a schema internal path by its name.
     *
     * @param path  path to get the name from, mustn't be null
     *
     * @return  internal schema path
     *
     * @throws NullPointerException if provided path is {@code null}
     */
    @NotNull
    @Contract(value = "null -> fail", pure = false)
    public static String getInternalPathNoCheck(
            @Nullable
            final String path
    ) {
        final String name = getInternalNameNoCheck(path);
        return INTERNAL_FILE_PATH + name + INTERNAL_FILE_EXT;
    }

    @Contract(value = "-> fail", pure = false)
    private UIuliiaSchema() {
        throw new UnsupportedOperationException();
    }
}
