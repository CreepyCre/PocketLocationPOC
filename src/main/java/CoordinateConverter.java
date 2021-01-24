import java.util.Arrays;
import java.util.Vector;

public class CoordinateConverter {

    // all ids >= cursor are unused
    private long cursor = 0;

    /*
    0 ->  0, 0
    1 ->  1, 0
    2 -> -1, 0
    3 ->  0, 1
    4 ->  1, 1
    5 -> -1, 1
    6 ->  0,-1
    7 ->  1,-1
    8 -> -1,-1

    If last id in area is lowest position, then we can use the last id as a pockets assigned position. This makes claiming IDs easier.
     */


    // You may interpret this as a chunk position (or as the position of a 2x2 chunk "pair"/ something larger, whatever fits your size)
    public Vector<Long> convertToGridPosition(long id) {
        Vector<Long> out = id > 8 ? convertToGridPosition(id / 9) : new Vector<>(Arrays.asList(0L,0L));
        long x = out.get(0) * 3;
        long z = out.get(1) * 3;

        long minor = id % 9;
        x += (minor + 1) % 3 - 1;
        z += (minor / 3 + 1) % 3 - 1;

        out.set(0, x);
        out.set(1, z);
        return out;
    }

    public long convertGridPositionToID(Vector<Long> vector) {
        return convToID(new Vector<>(vector));
    }

    private long convToID(Vector<Long> vector) {
        long x = vector.get(0);
        long z = vector.get(1);

        long id = Math.floorMod(x, 3) + (Math.floorMod(z, 3) * 3);

        x = Math.floorDiv(x + 1, 3);
        z = Math.floorDiv(z + 1, 3);
        if (x != 0 || z != 0) {
            vector.set(0, x);
            vector.set(1, z);
            id += 9 * convToID(vector);
        }
        return id;
    }

    public long findIDToFitGridSize(long size){
        if (size < 1) {
            throw new RuntimeException("size cannot be lower than 1");
        }

        // find smallest 3^n >= size
        long n = 1;
        while (size > n) {
            n *= 3;
        }
        n *= n; // n^2
        // take highest number n*m <= cursor (basically first id that a room of size "size" would occupy if it were placed one size x size block before)
        // then add 2*n - 1 to get the last id our actual room we are trying to find a spot for would occupy
        return cursor - (Math.floorMod(cursor - 1, n) + 1) + 2*n -1;
    }

    public void claimID(long id) {
        if (id >= cursor) {
            cursor = id + 1;
        }
    }
}
