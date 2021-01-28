import java.util.*;

public class CoordinateConverter {

    // key -> size
    // value -> id cursor of that size
    private final SortedMap<Long, Long> cursorMap = new TreeMap<>();

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

    If last id in area is lowest position, then we can use the last id as a pockets assigned position.
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

    public long findNextIDAndClaim(long size){
        if (size < 1) {
            throw new RuntimeException("size cannot be lower than 1");
        }
        // find smallest 3^l >= size
        long n = 1;
        while (size > n) {
            n *= 3;
        }

        long m = n * n; // n^2

        long cursor = cursorMap.headMap(n+1).values().stream().mapToLong(num -> num).max().orElse(-1L);
        cursor = cursor - (Math.floorMod(cursor, m)) + m; // lowest id in the next area of size "size"
        while (true) {
            long finalCursor = cursor;
            OptionalLong largestSizeAtID = cursorMap.tailMap(n+1).entrySet().stream().filter(entry -> entry.getValue().equals(finalCursor)).mapToLong(Map.Entry::getKey).max();
            if (!largestSizeAtID.isPresent()) break;
            cursor += largestSizeAtID.getAsLong() * largestSizeAtID.getAsLong();
        }

        cursorMap.put(n, cursor);
        return cursor;
    }
}
