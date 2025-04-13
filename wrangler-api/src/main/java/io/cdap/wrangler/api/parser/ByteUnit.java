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
 * The {@code ByteUnit} class represents a byte size measurement unit token.
 * It implements the {@code Token} interface to provide standardized access to:
 * <ul>
 * <li>The unit string value (e.g., "kb", "mb")</li>
 * <li>The token type information</li>
 * <li>JSON serialization capabilities</li>
 * </ul>
 *
 * <p>
 * This class normalizes unit values to lowercase and provides methods to access
 * both the unit value and its JSON representation.
 * </p>
 */
public class ByteUnit implements Token {
    /**
     * The string representation of the byte unit in lowercase.
     * Valid values include "kb", "mb"
     */
    private String value;

    /**
     * Constructs a {@code ByteUnit} object with the specified unit value.
     * The input value is converted to lowercase to ensure case-insensitive handling.
     *
     * @param value the string representation of the byte unit (e.g., "KB", "Mb")
     */
    public ByteUnit(String value) {
        this.value = value.toLowerCase();
    }

    /**
     * Returns the normalized string representation of this byte unit.
     * The returned value will always be in lowercase.
     *
     * @return the byte unit string in lowercase
     */
    @Override
    public Object value() {
        return this.value;
    }

    /**
     * Returns the token type for this class.
     *
     * @return {@code TokenType.BYTE_UNIT}
     */
    @Override
    public TokenType type() {
        return TokenType.BYTE_UNIT;
    }

    /**
     * Converts this byte unit to its JSON representation.
     * The JSON object contains:
     * <ul>
     * <li>The token type ("BYTE_UNIT")</li>
     * <li>The normalized unit string value (lowercase)</li>
     * </ul>
     *
     * @return a {@code JsonElement} representing this byte unit
     */
    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.BYTE_UNIT.name());
        object.addProperty("value", this.value);
        return object;
    }
}
