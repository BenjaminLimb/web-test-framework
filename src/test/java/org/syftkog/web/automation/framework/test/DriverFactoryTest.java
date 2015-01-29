package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.DesiredCapabilitiesFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class DriverFactoryTest {

    DriverFactory factory = new DriverFactory();

  /**
   *
   */
  @Test(groups = "unit")
    public void testGetDriver() {
        WebDriver driver = factory.getDriver();
        Assert.assertNotNull(driver);
        driver.quit();
    }

  /**
   *
   */
  @Test(groups = "unit")
    public void testGetDriverWithCapabilitites() {
        DesiredCapabilities caps = DesiredCapabilitiesFactory.htmlunit();

        WebDriver driver = factory.getDriver(caps);
        Assert.assertNotNull(driver);
        driver.quit();
    }

}
