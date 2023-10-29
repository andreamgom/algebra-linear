package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

public class Coordinate {
    private int row;
    private int column;
    private long value;

    public Coordinate(int row, int column, long value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public long getValue() {
        return value;
    }
}
