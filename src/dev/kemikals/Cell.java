package dev.kemikals;

// basic class to represent a Single cell. holds it's position and wheter it is alive or dead

public class Cell {

  private boolean alive;
  private Position position;

  public Cell(boolean alive, Position position) {
    this.alive = alive;
    this.position = position;
  }

  public Cell(Cell cell) {
    this.alive = cell.isAlive();
    this.position = cell.getPosition();
  }

  public boolean isAlive() {
    return alive;
  }

  public Position getPosition() {
    return position;
  }

  public void kill() {
    alive = false;
  }

  public void ressurect() {
    alive = true;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }


}
