package dev.kemikals;

// basic class to represent a position. holds the row and col.
public class Position {
  private final int row;
  private final int col;

  public Position(int col, int row) {
    super();
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public String toString() {
    return String.format("(%d, %d)%n", row, col);
  }

}
