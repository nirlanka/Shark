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

//    Projector() {}


    public void showFiltered() {
//        observablePackets.clear();
        observablePackets = FXCollections.observableArrayList();

        if (!Sea.chk_filter.isSelected())
            observablePackets.addAll(packets);
        else
            for (Packet packet : packets) {
                if (filterPacket(packet))
                    observablePackets.add(packet);
            }

        Sea.table_packets.setItems(observablePackets);
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
