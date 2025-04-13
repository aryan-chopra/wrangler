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

import io.cdap.wrangler.api.parser.ByteSize;

/**
 * Test class for {@link ByteSize} functionality.
 * Verifies correct parsing and conversion of byte size strings with:
 * <ul>
 *   <li>Kilobyte ("kb") units</li>
 *   <li>Megabyte ("mb") units</li>
 *   <li>Integer and decimal values</li>
 * </ul>
 *
 * <p>Tests validate that string representations are correctly converted to
 * their equivalent byte counts using decimal-based units (1kb = 1000 bytes).</p>
 */
public class ByteSizeTest {

    /**
     * Tests kilobyte unit parsing functionality.
     * Verifies that:
     * <ul>
     *   <li>Integer kilobyte values are correctly converted</li>
     *   <li>Decimal kilobyte values are correctly converted</li>
     *   <li>Values use decimal-based conversion (1000 bytes per kb)</li>
     * </ul>
     *
     * @throws Exception if any parsing error occurs
     */
    @Test
    public void testKiloBytes() throws Exception {
        String kb = "10kb";
        String decimalKb = "5.9kb";

        ByteSize kbSize = new ByteSize(kb);
        ByteSize decimalSize = new ByteSize(decimalKb);

        Assert.assertEquals(10000, kbSize.getBytes());
        Assert.assertEquals(5900, decimalSize.getBytes());
    }

    /**
     * Tests megabyte unit parsing functionality.
     * Verifies that:
     * <ul>
     *   <li>Integer megabyte values are correctly converted</li>
     *   <li>Decimal megabyte values are correctly converted</li>
     *   <li>Values use decimal-based conversion (1000000 bytes per mb)</li>
     * </ul>
     *
     * @throws Exception if any parsing error occurs
     */
    @Test
    public void testMegaBytes() throws Exception {
        String mb = "15mb";
        String decimalMb = "5.94mb";

        ByteSize mbSize = new ByteSize(mb);
        ByteSize decimalSize = new ByteSize(decimalMb);

        Assert.assertEquals(15_000_000, mbSize.getBytes());
        Assert.assertEquals(5_940_000, decimalSize.getBytes());
    }

    /**
     * Tests conversion from bytes to kilobytes.
     * Verifies that:
     * <ul>
     *   <li>1000 bytes is correctly converted to 1.0 kilobyte</li>
     *   <li>5500 bytes is correctly converted to 5.5 kilobytes</li>
     *   <li>0 bytes is converted to 0.0 kilobyte</li>
     *   <li>100 bytes is converted to 0.1 kilobyte</li>
     * </ul>
     */
    @Test
    public void testBytesToKiloBytes() {
        Assert.assertEquals(1.0, ByteSize.bytesToKiloBytes(1000), 0.001);
        Assert.assertEquals(5.5, ByteSize.bytesToKiloBytes(5500), 0.001);
        Assert.assertEquals(0.0, ByteSize.bytesToKiloBytes(0), 0.001);
        Assert.assertEquals(0.1, ByteSize.bytesToKiloBytes(100), 0.001);
    }

    /**
     * Tests conversion from bytes to megabytes.
     * Verifies that:
     * <ul>
     *   <li>1,000,000 bytes is correctly converted to 1.0 megabyte</li>
     *   <li>2,500,000 bytes is correctly converted to 2.5 megabytes</li>
     *   <li>0 bytes is converted to 0.0 megabyte</li>
     *   <li>1000 bytes is converted to 0.001 megabyte</li>
     * </ul>
     */
    @Test
    public void testBytesToMegaBytes() {
        Assert.assertEquals(1.0, ByteSize.bytesToMegaBytes(1_000_000), 0.001);
        Assert.assertEquals(2.5, ByteSize.bytesToMegaBytes(2_500_000), 0.001);
        Assert.assertEquals(0.0, ByteSize.bytesToMegaBytes(0), 0.001);
        Assert.assertEquals(0.001, ByteSize.bytesToMegaBytes(1000), 0.001);
    }
}
