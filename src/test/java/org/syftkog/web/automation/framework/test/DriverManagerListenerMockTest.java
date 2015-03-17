package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import org.syftkog.web.test.framework.DriverContext;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
//http://metlos.wordpress.com/2011/08/04/making-testng-listeners-apply-to-only-certain-classes/
//@Listeners(DriverManagerListener.class)
public class DriverManagerListenerMockTest {

    static Boolean debug = true;

  /**
   *
   * @return
   */
  @DataProvider(parallel = true)
    public Object[][] getData() {
        Object[][] series = new Object[5][2];
        series[0][0] = new DriverContextForTest();
        series[0][1] = new SkipException("Skip me");
        series[1][0] = new DriverContextForTest();
        series[2][0] = new DriverContextForTest();
        series[3][0] = new DriverContextForTest();
        series[4][0] = new DriverContextForTest();

        return series;
    }

  /**
   *
   * @param driver
   * @param time
   */
  public void sleep(Driver driver, int time) {
        try {
            for (int i = 0; i < time; i++) {
//                if (debug) {
//                    System.out.println(driver + " sleep " + i);
//                }

                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {

        }
    }

  /**
   *
   * @param context
   * @param th
   */
  @Test(groups = {""}, dataProvider = "getData", threadPoolSize = 5)
    public void reuseDriverTest(DriverContextForTest context, RuntimeException th) {
        Driver d = context.getDriver();
        d.get("http://someurl.com");
        sleep(d, 2);
        if (th != null) {
            throw th;
        }
    }

    class DriverContextForTest implements DriverContext<DriverContextForTest> {

        Driver driver;
        DriverFactory factory;

        public Driver driver() {
            if (driver == null) {
                driver = factory.getDriver(BrowserVersionPlatform.MOCK);
            }
            return driver;
        }

        @Override
        public Driver getDriver() {
            return driver;
        }

        @Override
        public DriverContextForTest setDriver(Driver driver) {
            this.driver = driver;
            return this;
        }

        @Override
        public DriverFactory getDriverFactory() {
            return factory;
        }

        @Override
        public DriverContextForTest setDriverFactory(DriverFactory factory) {
            this.factory = factory;
            return this;
        }

        @Override
        public Boolean isDriverInitialized() {
            return driver != null;
        }

    }

}
