package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

public class CompressedRowMatrix {
    int size;
    long[] values;
    int[] columns;
    int[] rowPointers;

    public CompressedRowMatrix(int size, long[] values, int[] columns, int[] rowPointers) {
        this.size = size;
        this.values = values;
        this.columns = columns;
        this.rowPointers = rowPointers;
    }

    public long get(int i, int j) {
        // Implement the logic to obtain the value in CRS
        int rowStart = rowPointers[i];
        int rowEnd = rowPointers[i + 1];

        for (int k = rowStart; k < rowEnd; k++) {
            if (columns[k] == j) {
                return values[k];
            }
        }
        return 0;
    }

    public void printMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();  // Line break for printing the next row
        }
    }

    public long[] multiply(long[] vector) {
        if (size != vector.length) {
            throw new IllegalArgumentException("Incompatible dimensions");
        }
        long[] result = new long[size];
        for (int i = 0; i < size; i++) {
            int start = rowPointers[i];
            int end = rowPointers[i + 1];
            for (int j = start; j < end; j++) {
                result[i] += values[j] * vector[columns[j]];
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

    public int[] getColumns() {
        return columns;
    }

    public int[] getRowPointers() {
        return rowPointers;
    }
}
