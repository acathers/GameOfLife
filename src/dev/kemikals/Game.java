package dev.kemikals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

  private Cell[][] board; // current state of the game/board
  private List<Cell[][]> history = new ArrayList<>(); // a list of all the past states of the game.

  private final Random rand = new Random(); // used to generate random values

  public Game(int dimensions) {
    board = new Cell[dimensions][dimensions];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        board[i][j] = new Cell(rand.nextBoolean(), new Position(i, j)); // creates a new cell with a
                                                                        // position and random state
                                                                        // alive/dead
      }
    }
    history.add(board); // add the current board to the history
  }

  public Cell[][] getBoard() {
    return board;
  }

  public void setBoard(Cell[][] board) {
    this.board = board;
  }

  public int numOfNeighbors(Cell cell, Cell[][] board) {
    int count = 0;

    for (Direction direction : Direction.class.getEnumConstants()) { // loop over every direction
      if (findNeighboringCell(cell, direction, board).isAlive()) { // if there is an alive
                                                                   // neighboring cell, increase the
                                                                   // count
        count++;
      }
    }

    return count;
  }

  public void updateBoard() {
    Cell[][] temp = new Cell[board.length][board[0].length]; // create a temporary board to store
                                                             // the changes
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        Cell currentCell = new Cell(board[i][j]); // create a new cell from the old cell at the
                                                  // position
        int neighbors = numOfNeighbors(board[i][j], this.board); // store number of neighbors for
                                                                 // this cell
        if (currentCell.isAlive() && (neighbors < 2 || neighbors > 3)) { // if the current cell is
                                                                         // alive, and amount of
                                                                         // neighbors is less than 2
                                                                         // or greater than 3, kill
                                                                         // the cell
          currentCell.kill();
        }
        if (neighbors == 3 && !currentCell.isAlive()) { // if the number of neighbors is 3 and the
                                                        // current cell is not alive, ressurect it
          currentCell.ressurect();
        }
        temp[i][j] = currentCell; // set our temp board to the new state of the cell
      }

    }
    history.add(temp); // add the new state to our history
    board = temp; // set the board to our new state
  }

  public Cell findNeighboringCell(Cell cell, Direction direction, Cell[][] board) {
    int col = (cell.getPosition().getCol() + direction.getRowOffset()); // calculate column of the
                                                                        // cell we're going to check
                                                                        // is a neighbor
    int row = (cell.getPosition().getRow() + direction.getColOffset()); // calculate row of the cell
                                                                        // we're going to check is a
                                                                        // neighbor
    if (col < 0) {
      col += board.length; // if the result is a negative, add the length of the board to get the
                           // correct position for wrapping
    }
    if (row < 0) {
      row += board[0].length; // if the result is a negative, add the length of the board to get the
                              // correct position for wrapping
    }

    if (col >= board.length) {
      col = col % board.length; // if the result is greater than the board length, use modulus to
                                // get the correct position. i.e. if board length was 10, and result
                                // was 11. 11 mod 10 would be 1
    }
    if (row >= board.length) {
      row = row % board[0].length;
    }

    return board[col][row];
  }

  public List<Cell[][]> getHistory() {
    return history;
  }

}
