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

package io.cdap.directives.aggregates;

import io.cdap.wrangler.TestingRig;
import io.cdap.wrangler.api.Row;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TimeAndByteDirectiveTest {
    @Test
    public void testAggregateTotal() throws Exception {
        String[] recipe = new String[] {
                "aggregate-stats :byte_size :time_duration total_bytes mb total_duration"
        };

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "2s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "4s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        Assert.assertEquals(1, result.size());
    }
}
