import org.jnetpcap.Pcap;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Reader {

    Reader() {
        loadFile();
    }

    void loadFile() {
            try {
                Sea.lbl_status_open.setStyle("-fx-background-color: #5d8");
                Sea.lbl_status_open.setText("Loading file...");
                new Extractor(getPcap(Sea.txt_file.getText()),
<<<<<<< HEAD
                        (Sea.txt_count.getText().equals(""))
=======
                        (Sea.txt_count.equals(""))
>>>>>>> origin/master
                        ? Sea.max_count
                        : Integer.parseInt(Sea.txt_count.getText())
                );

                // view
                Projector projector=new Projector(Sea.packets);
                projector.setFilters();
                projector.showFiltered();

                // view stats
//                Sea.count_http.setText("");
//                Sea.count_tcp.setText("");
//                Sea.count_udp.setText("");
//                Sea.size_http.setText("");
//                Sea.size_tcp.setText("");
//                Sea.size_udp.setText("");

                Sea.count_http.setText(Sea.n_http+"");
                Sea.count_tcp.setText(Sea.n_tcp_other+"");
                Sea.count_udp.setText(Sea.n_udp+"");
                Sea.size_http.setText(Sea.s_http+"");
                Sea.size_tcp.setText(Sea.s_tcp_other+"");
                Sea.size_udp.setText(Sea.s_udp+"");

                Sea.lbl_status_open.setText("Completed opening file");
                Sea.lbl_status_open.setStyle("-fx-background-color: #f5b");
            } catch (NullPointerException e) {
//                e.printStackTrace();
                Sea.lbl_status_open.setText("Error opening file");
                Sea.lbl_status_open.setStyle("-fx-background-color: #f77");
            }
    }

    Pcap getPcap(String filename) {
        StringBuilder errbuff=new StringBuilder();
        return Pcap.openOffline(filename, errbuff);
    }
}
