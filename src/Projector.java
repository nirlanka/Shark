import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Projector {
    ArrayList<Packet> packets;
    ObservableList<Packet> observablePackets;

    // initialize for the given list of meta-packets
    Projector(ArrayList<Packet> _packets) {
        packets=_packets;
    }

    // filter and show the packets
    public void showFiltered() {

        // list for the gui
        observablePackets = FXCollections.observableArrayList();

        // keep count of filtered (surviving) packets
        int filtered=0;

        // if 'apply filter' checkbox is OFF
        if (!Sea.chk_filter.isSelected())
            // add to the GUI's list
            observablePackets.addAll(packets);
        // if checkbox is ON
        else
            // for each meta-packet
            for (Packet packet : packets) {
                // apply filter
                if (filterPacket(packet)) {
                    // if surviving, add to the GUI's list
                    observablePackets.add(packet);
                    filtered++;
                }
            }

        // show the list on the GUI
        Sea.table_packets.setItems(observablePackets);

        // show filter status:

        // results exist
        if (filtered>0) {
            Sea.lbl_status_filtered_stats.setText("Filtered: "+filtered+" packets"); // in statistics
            Sea.lbl_status_filtered_stats.setStyle("-fx-background-color: #4cf");

            Sea.lbl_status_filtered_stats1.setText(filtered+" packets"); // in filter
            Sea.lbl_status_filtered_stats1.setStyle("-fx-background-color: #a4f");

        }

        // no results
        else {
            Sea.lbl_status_filtered_stats.setText("");
            Sea.lbl_status_filtered_stats.setStyle("-fx-background-color: #bbb");

            Sea.lbl_status_filtered_stats1.setText("");
            Sea.lbl_status_filtered_stats1.setStyle("-fx-background-color: #bbb");

        }
    }


    //[]// filter-OUT stuff

    // list of filter keywords
    ArrayList<String> filters;

    // size range filters
    int lowerThan, greaterThan;
    boolean compareSize;

    // get filters from GUI
    public void setFilters() {

        // empty keyword list
        filters=new ArrayList<String>();

        // default settings
        compareSize=false;
        lowerThan = 99999; greaterThan = -1;

        // each keyword
        String tmp;

        // source IP address

        // if not empty
        if (!(tmp=Sea.from_txtIP.getText()).equals(""))
            // match the beginning of address (filtering ISPs)
            if (!Sea.from_chkEnd.isSelected()) {
                filters.add(filters.size(), "from=" + tmp);
            }
            // match the end of the address (filtering hosts)
            else {
                filters.add(filters.size(), tmp+"-src");
            }

        // source IP port
        // if not empty
        if (!(tmp=Sea.from_txtPort.getText()).equals(""))
            filters.add(filters.size(), ":"+tmp+":src");

        // destination IP address

        // if not empty
        if (!(tmp=Sea.to_txtIP.getText()).equals(""))
            // match the beginning of address (filtering ISPs)
            if (!Sea.to_chkEnd.isSelected()) {
                filters.add(filters.size(), "to=" + tmp);
            }
            // match the end of the address (filtering hosts)
            else {
                filters.add(filters.size(), tmp+"-dest");
            }

        // destination IP port
        // if not empty
        if (!(tmp=Sea.to_txtPort.getText()).equals("")) {
            filters.add(filters.size(), ":" + tmp + ":dest");
        }

        // size range bound 1
        // if not empty
        if (!(tmp=Sea.size1_txtValue.getText()).equals("")) {

            // bound comparison

            compareSize=true;
            // packet.size < filter
            if (Sea.size1_cmbRelation.getValue().equals(Sea.size1_cmbRelation.getItems().get(1)))
                greaterThan=Integer.parseInt(Sea.size1_txtValue.getText());
            // packet.size > filter
            else if (Sea.size1_cmbRelation.getValue().equals(Sea.size1_cmbRelation.getItems().get(2)))
                lowerThan=Integer.parseInt(Sea.size1_txtValue.getText());

            // check equality

            else
                filters.add(filters.size(), "size="+Sea.size1_txtValue.getText());
        }

        // size range bound 2
        // if not empty
        if (!(tmp=Sea.size2_txtValue.getText()).equals("")) {

            // bound comparison

            compareSize=true;
            // packet.size < filter
            if (Sea.size2_cmbRelation.getValue().equals(Sea.size2_cmbRelation.getItems().get(1)))
                greaterThan=Integer.parseInt(Sea.size2_txtValue.getText());
            // packet.size > filter
            else if (Sea.size2_cmbRelation.getValue().equals(Sea.size2_cmbRelation.getItems().get(2)))
                lowerThan=Integer.parseInt(Sea.size2_txtValue.getText());

            // check equality

            else
                filters.add(filters.size(), "size="+Sea.size2_txtValue.getText());
        }

        // type (protocol)

        // if not empty

        Object typeCmbValue=Sea.type_cmb.getValue();
        ObservableList<Packet> typeCmbItems=Sea.type_cmb.getItems();

        if (!typeCmbValue.equals(typeCmbItems.get(typeCmbItems.size() - 1)))
            filters.add(filters.size(), "" + typeCmbValue.toString().toLowerCase());

    }

    private boolean filterPacket(Packet packet) {

        // check equality to all filter keywords
        for (String filter : filters) {
            if (!packet.toString().contains(filter))
                return false;
        }

        // do size comparison for the bounds
        if (compareSize) {
            if (!(packet.getSize()<=lowerThan)) {
                return false;
            }
            if (!(packet.getSize()>=greaterThan)) {
                return false;
            }
        }

        // if no filter-fails happened
        return true;
    }

}
