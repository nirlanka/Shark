import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import java.util.ArrayList;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Extractor {

    // keep count of the packets captured
    int COUNT;
    // initialize (construct) the module
    public Extractor(Pcap pcap, int _COUNT) {
        COUNT=_COUNT;

        int count=Sea.max_count, user_count=0;
        if (!Sea.txt_count.getText().equals(""))
            user_count=Integer.parseInt(Sea.txt_count.getText());
        if (Sea.chk_count.isSelected() && count>user_count)
            count=user_count;

        Sea.n_packets_all=0; Sea.n_http=0; Sea.n_tcp_other=0; Sea.n_udp=0; Sea.n_packets_unknown=0;
        /*s_packets_all=0;*/ Sea.s_http=0; Sea.s_tcp_other=0; Sea.s_udp=0/*; s_packets_unknown=0*/;

        Sea.packets.clear();
        pcap.loop(count, packetHandler, Sea.packets);
        pcap.close();

    }

    PcapPacketHandler<ArrayList> packetHandler=new PcapPacketHandler<ArrayList>() {

        // placeholders for recognized packet header types
        final Ip4 ip=new Ip4();
        final Tcp tcp=new Tcp();
        final Http http=new Http();
        final Udp udp=new Udp();
        final Icmp icmp=new Icmp();

        // handle each packet
        @Override
        public void nextPacket(PcapPacket pcapPacket, ArrayList arrayList) {

            // keep overall packet count
            Sea.printCapCountUp();

            // for IP packets
            if (pcapPacket.hasHeader(ip)) {

                // placeholder packet
                Packet packet=new Packet();
                packet.setSource(""+ FormatUtils.ip(ip.source()));
                packet.setDestination("" + FormatUtils.ip(ip.destination()));

                // analyze packet header
                PcapHeader header=pcapPacket.getCaptureHeader();

                // read packet size
                int size=header.wirelen();
                packet.setSize(size);

                // for TCP packets
                if (pcapPacket.hasHeader(tcp)) {

                    // for HTTP packets
                    if (pcapPacket.hasHeader(http)) {

                        packet.setSourceport(tcp.source());
                        packet.setDestport(tcp.destination());
                        packet.setType(Sea.HTTP);
                        Sea.n_http++;
                        Sea.s_http+=size;

                    }

                    // for ICMP packets
                    else if (pcapPacket.hasHeader(icmp)) {

                        packet.setSourceport(tcp.source());
                        packet.setDestport(tcp.destination());
                        packet.setType(Sea.ICMP);

                    }

                    // for other TCP packets
                    else {

                        packet.setSourceport(tcp.source());
                        packet.setDestport(tcp.destination());
                        packet.setType(Sea.TCP);
                        Sea.n_tcp_other++;
                        Sea.s_tcp_other+=size;

                    }
                }

                // for UDP packets
                else if (pcapPacket.hasHeader(udp)) {

                    packet.setSourceport(udp.source());
                    packet.setDestport(udp.destination());
                    packet.setType(Sea.UDP);
                    Sea.n_udp++;
                    Sea.s_udp+=size;

                }

                // issue meta-packet to our stack
                Sea.packets.add(Sea.packets.size(), packet);

            }

            // for unrecognized packets

            else {

                // keep count of type-unknown packets
                Sea.n_packets_unknown++;

                // placeholder packet
                Packet packet=new Packet();

                // analyze packet header
                PcapHeader header=pcapPacket.getCaptureHeader();

                // read packet size
                int size=header.wirelen();
                packet.setSize(size);
                Sea.s_non_ip+=size;

                // issue meta-packet
                Sea.packets.add(Sea.packets.size(), packet);

            }

            // keep overall packet count
            Sea.n_packets_all++;

            // break if preset-maximum count is exceeded
            if (Sea.n_packets_all>=COUNT)
                try {
                    // break analization loop
                    Sea.pcap.breakloop();
                } catch (org.jnetpcap.PcapClosedException e) {}
        }
    };
}
