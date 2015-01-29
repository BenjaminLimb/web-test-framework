package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.TestCaseContext;
import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class TestCaseContextTest {

  /**
   *
   */
  @Test(groups = "unit")
    public void testSetWindowSize() {
        DriverFactory factory = new DriverFactory();
        Dimension dimension = new Dimension(400, 600);

        TestCaseContext context = new TestCaseContext();
        context.parameters()
                .setBrowserVersionPlatform(BrowserVersionPlatform.WIN7FF)
                .setWindowSize(dimension);
        context.setDriverFactory(factory);

        Driver driver = context.getDriver();

        Dimension actualDimension = driver.manage().window().getSize();
        Assert.assertEquals(actualDimension.height, dimension.height);
        Assert.assertEquals(actualDimension.width, dimension.width);

        driver.quit();
    }
}
