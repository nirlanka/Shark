import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Live_test {

    @Test
    public void getInterfaces() throws Exception {
        Live live=new Live();
//        live.getInterfaces(); // can't use: dependent on FX

        /////// from Live().getInterfaces() ///////

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

        ///////////////////////////////////////////

        assertNotNull(Sea.devices);
//        assertNotNull(Sea.lst_interfaces); // can't test without gui
    }

    @Test
    public void capturePackets() throws Exception {
        Live live=new Live();

        /////// from Live().getInterfaces() ///////

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

        ///////////////////////////////////////////

        assertFalse(r == Pcap.NOT_OK || alldevs.isEmpty());

        //

        live.capturePackets("1");

        assertNotNull(Sea.pcap);
    }
}