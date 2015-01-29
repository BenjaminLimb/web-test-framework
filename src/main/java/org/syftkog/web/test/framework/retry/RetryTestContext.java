package org.syftkog.web.test.framework.retry;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public interface RetryTestContext<T extends RetryTestContext> {
      
  /**
   *
   * @return
   */
  public Integer getMaxRetryCount();      

  /**
   *
   * @return
   */
  public T retry();
}
