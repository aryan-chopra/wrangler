package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TimeDuration implements Token {
    private String value;
    private long duration;
    private String timeUnits;

    public TimeDuration(String value) {
        this.value = value;
        extractTimeAndUnits(value);
    }

    private void extractTimeAndUnits(String value) {
        int index = 0;

        while (index < value.length() && Character.isDigit(value.charAt(index))) {
            this.duration= (this.duration* 10) + Character.getNumericValue(value.charAt(index));
            index++;
        }

        StringBuilder tempByteUnits = new StringBuilder();
        while (index < value.length()) {
            tempByteUnits.append(value.charAt(index));
        }
        this.timeUnits = tempByteUnits.toString();
    }

    public long getTime() {
        return this.duration;
    }

    public String getTimeUnits() {
        return this.timeUnits;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.TIME_DURATION.name());
        object.addProperty("value", this.value);
        return object;
    }
}
