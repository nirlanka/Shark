import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public TableColumn<Packet, String> col_sourceport;
    public TableColumn<Packet, String> col_destport;
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
    public Button btnHelp;
    public TextField from_txtIP;
    public TextField from_txtPort;
    public CheckBox from_chkEnd;
    public CheckBox to_chkEnd;
    public TextField to_txtPort;
    public TextField to_txtIP;
    public ComboBox<String> type_cmb;
    public ComboBox size1_cmbRelation;
    public TextField size1_txtValue;
    public TextField size2_txtValue;
    public ComboBox size2_cmbRelation;

    Live live=new Live();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // test
//        txt_file.setText(Sea.filename);
        btn_stop.setDisable(true);
        txt_count_cap.setDisable(true);
        chk_count_cap.setDisable(true);

        // temp
        btnHelp.setVisible(false);
        txt_filters.setVisible(false);

        // expose ui components
        Sea.col_source=col_source;
        Sea.col_destination=col_destination;
        Sea.txt_file=txt_file;
        Sea.col_size=col_size;
        Sea.col_type=col_type;
        Sea.col_destport=col_destport;
        Sea.col_sourceport=col_sourceport;
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
        Sea.size_udp=size_udp;
        Sea.lbl_status_filtered_stats=lbl_status_filtered_stats;
        Sea.lbl_status_filtered_stats1=lbl_status_filtered_stats1;

        Sea.txt_count=txt_count;
        Sea.chk_count=chk_count;

        Sea.lst_interfaces=lst_interfaces;
        Sea.btn_get_interfaces=btn_get_interfaces;
        Sea.btn_stop=btn_stop;
        Sea.btn_start=btn_start;

        Sea.lbl_status_open_cap=lbl_status_open_cap;

            // expose: filters gui

        Sea.from_chkEnd=from_chkEnd;
        Sea.from_txtIP=from_txtIP;
        Sea.from_txtPort=from_txtPort;

        Sea.to_chkEnd=to_chkEnd;
        Sea.to_txtIP=to_txtIP;
        Sea.to_txtPort=to_txtPort;

        Sea.type_cmb=type_cmb;

        Sea.size1_cmbRelation=size1_cmbRelation;
        Sea.size1_txtValue=size1_txtValue;

        Sea.size2_cmbRelation=size2_cmbRelation;
        Sea.size2_txtValue=size2_txtValue;

            // initialize: filters gui

        final ObservableList<String> types= FXCollections.observableArrayList();
        types.add(Sea.HTTP); types.add(Sea.TCP); types.add(Sea.UDP); types.add(Sea.ICMP);
            types.add(Sea.Unknown); types.add("Any");
        type_cmb.setItems(types);
            type_cmb.setValue(types.get(types.size()-1));

        final ObservableList<String> relations= FXCollections.observableArrayList();
        relations.add("="); relations.add(">="); relations.add("<=");
        size1_cmbRelation.setItems(relations);
            size1_cmbRelation.setValue(relations.get(1));
        size2_cmbRelation.setItems(relations);
            size2_cmbRelation.setValue(relations.get(2));


        // table cell value factories
        col_source.setCellValueFactory(new PropertyValueFactory<Packet, String>("source"));
        col_destination.setCellValueFactory(new PropertyValueFactory<Packet, String>("destination"));
        col_size.setCellValueFactory(new PropertyValueFactory<Packet, Integer>("size"));
        col_type.setCellValueFactory(new PropertyValueFactory<Packet, String>("type"));
        col_sourceport.setCellValueFactory(new PropertyValueFactory<Packet, String>("sourceport"));
        col_destport.setCellValueFactory(new PropertyValueFactory<Packet, String>("destport"));

//        final

        // assign triggers & events

        btn_open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Reader();
            }
        });


        EventHandler<ActionEvent> filter_command=new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Projector proj=new Projector(Sea.packets);
                proj.setFilters();
                proj.showFiltered();
//                btn_filter.setDisable(true);
            }
        };

        btn_filter.setOnAction(filter_command);

        from_chkEnd.setOnAction(filter_command);
        to_chkEnd.setOnAction(filter_command);
        type_cmb.setOnAction(filter_command);
        size1_cmbRelation.setOnAction(filter_command);
        size2_cmbRelation.setOnAction(filter_command);
        // not working:
//        from_txtIP.setOnAction(filter_command);
//        from_txtPort.setOnAction(filter_command);
//        to_txtIP.setOnAction(filter_command);
//        to_txtPort.setOnAction(filter_command);
//        size1_txtValue.setOnAction(filter_command);
//        size2_txtValue.setOnAction(filter_command);
//        chk_filter.setOnAction(filter_command);


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
//                txt_filters.setText("");
//                btn_filter.setDisable(false);
                from_txtIP.setText(""); from_chkEnd.setSelected(false); from_txtPort.setText("");
                to_txtIP.setText(""); to_chkEnd.setSelected(false); to_txtPort.setText("");
                type_cmb.setValue(type_cmb.getItems().get(type_cmb.getItems().size()-1));
                size1_cmbRelation.setValue(size1_cmbRelation.getItems().get(1));
                    size1_txtValue.setText("");
                size2_cmbRelation.setValue(size2_cmbRelation.getItems().get(2));
                    size2_txtValue.setText("");
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
                if (lst_interfaces.getSelectionModel().getSelectedItem()==null)
                    return;

                lbl_status_open_cap.setText("Capturing...");
                lbl_status_open_cap.setStyle("-fx-background-color: #a4f");

                live=new Live();

                String str=lst_interfaces.getSelectionModel().getSelectedItem();
                live.capturePackets(str.split(":")[0].split("#")[1]);

                // view
                Projector projector=new Projector(Sea.packets);
                projector.setFilters();
                projector.showFiltered();

                // temp: use count int file-reader
                txt_count.setText(txt_count_cap.getText());
                chk_count.setSelected(chk_count_cap.isSelected());

                btn_stop.setDisable(false);
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
                } finally {
                    //view
                    Projector projector=new Projector(Sea.packets);
                    projector.setFilters();
                    projector.showFiltered();

                    // view stats
                    Sea.count_http.setText(Sea.n_http+"");
                    Sea.count_tcp.setText(Sea.n_tcp_other+"");
                    Sea.count_udp.setText(Sea.n_udp+"");
                    Sea.size_http.setText(Sea.s_http+"");
                    Sea.size_tcp.setText(Sea.s_tcp_other+"");
                    Sea.size_udp.setText(Sea.s_udp+"");
                }

                btn_start.setDisable(false);
                lst_interfaces.setDisable(false);
                chk_count.setDisable(false);
                txt_count_cap.setDisable(false);

                lbl_status_open_cap.setText("Stopped capturing");
                lbl_status_open_cap.setStyle("-fx-background-color: #4cf");
            }
        });

        final Tooltip filterHelp=new Tooltip();
        filterHelp.setText("Some text");
        btnHelp.setTooltip(filterHelp);

        //[]// clear --> no auto refresh [but prompt to apply]
    }

}
