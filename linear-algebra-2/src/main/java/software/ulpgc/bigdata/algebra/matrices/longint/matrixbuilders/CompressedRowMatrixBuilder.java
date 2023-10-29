package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedRowMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;

import java.util.List;
import java.util.Collections;

public class CompressedRowMatrixBuilder {
    private long[] values;
    private int[] columns;
    private int[] rowPointers;
    private final int size;

    public CompressedRowMatrixBuilder(int size) {
        this.size = size;
    }

    public void set(List<Coordinate> coordinates) {
        Collections.sort(coordinates, (c1, c2) -> {
            if (c1.getRow() != c2.getRow()) {
                return c1.getRow() - c2.getRow();
            } else {
                return c1.getColumn() - c2.getColumn();
            }
        });

        int nnz = coordinates.size();
        values = new long[nnz];
        columns = new int[nnz];
        rowPointers = new int[size + 1];

        int currentRow = -1;
        int index = 0;
        for (Coordinate coord : coordinates) {
            int row = coord.getRow();
            int col = coord.getColumn();
            long value = coord.getValue();

            values[index] = value;
            columns[index] = col;

            if (row != currentRow) {
                for (int k = currentRow + 1; k <= row; k++) {
                    rowPointers[k] = index;
                }
                currentRow = row;
            }
            index++;
        }
        for (int k = currentRow + 1; k <= size; k++) {
            rowPointers[k] = nnz;
        }
    }

    public CompressedRowMatrix get() {
        return new CompressedRowMatrix(size, values, columns, rowPointers);
    }
}
