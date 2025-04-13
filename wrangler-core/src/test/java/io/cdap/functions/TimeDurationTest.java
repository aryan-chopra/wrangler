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


package io.cdap.functions;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link TimeDuration} functionality.
 * Verifies correct parsing and conversion of time duration strings with:
 * <ul>
 *   <li>Second ("s") units</li>
 *   <li>Millisecond ("ms") units</li>
 *   <li>Both integer and decimal values</li>
 * </ul>
 *
 * <p>Tests validate that string representations are correctly converted to
 * their equivalent nanosecond durations, with proper handling of unit
 * conversions and decimal precision.</p>
 */
public class TimeDurationTest {

    /**
     * Tests second unit parsing functionality.
     * Verifies that:
     * <ul>
     *   <li>Integer second values are correctly converted to nanoseconds</li>
     *   <li>Decimal second values maintain proper precision</li>
     *   <li>Conversion uses standard time units (1s = 1,000,000,000ns)</li>
     * </ul>
     *
     * @throws Exception if any parsing error occurs
     */
    @Test
    public void testSeonds() throws Exception {
        String seconds = "5s";
        String decimalSeconds = "10.2s";

        TimeDuration secondsTimeDuration = new TimeDuration(seconds);
        TimeDuration decimalSecondsTimeDuration = new TimeDuration(decimalSeconds);

        Assert.assertEquals(5_000_000_000L, secondsTimeDuration.getTime());
        Assert.assertEquals(10_200_000_000L, decimalSecondsTimeDuration.getTime());
    }

    /**
     * Tests millisecond unit parsing functionality.
     * Verifies that:
     * <ul>
     *   <li>Integer millisecond values are correctly converted to nanoseconds</li>
     *   <li>Decimal millisecond values maintain proper precision</li>
     *   <li>Conversion uses standard time units (1ms = 1,000,000ns)</li>
     * </ul>
     *
     * @throws Exception if any parsing error occurs
     */
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
