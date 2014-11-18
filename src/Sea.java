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

    public static String filename="/dev/java/IdeaProjects/Shark/caps/SkypeIRC.cap"; //temp

    public static int max_count = 4999;//Pcap.LOOP_INFINITE;

    // types --> string
    public static String HTTP="HTTP—tcp", TCP="TCP", UDP="UDP", Unknown="Unknown";

    // ui elements
    public static TableColumn col_source;
    public static TableColumn col_destination;
    public static TextField txt_file;
    public static TableColumn col_size;
    public static TableColumn col_type;
    public static TableView table_packets;
    public static TextArea txt_filters;
//    public static Button btn_filter;
    public static CheckBox chk_filter;
//    public static Button btn_filter_clear;
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

//    public static Object btn_stop;
//    public static Button btn_start;
    public static Pcap pcap;
    public static List<PcapIf> devices;

    public static Thread thread;

    public static void setDevices(List<PcapIf> _devices) {
        devices=_devices;
    }
    public static void setDeviceList(ObservableList<String> _devs) {
        lst_interfaces.setItems(_devs);
    }
}
