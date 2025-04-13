/*
 *  Copyright © 2017-2019 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The {@code TimeUnit} class represents a time measurement unit token that implements
 * the {@code Token} interface. It provides:
 * <ul>
 *   <li>Case-insensitive handling of time units (e.g., "ms", "s")</li>
 *   <li>Standardized access to the unit value</li>
 *   <li>JSON serialization capabilities</li>
 *   <li>Type information through {@code TokenType.TIME_UNIT}</li>
 * </ul>
 *
 * <p>This class normalizes all time units to lowercase for consistent processing
 * throughout the application. Supported units typically include milliseconds ("ms"),
 * seconds ("s").</p>
 */
public class TimeUnit implements Token {

    /**
     * The normalized (lowercase) string representation of the time unit.
     * Examples include "ms" for milliseconds.
     */
    private String value;

    /**
     * Constructs a new {@code TimeUnit} with the specified time unit string.
     * The input value is converted to lowercase to ensure case-insensitive handling.
     *
     * @param value the time unit string to be stored (e.g., "MS", "S")
     */
    public TimeUnit(String value) {
        this.value = value.toLowerCase();
    }

    /**
     * Returns the normalized (lowercase) time unit string.
     *
     * @return the time unit in lowercase (e.g., "ms", "s")
     */
    @Override
    public Object value() {
        return this.value;
    }


    /**
     * Returns the token type identifier for time units.
     *
     * @return {@code TokenType.TIME_UNIT}
     */
    @Override
    public TokenType type() {
        return TokenType.TIME_UNIT;
    }

    /**
     * Converts the time unit to a JSON representation containing:
     * <ul>
     *   <li>The token type ("TIME_UNIT")</li>
     *   <li>The normalized time unit value</li>
     * </ul>
     *
     * @return a {@code JsonElement} containing the serialized time unit data
     */
    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.TIME_UNIT.name());
        object.addProperty("value", this.value);
        return object;
    }
}
