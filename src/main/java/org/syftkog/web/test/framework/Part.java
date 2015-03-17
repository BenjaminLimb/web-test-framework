package org.syftkog.web.test.framework;

import org.openqa.selenium.SearchContext;

/**
 *
 * @author BenjaminLimb
 */
public class Part implements HasDriver, HasSearchContext {

    private final Driver driver;
    private final SearchContext searchContext;

  /**
   *
   * @param page
   */
  public Part(Page page) {
        this.driver = page.getDriver();
        this.searchContext = page.getSearchContext();
    }

  /**
   *
   * @param driver
   * @param searchContext
   */
  public Part(Driver driver, SearchContext searchContext) {
        this.driver = driver;
        this.searchContext = searchContext;
    }

  /**
   *
   * @param hasDriver
   */
  public Part(HasDriver hasDriver) {
        this.driver = hasDriver.getDriver();
        this.searchContext = hasDriver.getDriver();
    }

  /**
   *
   * @return
   */
  @Override
    public Driver getDriver() {
        return driver;
    }

  /**
   *
   * @return
   */
  @Override
    public SearchContext getSearchContext() {
        return searchContext;
    }

}
