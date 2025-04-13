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

import org.junit.Assert;
import org.junit.Test;

import io.cdap.wrangler.api.parser.AggregateType;

/**
 * Test class for {@link AggregateType} case conversion functionality.
 * Verifies correct handling of case normalization for aggregate type strings:
 * <ul>
 *   <li>Lowercase input preservation</li>
 *   <li>Mixed-case input conversion</li>
 *   <li>Uppercase input conversion</li>
 * </ul>
 *
 * <p>Tests validate that all string representations are consistently converted
 * to lowercase regardless of input case, ensuring case-insensitive behavior
 * in aggregate operations.</p>
 */
public class AggregateTypeTest {

    /**
     * Tests lowercase input preservation.
     * Verifies that:
     * <ul>
     *   <li>Already lowercase "sum" remains unchanged</li>
     *   <li>Already lowercase "average" remains unchanged</li>
     *   <li>The original case is not preserved</li>
     * </ul>
     */
    @Test
    public void testLowerCase() {
        String sum = "sum";
        String average = "average";

        AggregateType sumType = new AggregateType(sum);
        AggregateType averageType = new AggregateType(average);

        Assert.assertEquals("sum", sumType.value());
        Assert.assertEquals("average", averageType.value());
    }

    /**
     * Tests mixed-case input conversion.
     * Verifies that:
     * <ul>
     *   <li>"sUm" is normalized to "sum"</li>
     *   <li>"avErAge" is normalized to "average"</li>
     *   <li>Partial uppercase is properly handled</li>
     * </ul>
     */
    @Test
    public void testMixedCase() {
        String sum = "sUm";
        String average = "avErAge";

        AggregateType sumType = new AggregateType(sum);
        AggregateType averageType = new AggregateType(average);

        Assert.assertEquals("sum", sumType.value());
        Assert.assertEquals("average", averageType.value());
    }

    /**
     * Tests uppercase input conversion.
     * Verifies that:
     * <ul>
     *   <li>"SUM" is normalized to "sum"</li>
     *   <li>"AVERAGE" is normalized to "average"</li>
     *   <li>Full uppercase is properly handled</li>
     * </ul>
     */
    @Test
    public void testUpperCase() {
        String sum = "SUM";
        String average = "AVERAGE";

        AggregateType sumType = new AggregateType(sum);
        AggregateType averageType = new AggregateType(average);

        Assert.assertEquals("sum", sumType.value());
        Assert.assertEquals("average", averageType.value());
    }
}