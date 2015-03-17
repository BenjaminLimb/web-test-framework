package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.TestCaseContext;
import org.syftkog.web.test.framework.TestCaseParameters;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import org.syftkog.web.test.framework.DriverContext;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
//@Listeners(DriverManagerListener.class)
public class DriverManagerSauceLabsReporterTest {

  /**
   *
   * @return
   */
  @DataProvider(parallel = true)
    public Object[][] getData() {
        Object[][] series = new Object[1][1];
        series[0][0] = new TestCaseContext(new TestCaseParameters().setBrowserVersionPlatform(BrowserVersionPlatform.WIN7FF));
        return series;
    }

  /**
   *
   * @param context
   */
  @Test(groups = {""}, dataProvider = "getData", threadPoolSize = 5)
    public void testFailedReport(DriverContext context) {
        Driver d = context.getDriver();
        Assert.fail(null, null);
    }
}
