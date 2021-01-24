import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateConverterTest {

    @Test
    void conversionConsistencyTest() {
        CoordinateConverter cc = new CoordinateConverter();
        for (int i = 0; i < 1000; i++) {
            Vector<Long> vec = cc.convertToGridPosition(i);
            System.out.println(vec.get(0) + ", " + vec.get(1));
            assertEquals(i, cc.convertGridPositionToID(cc.convertToGridPosition(i)));
        }
    }

    @Test
    void convertToPositionTest() {
        CoordinateConverter cc = new CoordinateConverter();
        Vector<Long> vec;
        // I scribbled the grid down so I could figure out these tests, lmao
        vec = cc.convertToGridPosition(86);
        assertEquals(8, vec.get(0));
        assertEquals(1, vec.get(1));
        vec = cc.convertToGridPosition(90);
        assertEquals(12, vec.get(0));
        assertEquals(0, vec.get(1));
        vec = cc.convertToGridPosition(100);
        assertEquals(7, vec.get(0));
        assertEquals(0, vec.get(1));
    }

    @Test
    void findIDtoFitTest() {
        CoordinateConverter cc = new CoordinateConverter();
        assertEquals(0, cc.findIDToFitGridSize(1));
        assertEquals(8, cc.findIDToFitGridSize(2));
        assertEquals(8, cc.findIDToFitGridSize(3));
        assertEquals(80, cc.findIDToFitGridSize(4));
        assertEquals(80, cc.findIDToFitGridSize(5));
        assertEquals(80, cc.findIDToFitGridSize(6));
        assertEquals(80, cc.findIDToFitGridSize(7));
        assertEquals(80, cc.findIDToFitGridSize(8));
        assertEquals(80, cc.findIDToFitGridSize(9));
        assertEquals(728, cc.findIDToFitGridSize(10));
        assertEquals(728, cc.findIDToFitGridSize(27));
    }

    @Test
    void claimIDTest() {
        CoordinateConverter cc = new CoordinateConverter();
        assertEquals(0, cc.findIDToFitGridSize(1));
        cc.claimID(0);
        assertEquals(1, cc.findIDToFitGridSize(1));
        cc.claimID(1);
        assertEquals(2, cc.findIDToFitGridSize(1));
        cc.claimID(2);
        assertEquals(3, cc.findIDToFitGridSize(1));
        cc.claimID(3);
        assertEquals(4, cc.findIDToFitGridSize(1));
        cc.claimID(4);
        assertEquals(5, cc.findIDToFitGridSize(1));
        cc.claimID(5);
        assertEquals(6, cc.findIDToFitGridSize(1));
        cc.claimID(6);


        assertEquals(17, cc.findIDToFitGridSize(3));
        cc.claimID(17);
        assertEquals(26, cc.findIDToFitGridSize(3));
        cc.claimID(27);
        assertEquals(44, cc.findIDToFitGridSize(3));

    }
}