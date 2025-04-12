package io.cdap.functions;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {

    @Test
    public void testSeonds() throws Exception {
        String seconds = "5s";
        String decimalSeconds = "10.2s";

        TimeDuration secondsTimeDuration = new TimeDuration(seconds);
        TimeDuration decimalSecondsTimeDuration = new TimeDuration(decimalSeconds);

        Assert.assertEquals(5_000_000_000L, secondsTimeDuration.getTime());
        Assert.assertEquals(10_200_000_000L, decimalSecondsTimeDuration.getTime());
    }

    @Test
    public void testMilliseconds() throws Exception {
        String milli = "9ms";
        String decimalMilli = "5.95ms";

        TimeDuration milliDuration = new TimeDuration(milli);
        TimeDuration decimalDuration = new TimeDuration(decimalMilli);

        Assert.assertEquals(9_000_000L, milliDuration.getTime());
        Assert.assertEquals(5_950_000L, decimalDuration.getTime());
    }
}
