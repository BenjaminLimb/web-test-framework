package org.syftkog.testng.retry;

import org.syftkog.web.test.framework.retry.RetryTestListener;
import org.syftkog.web.test.framework.retry.RetryTestAnalyzer.RetryOnceAnalyzer;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
@Listeners({RetryTestListener.class, RetryOnceAnalyzerTestListener.class})
public class RetryOnceAnalyzerTest {

    /**
     * Tests to make sure that the RetryOnce Analyzer retries once and only
     * once.
     *
     */
    private int runCount = 0;

  /**
   *
   */
  @Test(retryAnalyzer = RetryOnceAnalyzer.class)
    public void testRetryOnceOnlyRetriesOnce() {
        runCount++;
        System.out.println("Run Count #" + runCount);
        if (runCount == 1) {
            throw new RuntimeException("This is the first time it was run.");
        }
        if (runCount == 2) {
            throw new ExpectedException("This is the second time it was run.");
        }
        if (runCount == 3) {
            throw new RuntimeException("This is the third time it was run.");
        }
    }

}
