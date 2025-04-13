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
 * The {@code AggregateType} class represents an aggregation type value in a normalized
 * (lowercase) form. It implements the {@code Token} interface to provide standardized
 * access to:
 * <ul>
 * <li>The original string representation (converted to lowercase)</li>
 * <li>The token type information</li>
 * </ul>
 *
 * <p>
 * This class provides methods to access the stored value and supports JSON serialization
 * of the token information.
 * </p>
 */
public class AggregateType implements Token {
    /**
     * The string representation of the aggregate type, stored in lowercase.
     */
    private String value;

    /**
     * Constructs an {@code AggregateType} object with the specified value.
     * The input value is converted to lowercase during construction.
     *
     * @param value the string representation of the aggregate type
     */
    public AggregateType(String value) {
        this.value = value.toLowerCase();
    }

    /**
     * Returns the aggregate type value in lowercase.
     *
     * @return the lowercase string representation of the aggregate type
     */
    @Override
    public Object value() {
        return this.value;
    }

    /**
     * Returns the token type for this class.
     *
     * @return {@code TokenType.AGGREGATE_TYPE}
     */
    @Override
    public TokenType type() {
        return TokenType.AGGREGATE_TYPE;
    }

    /**
     * Converts this aggregate type to its JSON representation.
     * The JSON object contains:
     * <ul>
     * <li>The token type ("AGGREGATE_TYPE")</li>
     * <li>The lowercase string value</li>
     * </ul>
     *
     * @return a {@code JsonElement} representing this aggregate type
     */
    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.AGGREGATE_TYPE.name());
        object.addProperty("value", this.value);
        return object;
    }
}
