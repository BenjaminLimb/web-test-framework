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

  
  // TODO: NEEDS TESTING AND VERIFICATION
  //http://stackoverflow.com/questions/25062969/testing-angularjs-with-selenium
  public static ExpectedCondition<Boolean> documentReadyCompleteAndjQueryCompleteAndAngularNotActive(final String angularElementSelector) {
    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        String script = "try {"
                + "  if (document.readyState !== 'complete') {"
                + "    return false; // Page not loaded yet"
                + "  }"
                + "  if (window.jQuery) {"
                + "    if (window.jQuery.active) {"
                + "      return false;"
                + "    } else if (window.jQuery.ajax && window.jQuery.ajax.active) {"
                + "      return false;"
                + "    }"
                + "  }"
                + "  if (window.angular) {"
                + "    if (!window.qa) {"
                + "      window.qa = {"
                + "        doneRendering: false"
                + "      };"
                + "    }"
                + "    var injector = window.angular.element('" + angularElementSelector + "').injector();"
                + "    // Store providers to use for these checks"
                + "    var $rootScope = injector.get('$rootScope');"
                + "    var $http = injector.get('$http');"
                + "    var $timeout = injector.get('$timeout');"                
                + "    if ($rootScope.$$phase === '$apply' || $rootScope.$$phase === '$digest' || $http.pendingRequests.length !== 0) {"
                + "      window.qa.doneRendering = false;"
                + "      return false; // Angular digesting or loading data"
                + "    }"
                + "    if (!window.qa.doneRendering) {"
                + "      $timeout(function() {"
                + "        window.qa.doneRendering = true;"
                + "      }, 0);"
                + "      return false;"
                + "    }"
                + "  }"
                + "  return true;"
                + "} catch (ex) {"
                + "  return false;"
                + "}";
        return (Boolean) ((JavascriptExecutor) driver).executeScript(script);
      }

    };

  }

}
