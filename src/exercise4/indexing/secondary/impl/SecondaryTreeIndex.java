package exercise4.indexing.secondary.impl;


import exercise4.indexing.secondary.SecondaryIndex;

import java.util.*;

/**
 * An implementation of a SecondaryIndex around a TreeMap.
 */
public class SecondaryTreeIndex implements SecondaryIndex {
    // TODO: add field(s) as necessary
    private final TreeMap<Object, List<Long>> treeIndex;

    public SecondaryTreeIndex(Comparator<Object> cmp) {
        // TODO: The implementation should simply wrap Javas HashMap and TreeMap, respectively.
        this.treeIndex = new TreeMap<>(cmp);
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(Object value, Long tid) {
        // TODO
        if (treeIndex.containsKey(value)) {
            treeIndex.get(value).add(tid);
        } else {
            List<Long> list = new ArrayList<>();
            list.add(tid);
            treeIndex.put(value, list);
        }

//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Long> get(Object value) {
        // TODO
        return treeIndex.get(value);
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Long tid, Object value) {
        // TODO: make sure to only delete the given row/tid
        if (treeIndex.containsKey(value)) {
            List<Long> list = treeIndex.get(value);
            if (list.contains(tid)) {
                list.remove(tid);
                if (list.isEmpty()) {
                    treeIndex.remove(value);
                }
                return true;
            }
        }

        return false;
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Long> getRange(Object from, Object to) {
        // TODO
        if (treeIndex.subMap(from, to).isEmpty()) {
            return null;
        }
        return new ArrayList<>(treeIndex.subMap(from, to).values().stream().flatMap(List::stream).toList());
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
