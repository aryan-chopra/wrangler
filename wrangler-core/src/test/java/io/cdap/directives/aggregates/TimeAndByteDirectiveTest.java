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
                "aggregate-stats :byte_size :time_duration total_bytes total_duration"
        };

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row("byte_size", "1mb").add("time_duration", "1s"));

        List<Row> result = TestingRig.execute(recipe, rows);

        Assert.assertEquals(1, result.size());
    }
}
