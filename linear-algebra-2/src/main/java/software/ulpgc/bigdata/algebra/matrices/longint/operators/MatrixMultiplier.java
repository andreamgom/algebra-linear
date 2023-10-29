package software.ulpgc.bigdata.algebra.matrices.longint.operators;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MatrixMultiplier {
    public static SparseMatrix multiply(CompressedColumnMatrix a, CompressedRowMatrix b) {
        int size = a.getSize();
        List<Coordinate> resultCoordinates = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                long sum = 0;
                int rowStartA = a.getColPointers()[j];
                int rowEndA = a.getColPointers()[j + 1];
                int rowStartB = b.getRowPointers()[i];
                int rowEndB = b.getRowPointers()[i + 1];

                int indexA = rowStartA;
                int indexB = rowStartB;

                while (indexA < rowEndA && indexB < rowEndB) {
                    int colA = a.getRows()[indexA];
                    int colB = b.getColumns()[indexB];

                    if (colA == colB) {
                        sum += a.getValues()[indexA] * b.getValues()[indexB];
                        indexA++;
                        indexB++;
                    } else if (colA < colB) {
                        indexA++;
                    } else {
                        indexB++;
                    }
                }

                if (sum != 0) {
                    resultCoordinates.add(new Coordinate(i, j, sum));
                }
            }
        }

        return new SparseMatrix(size, resultCoordinates);
    }

    public static long[] generateVector(int size) {
        Random random = new Random();
        long[] v = new long[size];
        for (int i = 0; i < size; i++) {
            v[i] = random.nextLong();
        }
        return v;
    }

    // Function to validate CCS*CRS multiplication
    public static boolean validateCCSAndCRS(CompressedColumnMatrix A, CompressedRowMatrix B, SparseMatrix C) {
        // Verify A*B = C equality
        SparseMatrix AB = multiply(A, B);
        boolean condition1 = AB.equals(C);

        // Verify A*(B*v) = C*v equality
        // Generate a random vector
        long[] v = generateVector(B.getSize());

        long[] Bv = B.multiply(v);
        long[] AvBv = A.multiply(Bv);
        long[] Cv = C.multiplyWithVector(C.getCoordinates(), v, C.getSize());

        boolean condition2 = Arrays.equals(AvBv, Cv);

        return condition1 && condition2;
    }

    // Function to validate dense*dense matrix multiplication
    public static boolean validateDenseMatrix(DenseMatrix A, DenseMatrix B, DenseMatrix C) {
        // Verify A*B = C equality
        DenseMatrix AB = A.multiply(B);
        boolean condition1 = AB.equals(C);

        // Verify A*(B*v) = C*v equality
        long[] v = generateVector(A.getRows());

        long[] Bv = B.multiplyVector(v);
        long[] AvBv = A.multiplyVector(Bv);
        long[] Cv = C.multiplyVector(v);

        boolean condition2 = Arrays.equals(AvBv, Cv);

        return condition1 && condition2;
    }
}
