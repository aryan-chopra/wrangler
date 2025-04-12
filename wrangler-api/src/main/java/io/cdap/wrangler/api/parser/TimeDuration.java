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
 * The TimeDuration class parses and represents a time duration value with its
 * units.
 * An object of type {@code TimeDuration} contains:
 * <ul>
 * <li>The original string value</li>
 * <li>The numeric duration component</li>
 * <li>The time units component</li>
 * </ul>
 *
 * <p>
 * This class provides methods to access the parsed duration value and units,
 * as well as the original string representation.
 * </p>
 */
public class TimeDuration implements Token {
    /**
     * The original string representation of the time duration.
     */
    private String value;

    /**
     * The numeric duration component extracted from the value.
     */
    private long duration;

    /**
     * Constructs a TimeDuration by parsing the input string value.
     * The value should contain a numeric duration followed by time units.
     *
     * @param value the string representation of the time duration (e.g., "100ms")
     */
    public TimeDuration(String value) {
        this.value = value;
        extractTimeAndUnits(value);
    }

    /**
     * Parses the input string to extract the numeric duration and time units.
     * The numeric portion is converted to a long, while the remaining characters
     * are treated as the time units.
     *
     * @param value the string to parse
     */
    private void extractTimeAndUnits(String value) {
        int unitStartIndex = 0;
        if (Character.isDigit(value.charAt(value.length() - 2)) == false) {
            unitStartIndex = value.length() - 2;
        } else {
            unitStartIndex = value.length() - 1;
        }

        double tempTimeDuration = Double.parseDouble(value.substring(0, unitStartIndex));
        String tempTimeUnits = value.substring(unitStartIndex, value.length()).toLowerCase();

        switch (tempTimeUnits) {
            case "s":
                this.duration = (long)(tempTimeDuration * 1_000_000_000L);
                break;
            case "ms":
                this.duration = (long)(tempTimeDuration * 1_000_000L);
        }
    }

    /**
     * Returns the numeric duration component.
     *
     * @return the duration as a long value
     */
    public long getTime() {
        return this.duration;
    }

    /**
     * Returns the original string representation of this time duration.
     *
     * @return the original string value
     */
    @Override
    public String value() {
        return this.value;
    }

    /**
     * Returns the token type for this class.
     *
     * @return {@code TokenType.TIME_DURATION}
     */
    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    /**
     * Converts this time duration to its JSON representation.
     * The JSON object contains:
     * <ul>
     * <li>The token type ("TIME_DURATION")</li>
     * <li>The original string value</li>
     * </ul>
     *
     * @return a JsonElement representing this time duration
     */
    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.TIME_DURATION.name());
        object.addProperty("value", this.value);
        return object;
    }
}
