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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

/**
 * An iuliia.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class Iuliia {

    /**
     * A default word separator.
     */
    @NotNull
    private static final String DEFAULT_SEPARATOR = "\\b";

    /**
     * A word separator format.
     */
    @NotNull
    private static final String SEPARATOR_FORMAT = "((?<=%1$s)|(?=%1%s))";

    /**
     * Transliterate a text using provided word separator and schema.
     *
     * @param text          text to transliterate, may be null
     * @param separator     word separator to use, may be null
     * @param schema        transliteration schema, mustn't be null
     *
     * @return  transliterated text or the same string
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @UnknownNullability
    @Contract(value = "null, _, _ -> param1; !null, _, null -> fail", pure = false)
    public static String transliterate(
            @Nullable
            final String text,

            @Nullable
            String separator,

            @UnknownNullability
            final IuliiaSchema schema
    ) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        separator = UwObject.ifNull(separator, DEFAULT_SEPARATOR);
        separator = String.format(SEPARATOR_FORMAT, separator);

        final StringBuilder sb = new StringBuilder();
        for (String word : text.split(separator)) {
            sb.append(transliterateWord(word, schema));
        }

        return sb.toString();
    }

    /**
     * Transliterate a text using provided word separator and schema.
     *
     * @param text          text to transliterate, may be null
     * @param separator     word separator to use, may be null
     * @param schemaPath    path to transliteration schema, mustn't be null
     *
     * @return  transliterated text or the same string
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @UnknownNullability
    @Contract(value = "_, _, null -> fail", pure = false)
    public static String transliterate(
            @Nullable
            final String text,

            @Nullable
            final String separator,

            @UnknownNullability
            final String schemaPath
    ) {
        return transliterate(text, separator, IuliiaSchema.fromFilePathNoCheck(schemaPath));
    }

    /**
     * Transliterate a text using provided word separator and schema.
     *
     * @param text          text to transliterate, may be null
     * @param separator     word separator to use, may be null
     * @param schemaType    enum type of transliteration schema, mustn't be null
     *
     * @return  transliterated text or the same string
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @UnknownNullability
    @Contract(value = "_, _, null -> fail", pure = false)
    public static String transliterate(
            @Nullable
            final String text,

            @Nullable
            final String separator,

            @UnknownNullability
            final EIuliiaSchema schemaType
    ) {
        return transliterate(text, separator, IuliiaSchema.fromEnumTypeNoCheck(schemaType));
    }

    /**
     * Transliterate a text using provided word separator and schema.
     *
     * @param text      text to transliterate, may be null
     * @param schema    transliteration schema, mustn't be null
     *
     * @return  transliterated text or the same string
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @UnknownNullability
    @Contract(value = "!null, null -> fail", pure = false)
    public static String transliterate(
            @Nullable
            final String text,

            @UnknownNullability
            final IuliiaSchema schema
    ) {
        return transliterate(text, null, schema);
    }

    /**
     * Transliterate a text using provided word separator and schema.
     *
     * @param text          text to transliterate, may be null
     * @param schemaPath    path to transliteration schema, mustn't be null
     *
     * @return  transliterated text or the same string
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @UnknownNullability
    @Contract(value = "_, null -> fail", pure = false)
    public static String transliterate(
            @Nullable
            final String text,

            @UnknownNullability
            final String schemaPath
    ) {
        return transliterate(text, null, schemaPath);
    }

    /**
     * Transliterate a text using provided word separator and schema.
     *
     * @param text          text to transliterate, may be null
     * @param schemaType    enum type of transliteration schema, mustn't be null
     *
     * @return  transliterated text or the same string
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @UnknownNullability
    @Contract(value = "_, null -> fail", pure = false)
    public static String transliterate(
            @Nullable
            final String text,

            @UnknownNullability
            final EIuliiaSchema schemaType
    ) {
        return transliterate(text, null, schemaType);
    }

    /**
     * Transliterate a word using provided schema.
     *
     * @param word      word to transliterate, may be null
     * @param schema    transliteration schema, mustn't be null
     *
     * @return  transliterated word
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @NotNull
    @Contract(value = "_, null -> fail", pure = true)
    private static String transliterateWord(
            @NotNull
            final IuliiaWord word,

            @UnknownNullability
            final IuliiaSchema schema
    ) {
        final String stem = word.getStem();
        final String ending = word.getEnding();

        final String transliteratedEnding = transliterateEnding(ending, schema);
        if (transliteratedEnding == null) {
            return transliterateStem(stem + ending, schema);
        }

        return transliterateStem(stem, schema) + transliteratedEnding;
    }

    /**
     * Transliterate a word using provided schema.
     *
     * @param word      word to transliterate, may be null
     * @param schema    transliteration schema, mustn't be null
     *
     * @return  transliterated word
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @NotNull
    @Contract(value = "_, null -> fail", pure = true)
    private static String transliterateWord(
            @NotNull
            final String word,

            @UnknownNullability
            final IuliiaSchema schema
    ) {
        return transliterateWord(new IuliiaWord(word), schema);
    }

    /**
     * Transliterate a word stem using provided schema.
     *
     * @param stem      word stem to transliterate, mustn't be null
     * @param schema    transliteration schema, mustn't be null
     *
     * @return  transliterated word stem
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @NotNull
    @Contract(value = "_, null -> fail", pure = true)
    private static String transliterateStem(
            @NotNull
            final String stem,

            @UnknownNullability
            final IuliiaSchema schema
    ) {
        final StringBuilder sb = new StringBuilder();

        final int stemLength = stem.length();
        for (int i = 0; i < stemLength; ++i) {
            Character prev = i > 0 ? stem.charAt(i - 1) : null;
            Character curr = stem.charAt(i);
            Character next = i < stemLength - 1 ? stem.charAt(i + 1) : null;

            sb.append(transliterateLetter(prev, curr, next, schema));
        }

        return sb.toString();
    }

    /**
     * Transliterate a word ending using provided schema.
     *
     * @param ending    word ending to transliterate, mustn't be null
     * @param schema    transliteration schema, mustn't be null
     *
     * @return  transliterated word ending or {@code null}
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @Nullable
    @Contract(value = "_, null -> fail", pure = true)
    private static String transliterateEnding(
            @NotNull
            final String ending,

            @UnknownNullability
            final IuliiaSchema schema
    ) {
        return schema.getEndingLetterMap()
                .get(ending);
    }

    /**
     * Transliterate a letter using provided schema.
     *
     * @param prevChar  previous word character, may be null
     * @param currChar  current word character, may be null
     * @param nextChar  next word character, may be null
     * @param schema    transliteration schema, mustn't be null
     *
     * @return  transliterated letter
     *
     * @throws NullPointerException if provided schema is {@code null}
     */
    @NotNull
    @Contract(value = "_, _, _, null -> fail", pure = true)
    private static String transliterateLetter(
            @Nullable
            final Character prevChar,

            @Nullable
            final Character currChar,

            @Nullable
            final Character nextChar,

            @UnknownNullability
            final IuliiaSchema schema
    ) {
        final String prev = UwObject.ifNotNullNoCheck(prevChar, Object::toString, UwString.EMPTY);
        final String curr = UwObject.ifNotNullNoCheck(currChar, Object::toString, UwString.EMPTY);
        final String next = UwObject.ifNotNullNoCheck(nextChar, Object::toString, UwString.EMPTY);

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

        //noinspection DataFlowIssue
        return result;
    }

    @Contract(value = "-> fail", pure = false)
    private Iuliia() {
        throw new UnsupportedOperationException();
    }
}
