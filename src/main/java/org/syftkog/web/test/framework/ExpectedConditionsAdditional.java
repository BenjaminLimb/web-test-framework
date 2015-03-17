package org.syftkog.web.test.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Additional {@link ExpectedCondition}s which are generally useful within
 * webdriver tests.
 *
 * @author Benjamin Limb
 */
public class ExpectedConditionsAdditional {

    private final static Logger LOG = LoggerFactory.getLogger(ExpectedConditionsAdditional.class);

    // Utility class
    private ExpectedConditionsAdditional() {

    }

    /**
     * @see http://www.w3schools.com/jsref/prop_doc_readystate.asp
     * @return
     */
    public static ExpectedCondition<Boolean> documentReadyStateCondition() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
    }

  /**
   *
   * @param oldURL
   * @return
   */
  public static ExpectedCondition<Boolean> urlChange(final String oldURL) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) (!driver.getCurrentUrl().equalsIgnoreCase(oldURL));
            }
        };
    }

  /**
   *
   * @param urlPart
   * @return
   */
  public static ExpectedCondition<Boolean> urlToContain(final String urlPart) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) (driver.getCurrentUrl().contains(urlPart));
            }
        };
    }

  /**
   *
   * @return
   */
  public static ExpectedCondition<Boolean> jQueryExists() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((Boolean) ((JavascriptExecutor) driver).executeScript("return (typeof window.jQuery == 'function')"));
            }
        };
    }

  /**
   *
   * @return
   */
  public static ExpectedCondition<Boolean> jQueryNotActive() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Long jQueryActiveCount = (Long) ((JavascriptExecutor) driver).executeScript("return window.jQuery.active");
                LOG.trace("jQuery Active Count:" + jQueryActiveCount);
                return (Boolean) ((JavascriptExecutor) driver).executeScript("return window.jQuery.active == 0");
            }
        };
    }

}
