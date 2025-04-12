package io.cdap.directives.aggregates;

import io.cdap.wrangler.TestingRig;
import io.cdap.wrangler.api.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TimeAndByteDirectiveTest {
    @Test
    public void testAggregateTotal() throws Exception {
        String[] recipe = new String[] {
                "aggregate-stats :byte_size :time_duration total_bytes total_duration"
        };

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "2s"));
        rows.add(new Row("byte_size", "1kb").add("time_duration", "4s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        Assert.assertEquals(1, result.size());
    }
}
