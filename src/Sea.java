import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Sea {

    public static ArrayList<Packet> packets=new ArrayList<Packet>();

    // maximum count of packets accpeted
    public static int max_count = 49999; // instead of Pcap.LOOP_INFINITE;

    // types names as string
    public static String HTTP="HTTP—tcp", TCP="TCP", UDP="UDP", Unknown="Unknown"
            , ICMP="ICMP—tcp"
            , ARP="ARP", RTP="RTP—VoIP", SDP="SDP—VoIP", SIP="SIP—VoIP", PPP="PPP—wan", L2TP="L2TP—vpn"
                , VOIP="voip", WAN="wan", VPN="vpn"
                , VOIP__="VoIP (rtp,sdp,sid)", WAN__="WAN (L2TP)", VPN__="VPN (L2TP)"
            ;

    // ui element references

    public static TableColumn col_source;
    public static TableColumn col_destination;
    public static TextField txt_file;
    public static TableColumn col_size;
    public static TableColumn col_type;
    public static TableColumn col_destport;
    public static TableColumn col_sourceport;
    public static TableView table_packets;
    public static CheckBox chk_filter;
    public static Label lbl_status_open;

    public static Label count_http;
    public static Label count_tcp;
    public static Label count_udp;
    public static Label size_http;
    public static Label size_tcp;
    public static Label size_udp;
    public static Label lbl_status_filtered_stats;
    public static Label lbl_status_filtered_stats1;
    public static TextField txt_count;
    public static CheckBox chk_count;

    public static ListView lst_interfaces;
    public static Button btn_get_interfaces;

    public static Pcap pcap;
    public static List<PcapIf> devices;

    public static Thread thread;
    public static Button btn_stop;
    public static Button btn_start;
    public static Label lbl_status_open_cap;

    static int n_packets_all=0, n_http=0, n_tcp_other=0, n_udp=0, n_packets_unknown=0,
    /*s_packets_all=0,*/ s_http=0, s_tcp_other=0, s_udp=0/*, s_packets_unknown=0*/;

    static int n_non_ip=0, s_non_ip=0;
    public static CheckBox from_chkEnd;
    public static TextField from_txtIP;
    public static TextField from_txtPort;
    public static CheckBox to_chkEnd;
    public static TextField to_txtIP;
    public static TextField to_txtPort;
    public static ComboBox type_cmb;
    public static ComboBox size1_cmbRelation;
    public static TextField size1_txtValue;
    public static ComboBox size2_cmbRelation;
    public static TextField size2_txtValue;

    // count of captured packets
    static int capCount=0;
    public static void printCapCountUp() {
        capCount++;
    }
}
