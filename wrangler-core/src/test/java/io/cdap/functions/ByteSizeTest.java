package io.cdap.functions;

import org.junit.Assert;
import org.junit.Test;

import io.cdap.wrangler.api.parser.ByteSize;

public class ByteSizeTest {

    @Test
    public void testCandidConversion() throws Exception {
        String input = "10kb";

        ByteSize byteSize = new ByteSize(input);

        Assert.assertEquals(10000, byteSize.getBytes());
        Assert.assertEquals("kb", byteSize.getByteUnits());
    }

}
