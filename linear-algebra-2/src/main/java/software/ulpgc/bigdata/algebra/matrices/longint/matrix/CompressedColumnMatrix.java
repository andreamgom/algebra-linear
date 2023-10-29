package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

public class CompressedColumnMatrix {
    int size;
    long[] values;
    int[] rows;
    int[] colPointers;

    public CompressedColumnMatrix(int size, long[] values, int[] rows, int[] colPointers) {
        this.size = size;
        this.values = values;
        this.rows = rows;
        this.colPointers = colPointers;
    }

    public void printMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }

    public long get(int i, int j) {
        int start = colPointers[j];
        int end = colPointers[j + 1];

        for (int k = start; k < end; k++) {
            if (rows[k] == i) {
                return values[k];
            }
        }
        return 0;
    }

    // TODO
    public long[] multiply(long[] vector) {
        if (size != vector.length) {
            throw new IllegalArgumentException("Incompatible dimensions");
        }
        long[] result = new long[size];
        for (int j = 0; j < size; j++) {
            int start = colPointers[j];
            int end = colPointers[j + 1];
            for (int i = start; i < end; i++) {
                result[rows[i]] += values[i] * vector[j];
            }
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    public long[] getValues() {
        return values;
    }

    public int[] getRows() {
        return rows;
    }

    public int[] getColPointers() {
        return colPointers;
    }
}
