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

    // show list interfaces to select
    void getInterfaces() {

        // observable list for the GUI
        final ObservableList<String> devs =
                FXCollections.observableArrayList();

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

        // add each device (interface) to the observable list
        int i = 0;
        for (PcapIf device : alldevs) {
            devs.add("#"+(i)+": "+
                    ((device.getDescription() != null)
                            ? device.getDescription()
                            : device.getName())
            );
            i++;
        }

        // show the list in GUI
        Sea.lst_interfaces.setItems(devs);

    }

    // start capturing for selected interface
    void capturePackets(String i) {

        // get reference to the interface
        PcapIf device=Sea.devices.get(Integer.parseInt(i));
        StringBuilder errbuf = new StringBuilder();

        int snaplen = 64 * 1024;           // Capture all packets, no trucation
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
        int timeout = 10 * 1000;           // 10 seconds in millis

        // open interface for live capture
        Pcap pcap =
                Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

        // start capturing in a new thread
        Sea.thread=new Thread(new CaptureThread(pcap));
        Sea.thread.start();

    }

    // thread for live-capturing
    private class CaptureThread implements Runnable {
        Pcap pcap;

        // thread constructor (initializer)
        public CaptureThread(Pcap _pcap) {
            pcap=_pcap;
            // expose pcap (in memory) to Sea
            Sea.pcap=_pcap;
        }

        // start handling the capture
        @Override
        public void run() {
            // initialize and start the data extraction
            new Extractor(pcap, Sea.max_count);
        }
    }
}
