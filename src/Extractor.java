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
    }

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
                packet.setSize(""+header.wirelen());
                if (pcapPacket.hasHeader(tcp)) {
                    if (pcapPacket.hasHeader(http)) {
                        packet.setType(Sea.HTTP);
                    } else {
                        packet.setType(Sea.TCP);
                    }
                } else if (pcapPacket.hasHeader(udp)) {
                    packet.setType(Sea.UDP);
                }

                Sea.packets.add(Sea.packets.size(), packet);
            }

        }
    };
}
