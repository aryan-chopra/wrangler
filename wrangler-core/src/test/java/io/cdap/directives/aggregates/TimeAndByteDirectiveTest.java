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

/**
 * Unit test class for validating the functionality of the {@code aggregate-stats} directive
 * that aggregates byte sizes and time durations. This directive is used to calculate total
 * values from mixed units (e.g., KB, MB for bytes and ms, s for durations), and output
 * them in a consistent target unit.
 *
 * <p>This test class includes various scenarios such as:
 * <ul>
 *   <li>Default unit aggregation</li>
 *   <li>Explicit output units for time (ms, s)</li>
 *   <li>Explicit output units for bytes (kb, mb)</li>
 * </ul>
 */
public class TimeAndByteDirectiveTest {

    /**
     * Tests that the directive produces exactly one output row when aggregating multiple rows.
     */
    @Test
    public void testSingleOutputRow() throws Exception {
        String[] recipe = new String[]{"aggregate-stats :byte_size :time_duration total_bytes total_duration"};
        double expected_bytes = 2.001d;
        double expected_duration = 14d;

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "9s"));
        rows.add(new Row("byte_size", "1000kb").add("time_duration", "4s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        Assert.assertEquals(1, result.size());
    }

    /**
     * Tests aggregation without specifying output units.
     * Ensures that all byte and time units are normalized correctly to default units (MB and seconds).
     */
    @Test
    public void testAggregateWithoutUnits() throws Exception {
        String[] recipe = new String[]{"aggregate-stats :byte_size :time_duration total_bytes total_duration"};
        double expected_bytes = 2.001d;
        double expected_duration = 14d;

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "9s"));
        rows.add(new Row("byte_size", "1000kb").add("time_duration", "4s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        double actual_bytes = (double) result.get(0).getValue("total_bytes");
        double actual_duration = (double) result.get(0).getValue("total_duration");

        Assert.assertEquals(expected_bytes, actual_bytes, 0.01);
        Assert.assertEquals(expected_duration, actual_duration, 0.01);
    }

    /**
     * Tests aggregation with explicit time output unit in seconds.
     */
    @Test
    public void testAggregateSeconds() throws Exception {
        String[] recipe = new String[]{"aggregate-stats :byte_size :time_duration total_bytes total_duration s"};
        double expected_bytes = 1.002d;
        double expected_duration = 7d;

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "2s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "4s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        double actual_bytes = (double) result.get(0).getValue("total_bytes");
        double actual_duration = (double) result.get(0).getValue("total_duration");

        Assert.assertEquals(expected_bytes, actual_bytes, 0.01);
        Assert.assertEquals(expected_duration, actual_duration, 0.01);
    }

    /**
     * Tests aggregation with explicit time output unit in milliseconds.
     */
    @Test
    public void testAggregateMilliSeconds() throws Exception {
        String[] recipe = new String[]{"aggregate-stats :byte_size :time_duration total_bytes total_duration ms"};
        double expected_bytes = 1.002d;
        double expected_duration = 7000d;

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "2s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "4s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        double actual_bytes = (double) result.get(0).getValue("total_bytes");
        double actual_duration = (double) result.get(0).getValue("total_duration");

        Assert.assertEquals(expected_bytes, actual_bytes, 0.01);
        Assert.assertEquals(expected_duration, actual_duration, 0.01);
    }

    /**
     * Tests aggregation with output byte unit set to megabytes.
     */
    @Test
    public void testAggregateMegabytes() throws Exception {
        String[] recipe = new String[]{"aggregate-stats :byte_size :time_duration total_bytes mb total_duration"};
        double expected_bytes = 1.002d;
        double expected_duration = 7d;

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "2s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "4s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        double actual_bytes = (double) result.get(0).getValue("total_bytes");
        double actual_duration = (double) result.get(0).getValue("total_duration");

        Assert.assertEquals(expected_bytes, actual_bytes, 0.01);
        Assert.assertEquals(expected_duration, actual_duration, 0.01);
    }

    /**
     * Tests aggregation with output byte unit set to kilobytes.
     */
    @Test
    public void testAggregateKilobytes() throws Exception {
        String[] recipe = new String[]{"aggregate-stats :byte_size :time_duration total_bytes kb total_duration"};
        double expected_bytes = 1002d;
        double expected_duration = 7d;

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "2s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "4s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        double actual_bytes = (double) result.get(0).getValue("total_bytes");
        double actual_duration = (double) result.get(0).getValue("total_duration");

        Assert.assertEquals(expected_bytes, actual_bytes, 0.01);
        Assert.assertEquals(expected_duration, actual_duration, 0.01);
    }
}