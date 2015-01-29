package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class DriverRecycleTest {

  /**
   *
   */
  @Test(groups = "unit")
    public void testBasicRecycle() {
        DriverFactory factory = new DriverFactory();
        factory.setRecylceDrivers(Boolean.TRUE);

        Driver driverA = factory.getDriver(BrowserVersionPlatform.MOCK); // Default is Windows 7 Firefox on local machine
        driverA.navigate().to("http://google.com");
        driverA.recycle(); // Puts the driver back in the factory to be used again

        Driver driverB = factory.getDriver(BrowserVersionPlatform.MOCK); // Request an available driver
        Assert.assertTrue(driverA.hashCode() == driverB.hashCode());

        factory.quitAll();
    }

  /**
   *
   */
  @Test(groups = "unit")
    public void testAdvancedRecylce() {
        DriverFactory factory = new DriverFactory(2);
        factory.setRecylceDrivers(Boolean.TRUE);

        Driver driverRequestA = factory.getDriver(BrowserVersionPlatform.MOCK);
        Assert.assertTrue(factory.getInstantiatedDriverCount() == 1);
        Assert.assertTrue(factory.getIdleDriverCount() == 0);

        driverRequestA.recycle();
        Assert.assertTrue(factory.getInstantiatedDriverCount() == 1, factory.getInstantiatedDriverCount() + " == 1");
        Assert.assertTrue(factory.getIdleDriverCount() == 1);

        Driver driverRequestB = factory.getDriver(BrowserVersionPlatform.MOCK);
        Assert.assertTrue(factory.getInstantiatedDriverCount() == 1);
        Assert.assertTrue(factory.getIdleDriverCount() == 0);

        Driver driverRequestC = factory.getDriver(BrowserVersionPlatform.MOCK);
        Assert.assertTrue(factory.getInstantiatedDriverCount() == 2);
        Assert.assertTrue(factory.getIdleDriverCount() == 0);

        driverRequestB.quit();
        Assert.assertTrue(factory.getInstantiatedDriverCount() == 1);
        Assert.assertTrue(factory.getIdleDriverCount() == 0);

        driverRequestC.recycle();
        Assert.assertTrue(factory.getInstantiatedDriverCount() == 1);
        Assert.assertTrue(factory.getIdleDriverCount() == 1);

        Driver driverRequestD = factory.getDriver(BrowserVersionPlatform.MOCK);
        Assert.assertTrue(factory.getInstantiatedDriverCount() == 1);
        Assert.assertTrue(factory.getIdleDriverCount() == 0);

        Driver driverRequestE = factory.getDriver(BrowserVersionPlatform.MOCK);
        Assert.assertTrue(factory.getInstantiatedDriverCount() == 2);
        Assert.assertTrue(factory.getIdleDriverCount() == 0);
        driverRequestE.recycle();

        factory.quitAll();

    }

}
