package com.game.kalah.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Kalah {

  @Column
  @Id
  private int gameId;
  @Column
  private GameStatus status;
  @Column
  private final int[] pits = new int[14];
  @Column
  private int selectedPit;
  @Column
  private int lastPitPosition;
  @Column
  private Player currentPlayer;
  @Column
  private Player nextPlayer;
  @Column
  private String winner;

  public int getGameId() {
    return gameId;
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }

  public GameStatus getStatus() {
    return status;
  }

  public void setStatus(GameStatus status) {
    this.status = status;
  }

  public int[] getPits() {
    return pits;
  }

  public int getSelectedPit() {
    return selectedPit;
  }

  public void setSelectedPit(int selectedPit) {
    this.selectedPit = selectedPit;
  }

  public int getLastPitPosition() {
    return lastPitPosition;
  }

  public void setLastPitPosition(int lastPitPosition) {
    this.lastPitPosition = lastPitPosition;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public Player getNextPlayer() {
    return nextPlayer;
  }

  public void setNextPlayer(Player nextPlayer) {
    this.nextPlayer = nextPlayer;
  }

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }

}
