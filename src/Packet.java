import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Packet {
    private final StringProperty source, destination, size, type;

    public Packet(String source, String destination, String size, String type) {
        this.source = new SimpleStringProperty(source);
        this.destination = new SimpleStringProperty(destination);
        this.size = new SimpleStringProperty(size);
        this.type = new SimpleStringProperty(type);
    }

    public Packet() {this("","","","");}

    // setters
    public void setSource(String s) {this.source.set(s);}
    public void setDestination(String s) {this.destination.set(s);}
    public void setSize(String s) {this.size.set(s);}
    public void setType(String s) {this.type.set(s);}

    // getters
    public String getSource() {return this.source.get();}
    public String getDestination() {return this.destination.get();}
    public String getSize() {return this.size.get();}
    public String getType() {return this.type.get();}

    // as properties (getters)
    public StringProperty sourceProperty() {return source;}
    public StringProperty destinationProperty() {return destination;}
    public StringProperty sizeProperty() {return size;}
    public StringProperty typeProperty() {return type;}


    @Override
    public String toString() {
        return     "src="+getSource()
                +" dest="+getDestination()
                +" size="+getSize()
                +" type="+getType().toLowerCase();
    }
}
