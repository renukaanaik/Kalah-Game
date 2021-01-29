package com.game.kalah.exception;

/**
 * @author Renuka Naik
 */
public enum Errors {

  INVALID_PIT_SELECTED("KSE-001", "Invalid Pit Selected"),
  INVALID_TURN("KSE-002", "Invalid Player has taken the turn"),
  INVALID_STATE("KSE-003", "Game has already Finished"),
  GAME_DOES_NOT_EXIST("KSE-004", "Game does not exist"),
  GAME_ALREADY_IN_PROGRESS("KSE-005", "Game is already in progress");

  private final String errorCode;
  private final String errorMessage;

  Errors(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

}
