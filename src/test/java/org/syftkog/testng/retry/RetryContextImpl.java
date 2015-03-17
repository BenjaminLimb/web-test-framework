package org.syftkog.testng.retry;

import org.syftkog.web.test.framework.retry.RetryTestContext;

/**
 *
 * @author BenjaminLimb
 */
public class RetryContextImpl implements RetryTestContext<RetryContextImpl> {

    int maxRetryCount = 1;
    int currentAttempt = 1;

  /**
   *
   * @return
   */
  public int getCurrentAttempt() {
        return currentAttempt;
    }

  /**
   *
   * @return
   */
  @Override
    public Integer getMaxRetryCount() {
        return maxRetryCount;
    }

  /**
   *
   * @return
   */
  @Override
    public RetryContextImpl retry() {
        currentAttempt++;
        return this;
    }

  /**
   *
   * @param maxRetryCount
   */
  public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

}
