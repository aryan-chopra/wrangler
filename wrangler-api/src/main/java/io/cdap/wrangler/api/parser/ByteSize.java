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

//Sample comment
public class ByteSize implements Token {
    private String value;
    private long bytes;
    private String byteUnits;

    public ByteSize(String value) {
        this.value = value;
        extractBytesAndUnits(value);
    }

    private void extractBytesAndUnits(String value) {
        int index = 0;

        while (index < value.length() && Character.isDigit(value.charAt(index))) {
            this.bytes = (this.bytes * 10) + Character.getNumericValue(value.charAt(index));
            index++;
        }

        StringBuilder tempByteUnits = new StringBuilder();
        while (index < value.length()) {
            tempByteUnits.append(value.charAt(index));
        }
        this.byteUnits = tempByteUnits.toString();
    }

    public long getBytes() {
        return this.bytes;
    }

    public String getByteUnits() {
        return this.byteUnits;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.BYTE_SIZE.name());
        object.addProperty("value", this.value);
        return object;
    }
}
