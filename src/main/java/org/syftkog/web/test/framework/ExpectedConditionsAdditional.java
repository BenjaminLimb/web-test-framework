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

// See: http://stackoverflow.com/questions/25062969/testing-angularjs-with-selenium
  public static Boolean isDocumentReadyCompleteAndjQueryCompleteAndAngularNotActive(WebDriver driver, String angularElementSelector) {
    //String angularElementSelector = "[data-ng-app]";

    String script = ""
            + "    try {\n"
            + "        if (document.readyState !== 'complete') {\n"
            + "            return false;\n"
            + "        }\n"
            + "        if (window.jQuery) {\n"
            + "            if (window.jQuery.active) {\n"
            + "                return false;\n"
            + "            }\n"
            + "            else if (window.jQuery.ajax && window.jQuery.ajax.active) {\n"
            + "                return false;\n"
            + "            }\n"
            + "        }\n"
            + "\n"
            + "        if (window.angular) {\n"
            + "            if (!window.qa) {\n"
            + "                window.qa = {\n"
            + "                    doneRendering: false\n"
            + "                };\n"
            + "            }\n"
            + "            var injector = window.angular.element('[data-ng-app]').injector();\n"
            + "\n"
            + "            var $rootScope = injector.get('$rootScope');\n"
            + "            var $http = injector.get('$http');\n"
            + "            var $timeout = injector.get('$timeout');\n"
            + "            if ($rootScope.$$phase === '$apply' || $rootScope.$$phase === '$digest' || $http.pendingRequests.length !== 0) {\n"
            + "                window.qa.doneRendering = false;\n"
            + "                return false;\n"
            + "            }\n"
            + "            if (!window.qa.doneRendering) {\n"
            + "                $timeout(function () {\n"
            + "                    window.qa.doneRendering = true;\n"
            + "                }, 0);\n"
            + "                return false;\n"
            + "            }\n"
            + "        }\n"
            + "        return true;\n"
            + "    }\n"
            + "    catch (ex) {\n"
            + "        return false;\n"
            + "    }\n"
            + "";
    Object result = ((JavascriptExecutor) driver).executeScript(script);

    return (Boolean) result;
  }

  //http://stackoverflow.com/questions/25062969/testing-angularjs-with-selenium
  public static ExpectedCondition<Boolean> documentReadyCompleteAndjQueryCompleteAndAngularNotActive(final String angularElementSelector) {
    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return isDocumentReadyCompleteAndjQueryCompleteAndAngularNotActive(driver, angularElementSelector);
      }

    };

  }

}
