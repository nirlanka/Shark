import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TableColumn<Packet, String> col_source;
    public TableColumn<Packet, String> col_destination;
    public TableColumn<Packet, Integer> col_size;
    public TableColumn<Packet, String> col_type;
    public TextField txt_file;
    public Button btn_open;
    public TableView table_packets;
    public TextArea txt_filters;
    public Button btn_filter;
    public CheckBox chk_filter;
    public Button btn_filter_clear;
    public Label lbl_status_open;
    public Label count_http;
    public Label count_tcp;
    public Label count_udp;
    public Label size_http;
    public Label size_tcp;
    public Label size_udp;
    public Label lbl_status_filtered_stats;
    public Label lbl_status_filtered_stats1;
    public TextField txt_count;
    public CheckBox chk_count;
    public ListView<String> lst_interfaces;
    public Button btn_get_interfaces;
    public Button btn_start;
    public Button btn_stop;
    public TextField txt_count_cap;
    public CheckBox chk_count_cap;
    public Label lbl_status_open_cap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // test
//        txt_file.setText(Sea.filename);

        // expose ui components
        Sea.col_source=col_source;
        Sea.col_destination=col_destination;
        Sea.txt_file=txt_file;
        Sea.col_size=col_size;
        Sea.col_type=col_type;
        Sea.table_packets=table_packets;
        Sea.txt_filters=txt_filters;
//        Sea.btn_filter=btn_filter;
        Sea.chk_filter=chk_filter;
//        Sea.btn_filter_clear=btn_filter_clear;
        Sea.lbl_status_open=lbl_status_open;

        Sea.count_http=count_http;
        Sea.count_tcp=count_tcp;
        Sea.count_udp=count_udp;
        Sea.size_http=size_http;
        Sea.size_tcp=size_tcp;
        Sea.size_udp=size_tcp;
        Sea.lbl_status_filtered_stats=lbl_status_filtered_stats;
        Sea.lbl_status_filtered_stats1=lbl_status_filtered_stats1;

        Sea.txt_count=txt_count;
        Sea.chk_count=chk_count;

        Sea.lst_interfaces=lst_interfaces;
        Sea.btn_get_interfaces=btn_get_interfaces;
//        Sea.btn_stop;
//        Sea.btn_start=btn_start;

        // table cell value factories
        col_source.setCellValueFactory(new PropertyValueFactory<Packet, String>("source"));
        col_destination.setCellValueFactory(new PropertyValueFactory<Packet, String>("destination"));
        col_size.setCellValueFactory(new PropertyValueFactory<Packet, Integer>("size"));
        col_type.setCellValueFactory(new PropertyValueFactory<Packet, String>("type"));

        final Live live=new Live();

        // assign triggers & events

        btn_open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Reader();
            }
        });
        btn_filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Projector proj=new Projector(Sea.packets);
                proj.setFilters();
                proj.showFiltered();
                btn_filter.setDisable(true);
            }
        });
        txt_filters.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                btn_filter.setDisable(false);
            }
        });
        chk_filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btn_filter.setDisable(false);
            }
        });
        btn_filter_clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt_filters.setText("");
                btn_filter.setDisable(false);
            }
        });

        btn_get_interfaces.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                live.getInterfaces();
            }
        });
        btn_start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str=lst_interfaces.getSelectionModel().getSelectedItem();
                live.capturePackets(str.split(":")[0].split("#")[1]);

                // temp: use count int file-reader
                txt_count.setText(txt_count_cap.getText());
                chk_count.setSelected(chk_count_cap.isSelected());

                btn_start.setDisable(true);
                lst_interfaces.setDisable(true);
                chk_count.setDisable(true);
                txt_count_cap.setDisable(true);
            }
        });
        btn_stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Sea.pcap.breakloop();
//                System.out.println("Sea.pcap.breakloop()");
                try {
                    Sea.thread.join();
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    System.out.println("//");
                }
                btn_start.setDisable(false);
                lst_interfaces.setDisable(false);
                chk_count.setDisable(false);
                txt_count_cap.setDisable(false);
            }
        });

        //[]// clear --> no auto refresh [but prompt to apply]
    }
}
