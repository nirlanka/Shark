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
            Sea.lbl_status_filtered_stats.setStyle("-fx-background-color: #4cf");

            Sea.lbl_status_filtered_stats1.setText(filtered+" packets"); // in filter
            Sea.lbl_status_filtered_stats1.setStyle("-fx-background-color: #a4f");

        }
        else {
            Sea.lbl_status_filtered_stats.setText("");
            Sea.lbl_status_filtered_stats.setStyle("-fx-background-color: #bbb");

            Sea.lbl_status_filtered_stats1.setText("");
            Sea.lbl_status_filtered_stats1.setStyle("-fx-background-color: #bbb");

        }
    }


    //[]// filter-OUT stuff

    ArrayList<String> filters;
    int lowerThan, greaterThan;
    boolean compareSize;

    public void setFilters() {
        filters=new ArrayList<String>();

        compareSize=false;
        lowerThan = 99999; greaterThan = -1;

        String[] lines=Sea.txt_filters.getText().split("\n");

        for (String line : lines) {

            String[] words=line.split(" ");

            for (String word : words) {
                if (word.contains(">") || word.contains("<")) {
                    compareSize=true;
                    if (word.contains(">")) { //size>100
                        greaterThan=getNumOfComparison(word);
                    } else { //size<100
                        lowerThan=getNumOfComparison(word);
                    }
                }
                else if (!word.equals("") && !word.contains("/")) {
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
        if (compareSize) {
            if (!(packet.getSize()<=lowerThan)) {
                return false;
            }
            if (!(packet.getSize()>=greaterThan)) {
                return false;
            }
        }
        return true;
    }

    static int getNumOfComparison(String word) {
        char[] __word=word.toCharArray();
        String num="";
        boolean passedComparator=false;

        for (char c : __word) {
            if (!passedComparator) {
                if (c=='>' || c=='<') passedComparator=true;
            } else {
                num+=c;
            }
        }

        return Integer.parseInt(num);
    }
}
