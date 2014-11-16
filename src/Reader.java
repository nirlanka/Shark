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
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("NullPointerException -- full path needed");
            }
    }

    Pcap getPcap(String filename) {
        StringBuilder errbuff=new StringBuilder();
        return Pcap.openOffline(filename, errbuff);
    }
}
