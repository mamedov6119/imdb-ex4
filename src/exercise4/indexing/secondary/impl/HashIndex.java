package exercise4.indexing.secondary.impl;


import exercise4.indexing.secondary.SecondaryIndex;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An implementation of a SecondaryIndex around a HashMap.
 */
public class HashIndex implements SecondaryIndex {
    // TODO: add field(s) as necessary
    private final HashMap<Object, List<Long>> hashIndex;

    public HashIndex() {
        // TODO
        this.hashIndex = new HashMap<>();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(Object value, Long tid) {
        // TODO
        if (hashIndex.containsKey(value)) {
            hashIndex.get(value).add(tid);
        } else {
            List<Long> list = new ArrayList<>();
            list.add(tid);
            hashIndex.put(value, list);
        }
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Long> get(Object value) {
        // TODO
        return hashIndex.get(value);
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Long tid, Object value) {
        // TODO: make sure to only delete the given row/tid
        if (hashIndex.containsKey(value)) {
            List<Long> list = hashIndex.get(value);
            if (list.contains(tid)) {
                list.remove(tid);
                if (list.isEmpty()) {
                    hashIndex.remove(value);
                }
                return true;
            }
        }

        return false;
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}