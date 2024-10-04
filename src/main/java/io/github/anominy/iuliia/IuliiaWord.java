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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An iuliia word.
 */
@SuppressWarnings("unused")
final class IuliiaWord {
    private static final int ENDING_LENGTH = 2;

    /**
     * A word steam.
     */
    @NotNull
    private final String stem;

    /**
     * A word ending.
     */
    @NotNull
    private final String ending;

    /**
     * Initialize an {@link IuliiaWord} instance.
     *
     * @param word  word
     */
    @Contract(pure = true)
    public IuliiaWord(
            @Nullable
            String word
    ) {
        word = UwObject.ifNull(word, "");

        String stem = word;
        String ending = "";

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
     * @return  word stem
     */
    @NotNull
    @Contract(pure = true)
    public String getStem() {
        return this.stem;
    }

    /**
     * Get this word ending.
     *
     * @return  word ending
     */
    @NotNull
    @Contract(pure = true)
    public String getEnding() {
        return this.ending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    @Contract(pure = true)
    public String toString() {
        return this.stem + this.ending;
    }
}
