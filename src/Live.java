import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirmal on 18/11/2014.
 */
public class Live {

    void getInterfaces() {

        final ObservableList<String> devs =
                FXCollections.observableArrayList();

        List<PcapIf> alldevs = new ArrayList<PcapIf>();
        StringBuilder errbuf = new StringBuilder();

        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices. Errbuff:\n %s", errbuf.toString());
            return;
            //[]// show errors
        }

        Sea.devices=alldevs; // implement on Sea
//        Sea.setDevices(alldevs);

        int i = 0;
        for (PcapIf device : alldevs) {
            devs.add("#"+(i)+": "+
                    ((device.getDescription() != null)
                            ? device.getDescription()
                            : device.getName())
            );
            i++;
        }

        Sea.lst_interfaces.setItems(devs); // implement on Sea
//        Sea.setDeviceList(devs);

        //[]// show 'done'
    }

    void capturePackets(String i) {

        PcapIf device=Sea.devices.get(Integer.parseInt(i));
        StringBuilder errbuf = new StringBuilder();

        int snaplen = 64 * 1024;           // Capture all packets, no trucation
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
        int timeout = 10 * 1000;           // 10 seconds in millis

        Pcap pcap =
                Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

//        return new CaptureThread(pcap);
        Sea.thread=new Thread(new CaptureThread(pcap));
        Sea.thread.start();

////        Extractor ex=new Extractor(pcap);

//        System.out.println(device.getName());
    }

    private class CaptureThread implements Runnable {
        Pcap pcap;
        public CaptureThread(Pcap _pcap) {
            pcap=_pcap;
            Sea.pcap=_pcap;
        }

        @Override
        public void run() {
            /*Extractor ex=*/new Extractor(pcap, Sea.max_count);
            System.out.println("DONE: new Extractor(pcap)");
        }
    }
}
