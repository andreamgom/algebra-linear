package software.ulpgc.bigdata.algebra.matrices.longint.operators;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.SparseMatrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixLoader {

    // Load the matrix with .mtx extension into a SparseMatrix
    public SparseMatrix loadSparseMatrix(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        List<Coordinate> coordinates = new ArrayList<>();
        int size = 0;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("%")) {
                continue;
            }
            String[] parts = line.trim().split("\\s+");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            //long value = Long.parseLong(parts[2]);
            long value = (long) Double.parseDouble(parts[2]);
            coordinates.add(new Coordinate(row, col, value));
            size = Math.max(size, Math.max(row, col));
        }
        br.close();
        return new SparseMatrix(size, coordinates);
    }

    public DenseMatrix loadDenseMatrix(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int size = 0;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("%")) {
                continue;
            }
            String[] parts = line.trim().split("\\s+");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            size = Math.max(size, Math.max(row, col));
        }
        br.close();

        long[][] matrix = new long[size + 1][size + 1];
        br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {
            if (line.startsWith("%")) {
                continue;
            }
            String[] parts = line.trim().split("\\s+");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            long value = (long) Double.parseDouble(parts[2]);
            matrix[row][col] = value;
        }
        br.close();

        return new DenseMatrix(matrix);
    }
}