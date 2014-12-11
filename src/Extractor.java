import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Extractor {

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

        int i = 0;
        for (PcapIf device : alldevs) {
            devs.add("#"+(i-1)+": "+
                            ((device.getDescription() != null)
                                    ? device.getDescription()
                                    : device.getName())
            );
        }

        Sea.lst_interfaces.setItems(devs);

        //[]// show 'done'
    }

    int COUNT;
    public Extractor(Pcap pcap, int _COUNT) {
        COUNT=_COUNT;

        int count=Sea.max_count, user_count=0;
        if (!Sea.txt_count.getText().equals(""))
            user_count=Integer.parseInt(Sea.txt_count.getText());
        if (Sea.chk_count.isSelected() && count>user_count)
            count=user_count;

//        n_packets_all=Sea.txt

        Sea.n_packets_all=0; Sea.n_http=0; Sea.n_tcp_other=0; Sea.n_udp=0; Sea.n_packets_unknown=0;
        /*s_packets_all=0;*/ Sea.s_http=0; Sea.s_tcp_other=0; Sea.s_udp=0/*; s_packets_unknown=0*/;

        Sea.packets.clear();
        pcap.loop(count, packetHandler, Sea.packets);
        pcap.close();

//        // view
//        Projector projector=new Projector(Sea.packets);
//        projector.setFilters();
//        projector.showFiltered();
//
//        // view stats
//        Sea.count_http.setText(n_http+"");
//        Sea.count_tcp.setText(n_tcp_other+"");
//        Sea.count_udp.setText(n_udp+"");
//        Sea.size_http.setText(s_http+"");
//        Sea.size_tcp.setText(s_tcp_other+"");
//        Sea.size_udp.setText(s_udp+"");

    }

    PcapPacketHandler<ArrayList> packetHandler=new PcapPacketHandler<ArrayList>() {

        final Ip4 ip=new Ip4();
        final Tcp tcp=new Tcp();
        final Http http=new Http();
        final Udp udp=new Udp();
        final Icmp icmp=new Icmp();

        @Override
        public void nextPacket(PcapPacket pcapPacket, ArrayList arrayList) {

            Sea.printCapCountUp();

            if (pcapPacket.hasHeader(ip)) {

                Packet packet=new Packet();
                packet.setSource(""+ FormatUtils.ip(ip.source()));
                packet.setDestination("" + FormatUtils.ip(ip.destination()));

                PcapHeader header=pcapPacket.getCaptureHeader();
                int size=header.wirelen();
                packet.setSize(size);
                if (pcapPacket.hasHeader(tcp)) {
                    if (pcapPacket.hasHeader(http)) {

                        packet.setSourceport(tcp.source());
                        packet.setDestport(tcp.destination());
                        packet.setType(Sea.HTTP);
                        Sea.n_http++;
                        Sea.s_http+=size;
                    } else if (pcapPacket.hasHeader(icmp)) {

                        packet.setSourceport(tcp.source());
                        packet.setDestport(tcp.destination());
                        packet.setType(Sea.ICMP);
//                        Sea.n_http++;
//                        Sea.s_http+=size;
                    } else {
                        packet.setSourceport(tcp.source());
                        packet.setDestport(tcp.destination());
                        packet.setType(Sea.TCP);
                        Sea.n_tcp_other++;
                        Sea.s_tcp_other+=size;
                    }
                } else if (pcapPacket.hasHeader(udp)) {
                    packet.setSourceport(udp.source());
                    packet.setDestport(udp.destination());
                    packet.setType(Sea.UDP);
                    Sea.n_udp++;
                    Sea.s_udp+=size;
                }

//                System.out.println(packet.toString());

                Sea.packets.add(Sea.packets.size(), packet);

            } else {
                Sea.n_packets_unknown++;
//                s_packets_unknown+=size;

                /* #AllTypes */
                Packet packet=new Packet();

                PcapHeader header=pcapPacket.getCaptureHeader();
                int size=header.wirelen();
                packet.setSize(size);
                Sea.s_non_ip+=size;

                Sea.packets.add(Sea.packets.size(), packet);
            }

            Sea.n_packets_all++;
//            s_packets_all+=size;

            if (Sea.n_packets_all>=COUNT)
                try {
                    Sea.pcap.breakloop();
                    System.out.println("COUNT");
                } catch (org.jnetpcap.PcapClosedException e) {
                    System.out.println(":p");
                }
        }
    };
}
