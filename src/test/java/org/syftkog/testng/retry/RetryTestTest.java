package org.syftkog.testng.retry;

import org.syftkog.web.test.framework.retry.RetryTestListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * The first test makes sure that a test will
 *
 *
 *
 * @author BenjaminLimb
 */
@Listeners(RetryTestListener.class)
public class RetryTestTest {

    private Object[][] cachedTestDataA;

  /**
   *
   * @return
   */
  @DataProvider(parallel = true)
    public Object[][] getCachedTestDataA() {
        if (cachedTestDataA == null) {
            Object[][] o = new Object[1][1];
            RetryContextImpl context = new RetryContextImpl();
            context.setMaxRetryCount(1);
            o[0][0] = context;
            cachedTestDataA = o;
        }
        return cachedTestDataA;
    }

  /**
   *
   */
  public static int runCountA = 1;

  /**
   *
   * @param testCaseContext
   */
  @Test(dataProvider = "getCachedTestDataA", groups = {"unit"}, expectedExceptions = SecondException.class)
    public void testThatRetriesCorrectNumberOfTimes(RetryContextImpl testCaseContext) {

        if (runCountA == 1) {
            Assert.assertTrue(testCaseContext.getCurrentAttempt() == 1);
            runCountA++;
            throw new FirstException("First run");
        }
        if (runCountA == 2) {
            Assert.assertTrue(testCaseContext.getCurrentAttempt() == 2);
            runCountA++;
            throw new SecondException("Second run");
        }
        if (runCountA == 3) {
            Assert.assertTrue(testCaseContext.getCurrentAttempt() == 3);
            runCountA++;
            throw new ThirdException("Second run");
        }

    }

    private Object[][] cachedTestDataB;

  /**
   *
   * @return
   */
  @DataProvider(parallel = true)
    public Object[][] getCachedTestDataB() {
        if (cachedTestDataB == null) {
            Object[][] o = new Object[1][1];
            RetryContextImpl context = new RetryContextImpl();
            context.setMaxRetryCount(2);
            o[0][0] = context;
            cachedTestDataB = o;
        }
        return cachedTestDataB;
    }

  /**
   *
   */
  public static int runCountB = 1;

  /**
   *
   * @param testCaseContext
   */
  @Test(dataProvider = "getCachedTestDataB", groups = {"unit"}, expectedExceptions = ThirdException.class)
    public void testRetriesWithParameterDrivenRetryAnalzyer(RetryContextImpl testCaseContext) {

        if (runCountB == 1) {
            Assert.assertTrue(testCaseContext.getCurrentAttempt() == 1);
            runCountB++;
            throw new FirstException("First run");
        }
        if (runCountB == 2) {
            Assert.assertTrue(testCaseContext.getCurrentAttempt() == 2);
            runCountB++;
            throw new SecondException("Second run");
        }
        if (runCountB == 3) {
            Assert.assertTrue(testCaseContext.getCurrentAttempt() == 3);
            runCountB++;
            throw new ThirdException("Second run");
        }

    }

    private class FirstException extends RuntimeException {

        public FirstException(String message) {
            super(message);
        }
    }

    private class SecondException extends RuntimeException {

        public SecondException(String message) {
            super(message);
        }
    }

    private class ThirdException extends RuntimeException {

        public ThirdException(String message) {
            super(message);
        }
    }

}
