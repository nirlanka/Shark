import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TableColumn<Packet, String> col_source;
    public TableColumn<Packet, String> col_destination;
    public TableColumn<Packet, String> col_size;
    public TableColumn<Packet, String> col_type;
    public TextField txt_file;
    public Button btn_open;
    public TableView table_packets;
    public TextArea txt_filters;
    public Button btn_filter;
    public CheckBox chk_filter;


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
        Sea.btn_filter=btn_filter;
        Sea.chk_filter=chk_filter;

        // table cell value factories
        col_source.setCellValueFactory(new PropertyValueFactory<Packet, String>("source"));
        col_destination.setCellValueFactory(new PropertyValueFactory<Packet, String>("destination"));
        col_size.setCellValueFactory(new PropertyValueFactory<Packet, String>("size"));
        col_type.setCellValueFactory(new PropertyValueFactory<Packet, String>("type"));


        // assign triggers & events
    }
}
