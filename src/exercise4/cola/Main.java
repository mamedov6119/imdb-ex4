package exercise4.cola;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;

import xxl.core.collections.containers.io.BlockFileContainer;
import xxl.core.cursors.sources.io.FileInputCursor;
import xxl.core.io.converters.Converter;
import xxl.core.io.converters.DoubleConverter;
import xxl.core.io.converters.LongConverter;
import xxl.core.util.Pair;

public class Main {
    public static void main(String[] args) {

        BlockFileContainer raw = new BlockFileContainer("COLA", BasicCOLA.DISK_BLOCK_SIZE);

        BasicCOLA<Long, Double> cola = new BasicCOLA<>(
                LongConverter.SIZE + DoubleConverter.SIZE,
                LongConverter.DEFAULT_INSTANCE,
                DoubleConverter.DEFAULT_INSTANCE,
                raw);

        FileInputCursor<Pair<Long, Double>> input = new FileInputCursor<>(new Converter<>() {
            @Override
            public Pair<Long, Double> read(DataInput dataInput,
                                           Pair<Long, Double> object) throws IOException {
                return new Pair<>(dataInput.readLong(), dataInput.readDouble());
            }

            @Override
            public void write(DataOutput dataOutput, Pair<Long, Double> object)
                    throws IOException {
                dataOutput.writeLong(object.getFirst());
                dataOutput.writeDouble(object.getSecond());
            }
        }, new File("timeseries.bin"));

        input.open();
        for (int i = 0; input.hasNext(); i++) {
            Pair<Long, Double> p = input.next();
            cola.insertElement(p.getFirst(), p.getSecond());
            if (i < 10) {
                System.out.println("Inserting: " + p);
                cola.printLevels();
            }
        }
        input.close();

        // Search for the first and last inserted elements
        long key1 = 1325458800000L;
        System.out.println("Searching key: " + key1 + ", result: " + cola.searchElement(key1));
        long key2 = 1262127600000L;
        System.out.println("Searching key: " + key2 + ", result: " + cola.searchElement(key2));

        // TODO: benchmark the search for the first and the last inserted keys
        System.out.println("Starting benchmark...");
        benchmarkSearch(cola, key1);
        benchmarkSearch(cola, key2);

        cola.close();
    }

    private static void benchmarkSearch(BasicCOLA<Long, Double> cola, long key) {
        long startTime = System.nanoTime();
        Double result = cola.searchElement(key);
        long endTime = System.nanoTime();
        System.out.println("Searching key: " + key + ", result: " + result +
                ", time taken: " + (endTime - startTime) + " ns");
    }
}
