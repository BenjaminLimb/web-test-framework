package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.Page;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.HasDriver;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class PageTest {

    DriverFactory factory = new DriverFactory();

  /**
   *
   */
  @Test(groups = "unit")
    public void intatiateSimplePage() {
        Driver driver = factory.getDriver();
        HomePage page = new HomePage(driver, "http://google.com", true);
        page.load();
        driver.quit();
    }

  /**
   *
   */
  @Test
    public void intatiateNestablePage() {
        Driver driver = factory.getDriver();

        Page parent = new Page(driver, driver, "http://google.com");
        Page child = new Page(driver, parent, "http://google.com");
        driver.quit();
    }

    private class HomePage extends Page {

        public HomePage(HasDriver hasDriver, String url, Boolean hasJQuery) {
            super(hasDriver, url);
        }

    }

}
