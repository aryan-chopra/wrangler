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
 * The {@code ByteSize} class parses and represents a byte size value with its
 * units.
 * It implements the {@code Token} interface to provide standardized access to:
 * <ul>
 * <li>The original string representation</li>
 * <li>The numeric byte size component</li>
 * </ul>
 *
 * <p>
 * This class provides methods to access both the parsed components and the
 * original value, as well as JSON serialization capabilities.
 * </p>
 */
public class ByteSize implements Token {
    /**
     * The original string representation of the byte size.
     */
    private String value;

    /**
     * The numeric byte size component extracted from the value.
     */
    private long bytes;

    /**
     * The factor used for byte size calculations (1000 for decimal-based units).
     */
    private static final long FACTOR = 1000;

    /**
     * Constructs a {@code ByteSize} object by parsing the input string.
     * The string should contain a numeric size followed by byte units.
     *
     * @param value the string representation of byte size (e.g., "1024KB")
     * @throws IllegalArgumentException if the value cannot be properly parsed
     */
    public ByteSize(String value) {
        this.value = value;
        extractBytesAndUnits(value);
    }

    /**
     * Parses the input string to extract the numeric byte size and units.
     * The numeric portion is converted to a long, while the remaining characters
     * are treated as the byte units.
     *
     * @param value the string to parse
     */
    private void extractBytesAndUnits(String value) {
        double tempValue = Double.parseDouble(value.substring(0, value.length() - 2));
        String tempByteUnits = value.substring(value.length() - 2, value.length()).toLowerCase();

        switch(tempByteUnits) {
            case "mb":
                this.bytes = (long) Math.floor(tempValue * FACTOR * FACTOR);
                break;
            case "kb":
                this.bytes = (long) Math.floor(tempValue * FACTOR);
        }
    }

    /**
     * Returns the numeric byte size component.
     *
     * @return the byte size as a long value
     */
    public long getBytes() {
        return this.bytes;
    }

    /**
     * Converts the given bytes to kilobytes.
     *
     * @param bytes the number of bytes to convert
     * @return the equivalent value in kilobytes
     */
    public static double bytesToKiloBytes(double bytes) {
        return (bytes / FACTOR);
    }

    /**
     * Converts the given bytes to megabytes.
     *
     * @param bytes the number of bytes to convert
     * @return the equivalent value in megabytes
     */
    public static double bytesToMegaBytes(double bytes) {
        return (bytes / (FACTOR * FACTOR));
    }

    /**
     * Returns the original string representation of this byte size.
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
     * @return {@code TokenType.BYTE_SIZE}
     */
    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    /**
     * Converts this byte size to its JSON representation.
     * The JSON object contains:
     * <ul>
     * <li>The token type ("BYTE_SIZE")</li>
     * <li>The original string value</li>
     * </ul>
     *
     * @return a {@code JsonElement} representing this byte size
     */
    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.BYTE_SIZE.name());
        object.addProperty("value", this.value);
        return object;
    }
}
