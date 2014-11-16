import javafx.scene.control.*;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Sea {

    public static String filename="/dev/java/IdeaProjects/Shark/caps/NTLM-wenchao.pcap"; //temp

    // types --> string
    public static String HTTP="HTTP-header", TCP="TCP", UDP="UDP", Unknown="Unknown";

    // ui elements
    public static TableColumn col_source;
    public static TableColumn col_destination;
    public static TextField txt_file;
    public static TableColumn col_size;
    public static TableColumn col_type;
    public static TableView table_packets;
    public static TextArea txt_filters;
    public static Button btn_filter;
    public static CheckBox chk_filter;
}
