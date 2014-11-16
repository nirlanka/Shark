import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import java.util.ArrayList;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Extractor {
    public Extractor(Pcap pcap) {

        Sea.packets.clear();
        pcap.loop(Pcap.LOOP_INFINITE, packetHandler, Sea.packets);
        pcap.close();

        // view
        Projector projector=new Projector(Sea.packets);
        projector.setFilters();
        projector.showFiltered();

        // view stats
        Sea.count_http.setText(n_http+"");
        Sea.count_tcp.setText(n_tcp_other+"");
        Sea.count_udp.setText(n_udp+"");
        Sea.size_http.setText(s_http+"");
        Sea.size_tcp.setText(s_tcp_other+"");
        Sea.size_udp.setText(s_udp+"");
    }

    int n_packets_all=0, n_http=0, n_tcp_other=0, n_udp=0, n_packets_unknown=0,
        /*s_packets_all=0,*/ s_http=0, s_tcp_other=0, s_udp=0/*, s_packets_unknown=0*/;

    PcapPacketHandler<ArrayList> packetHandler=new PcapPacketHandler<ArrayList>() {

        final Ip4 ip=new Ip4();
        final Tcp tcp=new Tcp();
        final Http http=new Http();
        final Udp udp=new Udp();

        @Override
        public void nextPacket(PcapPacket pcapPacket, ArrayList arrayList) {

            if (pcapPacket.hasHeader(ip)) {

                Packet packet=new Packet();
                packet.setSource(""+ FormatUtils.ip(ip.source()));
                packet.setDestination("" + FormatUtils.ip(ip.destination()));

                PcapHeader header=pcapPacket.getCaptureHeader();
                int size=header.wirelen();
                packet.setSize(size);
                if (pcapPacket.hasHeader(tcp)) {
                    if (pcapPacket.hasHeader(http)) {
                        packet.setType(Sea.HTTP);
                        n_http++;
                        s_http+=size;
                    } else {
                        packet.setType(Sea.TCP);
                        n_tcp_other++;
                        s_tcp_other+=size;
                    }
                } else if (pcapPacket.hasHeader(udp)) {
                    packet.setType(Sea.UDP);
                    n_udp++;
                    s_udp+=size;
                }

                Sea.packets.add(Sea.packets.size(), packet);
            } else {
                n_packets_unknown++;
//                s_packets_unknown+=size;
            }

            n_packets_all++;
//            s_packets_all+=size;
        }
    };
}
