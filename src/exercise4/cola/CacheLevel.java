package exercise4.cola;

import java.util.Iterator;

import xxl.core.util.Pair;

/**
 * Abstracts a cola-level held in the cache.
 */
public class CacheLevel<K extends Comparable<K>, V> implements COLALevel<K, V> {

    /** The underlying COLA block */
    private final COLABlock<K, V> block;

    /** Start index of this level in the block */
    private final int fromIndex;

    /** End index of this level in the block (exclusive!) */
    private final int toIndex;

    /**
     * @param block     the acutal block holding this level's elements
     * @param fromIndex the start-index of this level (inclusive)
     * @param toIndex   the end-index of this level (exclusive)
     */
    public CacheLevel(COLABlock<K, V> block, int fromIndex, int toIndex) {
        this.block = block;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new Iterator<>() {
            int i = fromIndex;

            @Override
            public boolean hasNext() {
                return i < toIndex;
            }

            @Override
            public Pair<K, V> next() {
                return block.get(i++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not supported");
            }
        };
    }

    @Override
    public V search(K key) {
        // TODO:
        int low = fromIndex;
        int high = toIndex - 1;

        while (low<=high) {
            int mid = low + (high - low) / 2;
            Pair<K, V> midElement = block.get(mid);

            int comparison = key.compareTo(midElement.getFirst());
            if (comparison == 0) {
                return midElement.getSecond(); // Key found
            } else if (comparison < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return null; // Key not found
    }

    @Override
    public void set(Iterator<Pair<K, V>> elms) {
        int i = fromIndex;
        while (elms.hasNext()) {
            block.set(i++, elms.next());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = fromIndex; i < toIndex; i++) {
            if (i > fromIndex)
                sb.append(", ");
            sb.append(block.get(i));
        }
        sb.append(']');
        return sb.toString();
    }
}