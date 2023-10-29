package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedColumnMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;

import java.util.List;

import java.util.Collections;

public class CompressedColumnMatrixBuilder {
    private long[] values;
    private int[] rows;
    private int[] col_ptr;
    private final int size;

    public CompressedColumnMatrixBuilder(int size) {
        this.size = size;
    }

    public void set(List<Coordinate> coordinates) {
        Collections.sort(coordinates, (c1, c2) -> {
            if (c1.getColumn() != c2.getColumn()) {
                return c1.getColumn() - c2.getColumn();
            } else {
                return c1.getRow() - c2.getRow();
            }
        });

        int nnz = coordinates.size();
        values = new long[nnz];
        rows = new int[nnz];
        col_ptr = new int[size + 1];

        int currentCol = -1;
        int index = 0;
        for (Coordinate coord : coordinates) {
            int col = coord.getColumn();
            int row = coord.getRow();
            long value = coord.getValue();

            values[index] = value;
            rows[index] = row;

            if (col != currentCol) {
                for (int k = currentCol + 1; k <= col; k++) {
                    col_ptr[k] = index;
                }
                currentCol = col;
            }
            index++;
        }
        for (int k = currentCol + 1; k <= size; k++) {
            col_ptr[k] = nnz;
        }
    }


    public CompressedColumnMatrix get() {
        return new CompressedColumnMatrix(size, values, rows, col_ptr);
    }

    public void set(int i, int i1, int i2) {
    }

}
