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
                new Extractor(getPcap(Sea.txt_file.getText()));
                Sea.lbl_status_open.setText("Completed opening file");
                Sea.lbl_status_open.setStyle("-fx-background-color: #f5b");
            } catch (NullPointerException e) {
                e.printStackTrace();
//                System.out.println("NullPointerException -- full path needed");
                Sea.lbl_status_open.setText("Error opening file");
                Sea.lbl_status_open.setStyle("-fx-background-color: #f77");
            }
    }

    Pcap getPcap(String filename) {
        StringBuilder errbuff=new StringBuilder();
        return Pcap.openOffline(filename, errbuff);
    }
}
