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

        /*for (String line : lines) {

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

        }*/

        String tmp;

        // from

        if (!(tmp=Sea.from_txtIP.getText()).equals(""))
            if (!Sea.from_chkEnd.isSelected()) {
                filters.add(filters.size(), "from=" + tmp);
            } else {
                filters.add(filters.size(), tmp+"-src");
            }

        if (!(tmp=Sea.from_txtPort.getText()).equals(""))
            filters.add(filters.size(), ":"+tmp+":src");

        // to

        if (!(tmp=Sea.to_txtIP.getText()).equals(""))
            if (!Sea.to_chkEnd.isSelected()) {
                filters.add(filters.size(), "to=" + tmp);
            } else {
                filters.add(filters.size(), tmp+"-dest");
            }

        if (!(tmp=Sea.to_txtPort.getText()).equals(""))
            filters.add(filters.size(), ":"+tmp+":dest");

        // size 1

        if (!(tmp=Sea.size1_txtValue.getText()).equals("")) {
            compareSize=true;
            if (Sea.size1_cmbRelation.getValue().equals(Sea.size1_cmbRelation.getItems().get(1)))
                greaterThan=Integer.parseInt(Sea.size1_txtValue.getText());
            else if (Sea.size1_cmbRelation.getValue().equals(Sea.size1_cmbRelation.getItems().get(2)))
                lowerThan=Integer.parseInt(Sea.size1_txtValue.getText());
            else
                filters.add(filters.size(), "size="+Sea.size1_txtValue.getText());
        }

        // size 2

        if (!(tmp=Sea.size2_txtValue.getText()).equals("")) {
            compareSize=true;
            if (Sea.size2_cmbRelation.getValue().equals(Sea.size2_cmbRelation.getItems().get(1)))
                greaterThan=Integer.parseInt(Sea.size2_txtValue.getText());
            else if (Sea.size2_cmbRelation.getValue().equals(Sea.size2_cmbRelation.getItems().get(2)))
                lowerThan=Integer.parseInt(Sea.size2_txtValue.getText());
            else
                filters.add(filters.size(), "size="+Sea.size2_txtValue.getText());
        }

        // types

        if (!Sea.type_cmb.getValue().equals(Sea.type_cmb.getItems().get(Sea.type_cmb.getItems().size()-1)))
            filters.add(filters.size(), ""+Sea.type_cmb.getValue().toString().toLowerCase());

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

    /*static int getNumOfComparison(String word) {
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
    }*/
}
