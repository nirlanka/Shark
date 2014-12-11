import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Nirmal on 16/11/2014.
 */
public class Packet {
    private final StringProperty source, destination, type, destport, sourceport ;
    private final IntegerProperty size;

    public Packet(String source, String destination, int size, String type, String destport, String sourceport) {
        this.source = new SimpleStringProperty(source);
        this.destination = new SimpleStringProperty(destination);
        this.size = new SimpleIntegerProperty(size);
        this.type = new SimpleStringProperty(type);
        this.destport = new SimpleStringProperty(destport);
        this.sourceport = new SimpleStringProperty(sourceport);
    }

    public Packet() {this("","",0,"","", "");}

    // setters
    public void setSource(String s) {this.source.set(s);}
    public void setDestination(String s) {this.destination.set(s);}
    public void setSize(int s) {this.size.set(s);}
    public void setType(String s) {this.type.set(s);}
    public void setDestport(int s) {this.destport.set(String.valueOf(s));}
    public void setSourceport(int s) {this.sourceport.set(String.valueOf(s));}

    // getters
    public String getSource() {return this.source.get();}
    public String getDestination() {return this.destination.get();}
    public int getSize() {return this.size.get();}
    public String getType() {return this.type.get();}
    public String getDestport() {return this.destport.get();}
    public String getSourceport() {return this.sourceport.get();}

    // as properties (getters)
    public StringProperty sourceProperty() {return source;}
    public StringProperty destinationProperty() {return destination;}
    public IntegerProperty sizeProperty() {return size;}
    public StringProperty typeProperty() {return type;}
    public StringProperty destportProperty() {return destport;}
    public StringProperty sourceportProperty() {return sourceport;}

    @Override
    public String toString() {
        return   "from="+getSource()+"-src" + ":"+getSourceport()+":src"
                +"to="+getDestination()+"-dest" + ":"+getDestport()+":dest"
                +"size="+getSize()
                + "prot="+getType().toLowerCase()
/*
                +"from="+getSource()+":"+getSourceport()+"-src"
                +"to="+getDestination()+":"+getDestport()+"-dest"*/
        ;
    }
}
