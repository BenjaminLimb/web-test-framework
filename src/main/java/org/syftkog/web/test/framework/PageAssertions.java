package org.syftkog.web.test.framework;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public class PageAssertions<T extends Page> {

  private final T page;

  /**
   *
   * @param page
   */
  public PageAssertions(T page) {
    this.page = page;
  }

  /**
   *
   * @param <P>
   * @param page
   * @return
   */
  public static <P extends Page> PageAssertions that(P page) {
    return new PageAssertions<>(page);
  }

  /**
   *
   * @return
   */
  public T correctURL() {
    page.getDriver().logStep("ASSERT: Correct URL");

    Assert.assertTrue(page.isCorrectPage());
    return page;
  }

  /**
   *
   * @return
   */
  public T loaded() {
    page.getDriver().logStep("ASSERT: Page Loaded, AJAX calls are complete.");

    Assert.assertTrue(page.isCorrectPage());
    Assert.assertTrue(page.isLoadConditionMet());

    containsNoErrorMessages();
    return page;
  }

  /**
   * TODO: This should live in a page object.
   *
   * @return
   */
  public T containsNoErrorMessages() {
    // Look for error messages
    List<WebElement> els = page.getDriver().findElements(By.cssSelector(".search_error"));
    if (els.size() > 0) {
      for (WebElement el : els) {
        if (el.isDisplayed()) {
          Assert.fail(page.getDriver().getCurrentUrl() + " contained error message. Look for the css search_error class");
        }
      }
    }
    return page;
  }

  /**
   *
   * @return
   */
  public T containsOnlyValidLinks() {
    List<WebElement> links = page.getDriver().findElements(By.cssSelector("a"));
    for (WebElement el : links) {
      if (el.isDisplayed()) {
        String href = el.getAttribute("href");
        if (!href.contains("javascript")) {
          String response = Integer.toString(GeneralUtils.getResponseCode(href));
        }
      }
    }
    return page;
  }

}
