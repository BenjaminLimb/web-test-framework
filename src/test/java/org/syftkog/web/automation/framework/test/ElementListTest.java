package org.syftkog.web.automation.framework.test;

import org.syftkog.web.automation.framework.test.pages.LocalTestPage;
import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.ElementList;
import org.syftkog.web.test.framework.Page;
import org.syftkog.web.test.framework.ElementListFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.elements.Container;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class ElementListTest {

  /**
   *
   */
  @Test(groups = "unit")
  public void testBasicUsage() {
    DriverFactory factory = new DriverFactory();
    Driver driver = factory.getDriver();

    Page p = new Page(driver, driver, "https://www.google.com/#q=national+park").load();
    ElementList<Container> topResuts = ElementListFactory.create(Container.class, p, "Search Results", "#ires li.g ", "#rso .srg:nth-of-type(2) .g:nth-of-type(###)");

    topResuts.get(1).click();
    driver.quit();
  }

  /**
   */
  @Test(groups = "unit")
  public void testForStaleStateWithPattern() {
    DriverFactory factory = new DriverFactory();
    Driver driver = factory.getDriver();

    LocalTestPage p = new LocalTestPage(driver);
    p.load();
    p.fruits.get(0).click();

    driver.navigateBack();

    p.fruits.get(1).click();

    driver.navigate().back();

    p.fruits.get(2).click();

    driver.navigate().back();

    p.fruits.get(3).click();

    driver.quit();
  }

  /**
   *
   */
  @Test(groups = "unit")
  public void testForStaleStateWithoutPattern() {
    DriverFactory factory = new DriverFactory();
    Driver driver = factory.getDriver();

    LocalTestPage p = new LocalTestPage(driver);
    p.load();
    
    p.fruitsWithoutPattern.get(0).click();

    driver.navigateBack();

    p.fruitsWithoutPattern.get(1).click();

    driver.navigate().back();

    p.fruitsWithoutPattern.get(2).click();

    driver.navigate().back();

    p.fruitsWithoutPattern.get(3).click();

    driver.quit();

  }

}
