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

        Assert.assertEquals(10000, kb.getBytes());
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
