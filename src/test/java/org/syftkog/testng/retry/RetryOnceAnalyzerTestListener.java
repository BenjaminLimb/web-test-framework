package org.syftkog.testng.retry;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * This is a Listener to verify that the RetryTestListener and RetryTestAnalyzer are correctly retrying tests.
 * We can't use the testng expected exceptions annotation because then it wouldn't even mark the test as failed in the first place.
 * @author BenjaminLimb
 */
public class RetryOnceAnalyzerTestListener implements ITestListener {

  /**
   *
   * @param result
   */
  @Override
    public void onTestStart(ITestResult result) {
    }

  /**
   *
   * @param result
   */
  @Override
    public void onTestSuccess(ITestResult result) {

    }

  /**
   *
   * @param result
   */
  @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (throwable != null && throwable instanceof ExpectedException) {
            result.setStatus(ITestResult.SUCCESS);
        }
    }

  /**
   *
   * @param result
   */
  @Override
    public void onTestSkipped(ITestResult result) {

    }

  /**
   *
   * @param result
   */
  @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

  /**
   *
   * @param context
   */
  @Override
    public void onStart(ITestContext context) {

    }

  /**
   *
   * @param context
   */
  @Override
    public void onFinish(ITestContext context) {

    }

}
