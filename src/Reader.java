import org.jnetpcap.Pcap;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Reader {

    // initialization of module: load file
    Reader() {
        loadFile();
    }

    // loading the file
    void loadFile() {

            try {

                // show load status
                Sea.lbl_status_open.setStyle("-fx-background-color: #5d8");
                Sea.lbl_status_open.setText("Loading file...");

                // start-up extraction for the file
                new Extractor(getPcap(Sea.txt_file.getText()),
                        (Sea.txt_count.getText().equals(""))
                        ? Sea.max_count
                        : Integer.parseInt(Sea.txt_count.getText())
                );

                // show the extracted packets
                Projector projector=new Projector(Sea.packets);
                projector.setFilters();
                projector.showFiltered();

                // show the statistics
                Sea.count_http.setText(Sea.n_http+"");
                Sea.count_tcp.setText(Sea.n_tcp_other+"");
                Sea.count_udp.setText(Sea.n_udp+"");
                Sea.size_http.setText(Sea.s_http+"");
                Sea.size_tcp.setText(Sea.s_tcp_other+"");
                Sea.size_udp.setText(Sea.s_udp+"");

                // show load status: finished
                Sea.lbl_status_open.setText("Completed opening file");
                Sea.lbl_status_open.setStyle("-fx-background-color: #f5b");

            } catch (NullPointerException e) {

                // show error as status
                Sea.lbl_status_open.setText("Error opening file");
                Sea.lbl_status_open.setStyle("-fx-background-color: #f77");

            }
    }

    // access file for capturing
    Pcap getPcap(String filename) {
        StringBuilder errbuff=new StringBuilder();
        // pass the file to the capture module (in library)
        return Pcap.openOffline(filename, errbuff);
    }
}
