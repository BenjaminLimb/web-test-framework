package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import org.syftkog.web.test.framework.elements.Container;
import org.syftkog.web.test.framework.elements.Link;
import org.syftkog.web.automation.framework.test.pages.GoogleResultsPage;
import org.syftkog.web.automation.framework.test.pages.NPSPage;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class ElementTest {

  
  /**
   *
   */
  @Test(groups = "unit-disabled")
  public void testChildElement() {
    Driver driver = new DriverFactory().getDriver(BrowserVersionPlatform.WIN7FF);

    GoogleResultsPage googleResultsPage = new GoogleResultsPage(driver, "grand canyon").load();

    Container firstResult = new Container(googleResultsPage, "First Result", "#rso .srg:nth-of-type(2) .g:nth-of-type(1)");
    Link firstResultsLink = new Link(firstResult, "First Result Link", ".r a");

    firstResultsLink.assertElement().containsText("Grand Canyon");
    firstResultsLink.click();

    driver.quit();
  }

  /**
   *
   */
  @Test(groups = "unit-disabled")
  public void testClickAndLoadPageFromClassDefinition() {
    Driver driver = new DriverFactory().getDriver(BrowserVersionPlatform.WIN7FF);

    GoogleResultsPage googleResultsPage = new GoogleResultsPage(driver, "grand canyon").load();
    Container firstResult = googleResultsPage.topResuts.get(0);
    NPSPage npsPage = firstResult.clickAndLoadPage(NPSPage.class);

    driver.quit();
  }

  /**
   *
   */
  @Test(groups = "unit-disabled")
  public void testClickAndLoadPageFromPageInstance() {
    Driver driver = new DriverFactory().getDriver(BrowserVersionPlatform.WIN7FF);

    GoogleResultsPage googleResultsPage = new GoogleResultsPage(driver, "grand canyon").load();
    Container firstResult = googleResultsPage.topResuts.get(0);

    NPSPage npsPage = (NPSPage) firstResult.clickAndLoadPage(new NPSPage(driver));

    driver.quit();
  }

}
