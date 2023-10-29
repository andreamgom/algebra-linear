package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

public class DenseMatrix {
    private long[][] matrix;

    public DenseMatrix(long[][] matrix) {
        this.matrix = matrix;
    }

    public DenseMatrix multiply(DenseMatrix other) {
        int rowsThis = this.matrix.length;
        int colsThis = this.matrix[0].length;
        int colsOther = other.matrix[0].length;
        long[][] result = new long[rowsThis][colsOther];

        for (int i = 0; i < rowsThis; i++) {
            for (int j = 0; j < colsOther; j++) {
                for (int k = 0; k < colsThis; k++) {
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new DenseMatrix(result);
    }

    public int getRows() {
        return matrix.length;
    }

    public int getCols() {
        return matrix[0].length;
    }

    public long get(int i, int j) {
        return matrix[i][j];
    }

    public long[] multiplyVector(long[] vector) {
        int rowsThis = this.matrix.length;
        int colsThis = this.matrix[0].length;
        if (vector.length != colsThis) {
            throw new IllegalArgumentException("Incompatible dimensions for matrix and vector multiplication");
        }
        long[] result = new long[rowsThis];
        for (int i = 0; i < rowsThis; i++) {
            for (int j = 0; j < colsThis; j++) {
                result[i] += this.matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    public void printMatrix() {
        int size = getRows();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }
}
