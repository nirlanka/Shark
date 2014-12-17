import org.junit.Test;

import static org.junit.Assert.*;

public class Extractor_test {

    // !! Extractor doesn't have methods :(

    @Test
    public void testPackets() throws Exception {
        // capturePackets() of Live_test
        Live_test live_test=new Live_test();
        live_test.capturePackets();

        assertEquals(0,Sea.packets.size());
    }
}