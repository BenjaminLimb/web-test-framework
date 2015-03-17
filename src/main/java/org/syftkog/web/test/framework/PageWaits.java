package org.syftkog.web.test.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public class PageWaits<T extends Page> {

  /**
   *
   */
  public Integer maxWaitForPageLoadSeconds = Integer.parseInt(PropertiesRetriever.getString("maxWaitForPageLoadSeconds", "31"));

  /**
   *
   */
  public Integer maxWaitForUrlSeconds = Integer.parseInt(PropertiesRetriever.getString("maxWaitForUrlSeconds", "10"));

  /**
   *
   */
  public Integer maxJQueryWaitSeconds = Integer.parseInt(PropertiesRetriever.getString("maxJQueryWaitSeconds", "10"));

  private final T page;

  /**
   *
   * @param page
   */
  public PageWaits(T page) {
    this.page = page;
  }

  /**
   *
   * @param condition
   * @return
   */
  public T condition(ExpectedCondition condition) {
    page.getDriver().logStep("WAIT FOR CONDITION " + condition.toString());
    Wait<WebDriver> wait = new WebDriverWait(page.getDriver(), maxWaitForPageLoadSeconds);
    wait.until(condition);
    return page;
  }

  /**
   *
   * @return
   */
  public T loaded() {
    page.getDriver().logStep("WAIT FOR PAGE TO LOAD: \"" + page.getUrl() + "\"");
    Wait<WebDriver> wait = new WebDriverWait(page.getDriver(), maxWaitForPageLoadSeconds);
    wait.until(new ExpectedCondition<Boolean>() {

      @Override
      public Boolean apply(WebDriver driver) {
        return page.isLoaded();
      }

    });
    return page;
  }

  /**
   *
   * @param <P>
   * @param page
   * @return
   */
  public static <P extends Page> PageWaits until(P page) {
    return new PageWaits<>(page);
  }

  /**
   *
   * @return
   */
  public T correctPage() {
    page.getDriver().logStep("WAIT FOR CORRECT PAGE : \"" + page.getUrl() + "\"");

    Wait<WebDriver> wait = new WebDriverWait(page.getDriver(), maxWaitForUrlSeconds);
    wait.until(new ExpectedCondition<Boolean>() {

      @Override
      public Boolean apply(WebDriver f) {
        return page.isCorrectPage();
      }

    });
    return page;
  }

  /**
   *
   * @return
   */
  public T jQueryToExist() {
    page.getDriver().logStep("WAIT FOR JQUERY TO EXIST : \"" + page.getUrl() + "\"");
    Wait<WebDriver> wait = new WebDriverWait(page.getDriver(), maxJQueryWaitSeconds);
    wait.until(ExpectedConditionsAdditional.jQueryExists());
    return page;
  }

  /**
   *
   * @return
   */
  public T jQueryNotActive() {
    page.getDriver().logStep("WAIT FOR JQUERY NOT ACTIVE : \"" + page.getUrl() + "\"");
    Wait<WebDriver> wait = new WebDriverWait(page.getDriver(), maxJQueryWaitSeconds);
    wait.until(ExpectedConditionsAdditional.jQueryNotActive());
    return page;
  }

  /**
   *
   * @return
   */
  public T jQueryToExistAndNotActive() {
    page.getDriver().logStep("WAIT FOR JQUERY TO EXIST AND NOT BE ACTIVE: \"" + page.getUrl() + "\"");
    Wait<WebDriver> wait = new WebDriverWait(page.getDriver(), maxJQueryWaitSeconds);
    wait.until(ExpectedConditionsAdditional.jQueryExists());
    wait.until(ExpectedConditionsAdditional.jQueryNotActive());
    return page;
  }

  /**
   *
   * @return
   */
  public Integer getMaxWaitForPageLoadSeconds() {
    return maxWaitForPageLoadSeconds;
  }

  /**
   *
   * @param maxWaitForPageLoadSeconds
   */
  public void setMaxWaitForPageLoadSeconds(Integer maxWaitForPageLoadSeconds) {
    this.maxWaitForPageLoadSeconds = maxWaitForPageLoadSeconds;
  }

  /**
   *
   * @return
   */
  public Integer getMaxWaitForUrlSeconds() {
    return maxWaitForUrlSeconds;
  }

  /**
   *
   * @param maxWaitForUrlSeconds
   */
  public void setMaxWaitForUrlSeconds(Integer maxWaitForUrlSeconds) {
    this.maxWaitForUrlSeconds = maxWaitForUrlSeconds;
  }

  /**
   *
   * @return
   */
  public Integer getMaxJQueryWaitSeconds() {
    return maxJQueryWaitSeconds;
  }

  /**
   *
   * @param maxJQueryWaitSeconds
   */
  public void setMaxJQueryWaitSeconds(Integer maxJQueryWaitSeconds) {
    this.maxJQueryWaitSeconds = maxJQueryWaitSeconds;
  }

}
