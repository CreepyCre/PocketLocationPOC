import java.util.Arrays;
import java.util.Map;
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
    public Vector<Long> convertToPosition(long id) {
        Vector<Long> out = id > 8 ? convertToPosition(id / 9) : new Vector<>(Arrays.asList(0L,0L));
        long x = out.get(0) * 3;
        long z = out.get(1) * 3;

        long minor = id % 9;
        x += (minor + 1) % 3 - 1;
        z += (minor / 3 + 1) % 3 - 1;

        out.set(0, x);
        out.set(1, z);
        return out;
    }

    public long convertToID(Vector<Long> vector) {
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

    public long findIDToFit(long size){
        if (size < 1) {
            throw new RuntimeException("size cannot be lower than 1");
        }
        long n = 1;
        while (size > n) {
            n *= 3;
        }
        n *= n;
        return cursor - (cursor % n) + n - 1;
    }

    public void claimID(long id) {
        if (id >= cursor) {
            cursor = id + 1;
        }
    }
}
