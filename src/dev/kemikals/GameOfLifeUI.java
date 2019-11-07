package dev.kemikals;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameOfLifeUI {
  public static Timer timer = null;
  private static Cell[][] originalBoard;
  public static Game game;
  private static int count;
  private static JLabel counter = new JLabel("0");
  private static int defaultSpeed = 3000; // 1 second
  private static int speedMin = 100;
  private static int speedMax = 10000;


  public void run() {
    JFrame frame = new JFrame();
    frame.setSize(800, 1000); // set the initial size of the window
    frame.setJMenuBar(createTopMenu()); // create the top menu bar (displays information like
                                        // current iteration)
    game = new Game(50); // create a new 50 x 50 cell game
    originalBoard = game.getBoard(); // set the original board to quicky reset to beginning
                                     // position.
    Box box = Box.createVerticalBox(); // create a Box to hold our grid and buttons
    Grid grid = new Grid(game.getBoard(), true); // create the grid
    box.add(grid); // add the grid to the box
    box.add(createBottomBar(grid)); // create the bottom buttons and slider and add them to the box
    frame.add(box); // add the box to the frame
    frame.setVisible(true); // set it as visible.
  }

  public JMenuBar createTopMenu() {
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(new JLabel("Current iteration: "));
    menuBar.add(counter);
    return menuBar;
  }

  public Box createSpeedSlider() {
    JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, speedMin, speedMax, defaultSpeed); // creates
                                                                                             // a
                                                                                             // new
                                                                                             // slider
                                                                                             // with
                                                                                             // our
                                                                                             // default
                                                                                             // values
    speedSlider.setPaintTicks(true);
    speedSlider.setPaintLabels(true);

    Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>(); // create a hashtable to
                                                                            // use for our
                                                                            // position(speed value)
                                                                            // and JLabel associated
                                                                            // with it
    position.put(100, new JLabel("0.1s"));
    position.put(1000, new JLabel("1s"));
    position.put(2000, new JLabel("2s"));
    position.put(3000, new JLabel("3s"));
    position.put(4000, new JLabel("4s"));
    position.put(5000, new JLabel("5s"));
    position.put(6000, new JLabel("6s"));
    position.put(7000, new JLabel("7s"));
    position.put(8000, new JLabel("8s"));
    position.put(8000, new JLabel("8s"));
    position.put(9000, new JLabel("9s"));
    position.put(10000, new JLabel("10s"));
    speedSlider.setLabelTable(position); // set the slider to use the above HashTable

    Box slider = Box.createVerticalBox(); // create a verticle box to store our Jslider and Jlabel
                                          // identifier above it
    JLabel speedText = new JLabel("Speed");
    speedText.setAlignmentX(Component.CENTER_ALIGNMENT);
    slider.add(speedText);
    slider.add(speedSlider);

    speedSlider.addChangeListener(new ChangeListener() { // this is the ChangeListener that will
                                                         // update the timer on the JSliders change.
      public void stateChanged(ChangeEvent e) {
        if (timer != null) {
          timer.setDelay(((JSlider) e.getSource()).getValue());
        }
      }
    });

    return slider;
  }

  public Box createBottomBar(Grid grid) {
    JButton start = new JButton("Start"); // create our four buttons
    JButton pause = new JButton("Pause");
    JButton reset = new JButton("Reset");
    JButton save = new JButton("Save");

    start.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (timer == null) { // if the timer is null we need to create the timer
          timer = new Timer(defaultSpeed, new ActionListener() { // creates a timer with an action
                                                                 // listener that will update the
                                                                 // board on each tick and our
                                                                 // counter and update it's contents
            public void actionPerformed(ActionEvent evt) {
              game.updateBoard();
              grid.update(game.getBoard(), false);
              count++;
              counter.setText(String.valueOf(count));

            }


          });
        }
        timer.start(); // start our timer
      }

    });

    pause.addActionListener(new ActionListener() { // action listener for pause button
      @Override
      public void actionPerformed(ActionEvent e) {
        if (timer != null) { // if the timer is not null stop it and update the board.
          timer.stop();
          grid.update(game.getBoard(), false);
        }
      }
    });

    reset.addActionListener(new ActionListener() { // handles reset button action
      @Override
      public void actionPerformed(ActionEvent e) {
        if (timer != null) {
          game.setBoard(originalBoard); // sets the game board to the original board
          grid.update(game.getBoard(), true); // updates the grid to the new game board
          count = 0; // reset count
          counter.setText(String.valueOf(count)); // display the new count;
        }
      }
    });

    save.addActionListener(new ActionListener() { // handles save action
      @Override
      public void actionPerformed(ActionEvent e) {
        if (timer != null) {
          timer.stop(); // stop the game if we click save
          grid.update(game.getBoard(), false);
        }

        JFrame parentFrame = new JFrame(); // create a new JFrame to display the file chooser

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selection a location to save game states");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
          File fileToSave = fileChooser.getSelectedFile();
          Utilities.writeHistoryToFile(game.getHistory(), fileToSave, game); // write the file
                                                                             // history to the
                                                                             // selected file
        }
      }


    });



    Box bottomBar = Box.createHorizontalBox(); // create a box to hold the 4 buttons and speed
                                               // slider
    bottomBar.setPreferredSize(new Dimension(20, 100)); // set it's size
    bottomBar.add(start); // add all of our buttons to the box
    bottomBar.add(pause);
    bottomBar.add(reset);
    bottomBar.add(save);

    bottomBar.add(Box.createHorizontalGlue()); // create the spacing between the Buttons and Slider

    bottomBar.add(createSpeedSlider()); // add the slider
    return bottomBar;
  }


}
