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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // test
        txt_file.setText(Sea.filename);

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

        // table cell value factories
        col_source.setCellValueFactory(new PropertyValueFactory<Packet, String>("source"));
        col_destination.setCellValueFactory(new PropertyValueFactory<Packet, String>("destination"));
        col_size.setCellValueFactory(new PropertyValueFactory<Packet, Integer>("size"));
        col_type.setCellValueFactory(new PropertyValueFactory<Packet, String>("type"));


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

        //[]// clear --> no auto refresh [but prompt to apply]
    }
}
