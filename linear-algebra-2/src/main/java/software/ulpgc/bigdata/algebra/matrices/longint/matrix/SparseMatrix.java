package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

import java.util.List;

public class SparseMatrix {
    private int size;
    private List<Coordinate> coordinates;

    public SparseMatrix(int size, List<Coordinate> coordinates) {
        this.size = size;
        this.coordinates = coordinates;
    }

    public int getSize() {
        return size;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public long get(int i, int j) {
        for (Coordinate coordinate : coordinates) {
            if (coordinate.getRow() == i && coordinate.getColumn() == j) {
                return coordinate.getValue();
            }
        }
        return 0;
    }

    public static long[] multiplyWithVector(List<Coordinate> sparseMatrix, long[] vector, int size) {
        if (sparseMatrix == null || sparseMatrix.isEmpty() || vector == null || vector.length != size) {
            throw new IllegalArgumentException("Invalid input");
        }

        long[] result = new long[size];
        for (Coordinate coordinate : sparseMatrix) {
            int row = coordinate.getRow();
            int col = coordinate.getColumn();
            long value = coordinate.getValue();
            result[row] += value * vector[col];
        }

        return result;
    }

    public void printMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }
}
