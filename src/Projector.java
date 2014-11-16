import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Projector {
    ArrayList<Packet> packets;
    ObservableList<Packet> observablePackets;

    Projector(ArrayList<Packet> _packets) {
        packets=_packets;
    }

    public void showFiltered() {
        observablePackets = FXCollections.observableArrayList();
        int filtered=0;

        if (!Sea.chk_filter.isSelected())
            observablePackets.addAll(packets);
        else
            for (Packet packet : packets) {
                if (filterPacket(packet)) {
                    observablePackets.add(packet);
                    filtered++;
                }
            }

        Sea.table_packets.setItems(observablePackets);

        if (filtered>0) {
            Sea.lbl_status_filtered_stats.setText("Filtered: "+filtered+" packets"); // in statistics
            Sea.lbl_status_filtered_stats1.setText(filtered+" packets"); // in filter
        }
        else {
            Sea.lbl_status_filtered_stats.setText("");
            Sea.lbl_status_filtered_stats1.setText("");
        }
    }


    ArrayList<String> filters;

    public void setFilters() {
        filters=new ArrayList<String>();

        String[] lines=Sea.txt_filters.getText().split("\n");

        for (String line : lines) {

            String[] words=line.split(" ");

            for (String word : words) {
                if (!word.equals("") && !word.contains("/")) {
                    filters.add(filters.size(), word);
                }
            }

        }
    }

    private boolean filterPacket(Packet packet) {
        for (String filter : filters) {
            if (!packet.toString().contains(filter))
                return false;
        }
        return true;
    }
}
