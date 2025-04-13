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

public class ByteSizeTest {

    @Test
    public void testKiloBytes() throws Exception {
        String kb = "10kb";
        String decimalKb = "5.9kb";

        ByteSize kbSize = new ByteSize(kb);
        ByteSize decimalSize = new ByteSize(decimalKb);

        Assert.assertEquals(10000, kbSize.getBytes());
        Assert.assertEquals(5900, decimalSize.getBytes());
    }

    @Test
    public void testMegaBytes() throws Exception {
        String mb = "15mb";
        String decimalMb = "5.94mb";

        ByteSize mbSize = new ByteSize(mb);
        ByteSize decimalSize = new ByteSize(decimalMb);

        Assert.assertEquals(15_000_000, mbSize.getBytes());
        Assert.assertEquals(5_940_000, decimalSize.getBytes());
    }
}
