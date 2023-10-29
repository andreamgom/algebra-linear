package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedColumnMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedRowMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class SparseMatrixBuilder {

    private List<Coordinate> coordinates;
    private int size;

    public CompressedColumnMatrix getCompressedColumnMatrix() {
        int nnz = coordinates.size();
        long[] values = new long[nnz];
        int[] rows = new int[nnz];
        int[] colPointers = new int[size + 1];

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
                    colPointers[k] = index;
                }
                currentCol = col;
            }
            index++;
        }

        for (int k = currentCol + 1; k <= size; k++) {
            colPointers[k] = nnz;
        }

        return new CompressedColumnMatrix(size, values, rows, colPointers);


    }

    public CompressedRowMatrix getCompressedRowMatrix() {
        int nnz = coordinates.size();
        long[] values = new long[nnz];
        int[] columns = new int[nnz];
        int[] rowPointers = new int[size + 1];

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

        return new CompressedRowMatrix(size, values, columns, rowPointers);
    }
    public SparseMatrixBuilder(int size) {
        this.size = size;
        this.coordinates = new ArrayList<>();
    }

    public void set(int i, int j, long value) {
        coordinates.add(new Coordinate(i, j, value));
    }


}

