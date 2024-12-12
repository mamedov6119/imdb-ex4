package exercise4.indexing.utils;


import exercise4.indexing.primary.PrimaryIndex;
import exercise4.indexing.secondary.SecondaryIndex;

public class Table {
    private final Schema schema;
    private final PrimaryIndex primaryIndex;
    private final SecondaryIndex[] secondaryIndexes;

    public Table(Schema schema, PrimaryIndex primaryIndex) {
        this.schema = schema;
        this.primaryIndex = primaryIndex;
        this.secondaryIndexes = new SecondaryIndex[schema.columnCount()];
    }

    /**
     * Set the secondary index for the respective column, replacing any existing index.
     * Rows already in the table must be added to the index.
     */
    public void setSecondaryIndex(int columnIndex, SecondaryIndex index) {
        this.primaryIndex.scan().forEach(entry -> index
                .insert(entry.getValue().getColumn(columnIndex), entry.getKey()));
        this.secondaryIndexes[columnIndex] = index;
    }

    /**
     * Insert a row into the table by assigning a new TID and adding it to all primary/secondary indexes.
     */
    public void insert(Row row) {
        // TODO: inserts a row into the primary index and all existing secondary indexes.
        long tid = this.primaryIndex.insert(row);
        for (int i = 0; i < this.secondaryIndexes.length; i++) {
            if (this.secondaryIndexes[i] != null) {
                this.secondaryIndexes[i].insert(row.getColumn(i), tid);
            }
        }

//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(ResultSet resultSet) {
        // TODO:  removes all rows in set from the primary and all secondary indexes
        resultSet.getTids().forEach(tid -> {
            Row row = this.primaryIndex.get(tid);
            this.primaryIndex.remove(tid);
            for (int i = 0; i < this.secondaryIndexes.length; i++) {
                if (this.secondaryIndexes[i] != null) {
                    this.secondaryIndexes[i].remove(tid, row.getColumn(i));
                }
            }
        });
        return true;
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Perform a point query on the table. Returns the ResultSet of all rows with the respective column value at the given column.
     */
    public ResultSet pointQueryAtColumn(int columnIndex, Object value) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Perform a range query on the table. Returns the ResultSet of all rows with column value at columnIndex in the interval [from, to).
     */
    public ResultSet rangeQueryAtColumn(int columnIndex, Object from, Object to) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }
}