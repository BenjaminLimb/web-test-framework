package org.syftkog.testng.retry;

/**
 *
 * @author BenjaminLimb
 */
public class ExpectedException extends RuntimeException {

  /**
   *
   */
  public ExpectedException() {
    }

  /**
   *
   * @param message
   */
  public ExpectedException(String message) {
        super(message);
    }
}
