package org.syftkog.web.test.framework.retry;

import org.syftkog.web.test.framework.PropertiesRetriever;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 *
 * Used with RetryTestListener
 *
 * It's too late to change the TestResult Status in the Retry Analyzer so it
 * must be set in the Listener.
 *
 * @author BenjaminLimb
 */
public class RetryTestAnalyzer implements IRetryAnalyzer {

    private static final Integer DEFAULT_MAX_RETRY_COUNT = Integer.parseInt(PropertiesRetriever.getString("retry.maxRetryCount", "0"));

    int maxRetryCount = 0;
    int numOfRetries = 0;
    RetryTestContext context = null;

  /**
   *
   * @param context
   */
  public RetryTestAnalyzer(RetryTestContext context) {
      if(context.getMaxRetryCount() !=null){
        this.maxRetryCount = context.getMaxRetryCount();  
      }else{
          this.maxRetryCount = DEFAULT_MAX_RETRY_COUNT;
      }
        
        this.context = context;
    }

  /**
   *
   * @param maxRetryCount
   */
  public RetryTestAnalyzer(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

  /**
   *
   */
  public RetryTestAnalyzer() {
        this.maxRetryCount = DEFAULT_MAX_RETRY_COUNT;
    }

  /**
   *
   * @return
   */
  public boolean willRetry() {
        return numOfRetries < maxRetryCount;
    }

  /**
   *
   * @param itr
   * @return
   */
  @Override
    public boolean retry(ITestResult itr) {
        if (numOfRetries < maxRetryCount) {
            numOfRetries++;
            if (context != null) {
                context.retry();
            }
            return true;
        } else {
            return false;
        }
    }

  /**
   *
   */
  public static class RetryOnceAnalyzer extends RetryTestAnalyzer {

      /**
       *
       */
      public RetryOnceAnalyzer() {
            super(1);
        }
    }

  /**
   *
   */
  public static class RetryTwiceAnalyzer extends RetryTestAnalyzer {

      /**
       *
       */
      public RetryTwiceAnalyzer() {
            super(2);
        }

    }

}
