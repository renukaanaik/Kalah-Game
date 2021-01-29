package com.game.kalah.exception;

/**
 * @author Renuka Naik
 */
public class KalahServiceException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  public static final String COLON = ":";

	/*public KalahServiceException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}*/

  public KalahServiceException(String errorCode, String errorMessage) {
    super(errorCode + COLON + errorMessage);
  }

}