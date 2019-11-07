package dev.kemikals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Utilities {

  public static void writeHistoryToFile(List<Cell[][]> history, File file, Game game) {
    StringBuilder sb = new StringBuilder(); // used to create our String to write to the file

    int i = 0;
    for (Cell[][] state : history) { // loop over our history list
      sb.append("State " + i++);
      sb.append(System.lineSeparator());
      for (Cell[] col : state) { // loop over each Array in the board
        for (Cell cell : col) { // loop over each cell in the array
          sb.append(game.numOfNeighbors(cell, state)); // append the number of neighbors to the
                                                       // String

        }

        sb.append(System.lineSeparator());
      }
      sb.append(System.lineSeparator());
    }

    try (PrintWriter pw = new PrintWriter(file)) {
      pw.print((sb.toString()));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
