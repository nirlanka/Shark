import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestLive {

    @Test
    public void testGetInterfaces() throws Exception {
        Live live=new Live();
//        live.getInterfaces(); // can't use: dependent on FX

        assertNotNull(Sea.devices);
        assertNotNull(Sea.lst_interfaces);
    }

    @Test
    public void testCapturePackets() throws Exception {
        Live live=new Live();

        /////// from Live().getInterfaces()

        // placeholder for device list
        List<PcapIf> alldevs = new ArrayList<PcapIf>();
        StringBuilder errbuf = new StringBuilder();

        // fill it
        int r = Pcap.findAllDevs(alldevs, errbuf);

        // on errors
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices. Errbuff:\n %s", errbuf.toString());
            return;
        }

        // expose to Sea
        Sea.devices=alldevs;

        ///////////////////////////////////

        assertFalse(r==Pcap.NOT_OK||alldevs.isEmpty());

        live.capturePackets("1");
    }
}