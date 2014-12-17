import org.junit.Test;

import static org.junit.Assert.*;

public class TestPacket {

    static String dummySourceIP ="source.dummy.ip",
            dummyDestIP ="dest.dummy.ip",
            dummyType=Sea.HTTP;
    static int  dummySize =1992,
                dummySourcePort =9999,
                dummyDestPort =1313;
    static String   dummySize_str =dummySize+"",
                    dummySourcePort_str =dummySourcePort+"",
                    dummyDestPort_str =dummyDestPort+"";


    // get fields

    @Test
    public void testGetters() throws Exception {

        // default constructor

        Packet packet=new Packet();

        assertNotNull(packet.getSource());
        assertNotNull(packet.getDestination());

        assertNotNull(packet.getSize());

        assertNotNull(packet.getType());

        assertNotNull(packet.getSourceport());
        assertNotNull(packet.getDestport());

        // full constructor

        packet=new Packet(dummySourceIP, dummyDestIP, dummySize,dummyType, dummySourcePort_str, dummyDestPort_str);

        assertEquals(dummySourceIP,packet.getSource());
        assertEquals(dummyDestIP,packet.getDestination());

        assertEquals(dummySize,packet.getSize());

        assertEquals(dummyType,packet.getType());

        assertEquals(dummySourcePort_str,packet.getSourceport());
        assertEquals(dummyDestPort_str,packet.getDestport());
    }

    // set fields

    @Test
    public void testSetters() throws Exception {

        Packet packet=new Packet();

        packet.setSource(dummySourceIP);
        packet.setDestination(dummyDestIP);
        packet.setSize(dummySize);
        packet.setType(dummyType);
        packet.setSourceport(dummySourcePort);
        packet.setDestport(dummyDestPort);

        assertEquals(dummySourceIP, packet.getSource());
        assertEquals(dummyDestIP,packet.getDestination());

        assertEquals(dummySize,packet.getSize());

        assertEquals(dummyType,packet.getType());

        assertEquals(dummySourcePort_str,packet.getSourceport());
        assertEquals(dummyDestPort_str,packet.getDestport());
    }

    // property values (observable list -- columns)

    @Test
    public void testProperties() throws Exception {
        // depends on the setters and the constructor
        // getters depend on this
        // therefore, getter+setter tests also
        // prove this
    }

    //

    @Test
    public void testToString() throws Exception {

        Packet packet=new Packet(dummySourceIP, dummyDestIP, dummySize,dummyType, dummySourcePort_str, dummyDestPort_str);

//        new Packet(); // DEMO

        assertEquals(
                "from="+dummySourceIP+"-src" + ":"+dummySourcePort_str+":src"
                +"to="+dummyDestIP+"-dest" + ":"+dummyDestPort_str+":dest"
                +"size="+dummySize_str
                + "prot="+dummyType.toLowerCase(),

                packet.toString()
                );
    }
}