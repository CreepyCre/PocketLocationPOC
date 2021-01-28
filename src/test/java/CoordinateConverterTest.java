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
    void findNextIDAndClaimTest() {
        CoordinateConverter cc = new CoordinateConverter();
        assertEquals(0, cc.findNextIDAndClaim(1));
        assertEquals(1, cc.findNextIDAndClaim(1));
        assertEquals(2, cc.findNextIDAndClaim(1));
        assertEquals(3, cc.findNextIDAndClaim(1));
        assertEquals(4, cc.findNextIDAndClaim(1));
        assertEquals(5, cc.findNextIDAndClaim(1));
        assertEquals(6, cc.findNextIDAndClaim(1));

        assertEquals(9, cc.findNextIDAndClaim(3));
        assertEquals(18, cc.findNextIDAndClaim(3));
        assertEquals(81, cc.findNextIDAndClaim(4));

        assertEquals(27, cc.findNextIDAndClaim(3));
        assertEquals(7, cc.findNextIDAndClaim(1));
        assertEquals(8, cc.findNextIDAndClaim(1));
    }
}