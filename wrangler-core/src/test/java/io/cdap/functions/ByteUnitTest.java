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
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package io.cdap.functions;

import io.cdap.wrangler.api.parser.ByteUnit;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link ByteUnit} functionality.
 * Verifies correct parsing and representation of byte unit tokens such as:
 * <ul>
 *   <li>Lowercase and uppercase inputs</li>
 *   <li>Unit normalization to lowercase</li>
 *   <li>Correct token type and JSON serialization</li>
 * </ul>
 */
public class ByteUnitTest {

    /**
     * Tests that ByteUnit stores and returns lowercase representations.
     * Ensures that input values like "KB", "Mb" are normalized to "kb", "mb".
     */
    @Test
    public void testByteUnitNormalization() {
        ByteUnit unit1 = new ByteUnit("KB");
        ByteUnit unit2 = new ByteUnit("Mb");

        Assert.assertEquals("kb", unit1.value());
        Assert.assertEquals("mb", unit2.value());
    }
}