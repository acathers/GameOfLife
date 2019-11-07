package dev.kemikals;

// This enum is used to store the possible directions we can move in our multidimensional array
// world. Negative is up with respect to the Y axis, and negative is left with respect to the X axis
public enum Direction {
  NORTH(0, -1), NORTH_EAST(1, -1), EAST(1, 0), SOUTH_EAST(1, 1), SOUTH(0, 1), SOUTH_WEST(-1,
      1), WEST(-1, 0), NORTH_WEST(-1, -1);


  private int col, row;

  private Direction(int col, int row) {
    this.col = col;
    this.row = row;
  }

  public int getRowOffset() {
    return row;
  }

  public int getColOffset() {
    return col;
  }
}
