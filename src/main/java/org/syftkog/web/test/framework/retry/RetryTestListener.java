package org.syftkog.web.test.framework.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.internal.TestResult;

/**
 *
 * This test listener handles test retries. If you test is data driven with a
 parameter that implements RetryTestContext it will append the RetryTestAnalyzer.

 Note: The
 *
 * @author BenjaminLimb
 */
public class RetryTestListener implements ITestListener {
    private RetryTestContext getRetryContext(ITestResult tr) {
        Object[] parameters = tr.getParameters();
        for (Object o : parameters) {
            if (o instanceof RetryTestContext) {
                return (RetryTestContext) o;
            }
        }
        return null;
    }

  /**
   *
   * @param result
   */
  @Override
    public void onTestStart(ITestResult result) {
        RetryTestContext context = getRetryContext(result);
        if (context != null) {
            
            if (result.getMethod().getRetryAnalyzer() == null) {
                IRetryAnalyzer retryAnalyzer = new RetryTestAnalyzer(context);
                result.getMethod().setRetryAnalyzer(retryAnalyzer);
            }
        }
    }

  /**
   *
   * @param result
   */
  @Override
    public void onTestFailure(ITestResult result) {
        IRetryAnalyzer retryAnalyzer = result.getMethod().getRetryAnalyzer();
        if(retryAnalyzer instanceof RetryTestAnalyzer){
            RetryTestAnalyzer retryTestAnalyzer = (RetryTestAnalyzer) retryAnalyzer;
            
            if (retryTestAnalyzer.willRetry()) {
                result.setStatus(TestResult.SKIP);
            }
            
        }
        
//        RetryTestContext context = getRetryContext(result);
//        if (context != null) {
//
//            IRetryAnalyzer retryAnalyzer = result.getMethod().getRetryAnalyzer();
//            Assert.assertTrue(retryAnalyzer instanceof RetryTestAnalyzer, "RetryAnalyzer wasn't initialized first.");
//            RetryTestAnalyzer analyzer = (RetryTestAnalyzer) retryAnalyzer;
//            
//            // It's too late to change the TestResult Status in the Retry Analyzer so it
//            // must be set here. Feel free to set it to PASS if desired.
//            if (analyzer.willRetry()) {
//                result.setStatus(TestResult.SKIP);
//            }
//        }
        
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
