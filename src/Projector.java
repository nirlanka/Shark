import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Projector {
    ArrayList<Packet> packets;
    ObservableList<Packet> observablePackets = FXCollections.observableArrayList();

    Projector(ArrayList<Packet> _packets) {
        packets=_packets;
    }

    public void showFiltered() {
        observablePackets.clear();

        if (!Sea.chk_filter.isSelected())
            observablePackets.addAll(packets);
        else
            for (Packet packet : packets) {
                if (filterPacket(packet))
                    observablePackets.add(packet);
            }

        Sea.table_packets.setItems(observablePackets);
    }

    public void setFilters() {
        //
    }

    private boolean filterPacket(Packet packet) {
        return true;
    }
}
