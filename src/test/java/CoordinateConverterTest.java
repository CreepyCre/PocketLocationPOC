import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateConverterTest {

    @Test
    void conversionConsistencyTest() {
        CoordinateConverter cc = new CoordinateConverter();
        for (int i = 0; i < 1000; i++) {
            Vector<Long> vec = cc.convertToPosition(i);
            System.out.println(vec.get(0) + ", " + vec.get(1));
            assertEquals(i, cc.convertToID(cc.convertToPosition(i)));
        }
    }

    @Test
    void convertToPositionTest() {
        CoordinateConverter cc = new CoordinateConverter();
        Vector<Long> vec;
        // I scribbled the grid down so I could figure out these tests, lmao
        vec = cc.convertToPosition(86);
        assertEquals(8, vec.get(0));
        assertEquals(1, vec.get(1));
        vec = cc.convertToPosition(90);
        assertEquals(12, vec.get(0));
        assertEquals(0, vec.get(1));
        vec = cc.convertToPosition(100);
        assertEquals(7, vec.get(0));
        assertEquals(0, vec.get(1));
    }

    @Test
    void findIDtoFitTest() {
        CoordinateConverter cc = new CoordinateConverter();
        assertEquals(0, cc.findIDToFit(1));
        assertEquals(8, cc.findIDToFit(2));
        assertEquals(8, cc.findIDToFit(3));
        assertEquals(80, cc.findIDToFit(4));
        assertEquals(80, cc.findIDToFit(5));
        assertEquals(80, cc.findIDToFit(6));
        assertEquals(80, cc.findIDToFit(7));
        assertEquals(80, cc.findIDToFit(8));
        assertEquals(80, cc.findIDToFit(9));
        assertEquals(728, cc.findIDToFit(10));
        assertEquals(728, cc.findIDToFit(27));
    }

    void claimIDTest() {
        CoordinateConverter cc = new CoordinateConverter();
        assertEquals(0, cc.findIDToFit(1));
        cc.claimID(0);
        assertEquals(1, cc.findIDToFit(1));
        cc.claimID(1);
        assertEquals(2, cc.findIDToFit(1));
        cc.claimID(2);
        assertEquals(3, cc.findIDToFit(1));
        cc.claimID(3);
        assertEquals(4, cc.findIDToFit(1));
        cc.claimID(4);
        assertEquals(5, cc.findIDToFit(1));
        cc.claimID(5);
        assertEquals(6, cc.findIDToFit(1));
        cc.claimID(6);
        assertEquals(7, cc.findIDToFit(1));
        cc.claimID(7);
        assertEquals(8, cc.findIDToFit(1));
        cc.claimID(8);


        assertEquals(17, cc.findIDToFit(3));
        cc.claimID(17);
        assertEquals(26, cc.findIDToFit(3));
        cc.claimID(27);
        assertEquals(44, cc.findIDToFit(3));

    }
}