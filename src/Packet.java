import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Packet {
    private final StringProperty source, destination, type;
    private final IntegerProperty size;

    public Packet(String source, String destination, int size, String type) {
        this.source = new SimpleStringProperty(source);
        this.destination = new SimpleStringProperty(destination);
        this.size = new SimpleIntegerProperty(size);
        this.type = new SimpleStringProperty(type);
    }

    public Packet() {this("","",0,"");}

    // setters
    public void setSource(String s) {this.source.set(s);}
    public void setDestination(String s) {this.destination.set(s);}
    public void setSize(int s) {this.size.set(s);}
    public void setType(String s) {this.type.set(s);}

    // getters
    public String getSource() {return this.source.get();}
    public String getDestination() {return this.destination.get();}
    public int getSize() {return this.size.get();}
    public String getType() {return this.type.get();}

    // as properties (getters)
    public StringProperty sourceProperty() {return source;}
    public StringProperty destinationProperty() {return destination;}
    public IntegerProperty sizeProperty() {return size;}
    public StringProperty typeProperty() {return type;}


    @Override
    public String toString() {
        return     "src="+getSource()
                +" dest="+getDestination()
                +" size="+getSize()
                +" type="+getType().toLowerCase();
    }
}
