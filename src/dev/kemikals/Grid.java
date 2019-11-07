package dev.kemikals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

class Grid extends JPanel {

  LayoutManager layout; // stores our layout manager

  Grid(Cell[][] grid, boolean custom) {
    layout = new GridLayout(grid.length, grid[0].length, 1, 1);
    setLayout(layout); // sets the Layout to
                       // GridLayout with
                       // dimension x dimension and a value of 1
                       // for hgap and vgap to show grid lines
    setBackground(Color.BLACK); // sets background to black to show grid lines (shows through our
                                // gaps we created)

    createCells(grid, custom); // creats all the cells
  }

  void update(Cell[][] grid, boolean clickable) {

    removeAll(); // remove all the current cells from the grid
    createCells(grid, clickable); // creates all of our cells
    revalidate(); // forces the layout manager to recalculate.
    repaint(); // repaints our new grid.
  }

  public void createCells(Cell[][] grid, boolean clickable) {
    for (int col = 0; col < grid.length; col++) {
      for (int row = 0; row < grid[0].length; row++) {
        Cell currentCell = grid[col][row];
        JPanel gridCell = new JPanel() { // creat a new JPanel to display each cell
          @Override
          public Dimension getPreferredSize() {
            return new Dimension(10, 10);
          }
        };

        gridCell.setBackground(grid[col][row].isAlive() ? Color.GREEN : Color.RED); // sets the
                                                                                    // color of the
                                                                                    // cell
                                                                                    // depending on
                                                                                    // if it is
                                                                                    // alive or dead
        if (clickable) { // if we want it to be clickable
          gridCell.addMouseListener(new MouseAdapter() {
            Cell thisCell = currentCell; // allow access to the currentCell

            @Override
            public void mouseClicked(MouseEvent e) { // handle mouse click
              if (e.getButton() == MouseEvent.BUTTON1) {

                if (thisCell.isAlive()) { // if it's alive set it to red and kill the cell
                  gridCell.setBackground(Color.RED);
                  thisCell.kill();
                } else { // otherwise the cell is dead, so set it to green and ressurect it.
                  gridCell.setBackground(Color.GREEN);
                  thisCell.ressurect();
                }

              }

            }
          });
        }
        add(gridCell); // add the grid cell to the JPanel ( method is inherited from JPanel since we
                       // extend)
      }
    }
  }
}
