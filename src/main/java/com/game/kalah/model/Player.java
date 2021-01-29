package com.game.kalah.model;

/**
 * @author Renuka Naik
 */
public enum Player {

  NORTH(6, 0, 5),
  SOUTH(13, 7, 12);

  private int homePit;
  private int pitStartPosition;
  private int pitEndPosition;

  Player(int homePit, int pitStartPosition, int pitEndPosition) {
    this.homePit = homePit;
    this.pitStartPosition = pitStartPosition;
    this.pitEndPosition = pitEndPosition;
  }

  public int getHomePit() {
    return homePit;
  }

  public int getPitStartPosition() {
    return pitStartPosition;
  }

  public int getPitEndPosition() {
    return pitEndPosition;
  }

}
