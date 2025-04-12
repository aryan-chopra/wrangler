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
