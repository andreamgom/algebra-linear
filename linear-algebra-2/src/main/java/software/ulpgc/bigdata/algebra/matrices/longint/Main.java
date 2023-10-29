package software.ulpgc.bigdata.algebra.matrices.longint;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.*;
import software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders.SparseMatrixBuilder;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.MatrixLoader;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.MatrixMultiplier;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String filePath = "C:/Users/Alcampo/IdeaProjects/linear-algebra-2/src/main/resources/494_bus.mtx";
            MatrixLoader matrixLoader = new MatrixLoader();

            // Load the matrix in SparseMatrix format
            SparseMatrix sparseMatrix = matrixLoader.loadSparseMatrix(filePath);
            //System.out.println("Original Matrix (SparseMatrix):");
            //sparseMatrix.printMatrix();

            // Load the matrix in DenseMatrix format
            DenseMatrix denseMatrix = matrixLoader.loadDenseMatrix(filePath);
            //System.out.println("Original Matrix (DenseMatrix):");
           // denseMatrix.printMatrix();

            // Create a SparseMatrixBuilder for the original matrix
            SparseMatrixBuilder builder = new SparseMatrixBuilder(sparseMatrix.getSize());

            // Load values into the SparseMatrixBuilder
            for (Coordinate coordinate : sparseMatrix.getCoordinates()) {
                int row = coordinate.getRow();
                int col = coordinate.getColumn();
                long value = coordinate.getValue();
                builder.set(row, col, value);
            }

            // Obtain the compressed CCS and CRS matrices using your classes
            CompressedColumnMatrix ccsMatrix = builder.getCompressedColumnMatrix();
            CompressedRowMatrix crsMatrix = builder.getCompressedRowMatrix();

            System.out.println("Size of the original matrix: " + sparseMatrix.getSize());
            System.out.println("Values of the elements in the original matrix (SparseMatrix):");
            for (Coordinate coordinate : sparseMatrix.getCoordinates()) {
                System.out.println("Row: " + coordinate.getRow() + " Col: " + coordinate.getColumn() + " Value: " + coordinate.getValue());
            }
            System.out.println("Resulting matrix in CCS format (CompressedColumnMatrix):");
            //ccsMatrix.printMatrix();

            System.out.println("Resulting matrix in CRS format (CompressedRowMatrix):");
            //crsMatrix.printMatrix();

            // Perform CCS*CRS multiplication
            SparseMatrix result = MatrixMultiplier.multiply(ccsMatrix, crsMatrix);

            // Display the result in matrix format
            System.out.println("Result of CCS*CRS multiplication:");
            result.printMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



