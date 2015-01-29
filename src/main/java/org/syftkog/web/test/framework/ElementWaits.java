package org.syftkog.web.test.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public class ElementWaits<T extends Element> {

  private final T el;

  /**
   *
   * @param el
   */
  public ElementWaits(T el) {
    this.el = el;
  }

  /**
   *
   * @param <E>
   * @param el
   * @return
   */
  public static <E extends Element> ElementWaits until(E el) {
    return new ElementWaits<>(el);
  }

  /**
   *
   * @param timeoutInSeconds
   * @return
   */
  public T isDisplayed(long timeoutInSeconds) {
    el.logAction("WAIT UNTIL ELEMENT IS DISPLAYED");

    WebDriverWait wait = new WebDriverWait(el.getDriver(), timeoutInSeconds);
    wait.until(new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return (el.isDisplayed());
      }
    });
    return el;
  }

  /**
   *
   * @param timeoutInSeconds
   * @return
   */
  public T isNotDisplayed(long timeoutInSeconds) {
    el.logAction("WAIT UNTIL ELEMENT IS NOT DISPLAYED");
    WebDriverWait wait = new WebDriverWait(el.getDriver(), timeoutInSeconds);
    wait.until(new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return (!el.isDisplayed());
      }
    });
    return el;
  }

  /**
   *
   * @param timeoutInSeconds
   * @return
   */
  public T isSelected(long timeoutInSeconds) {
    el.logAction("WAIT UNTIL SELECTED");

    WebDriverWait wait = new WebDriverWait(el.getDriver(), timeoutInSeconds);
    wait.until(new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return (el.isSelected());
      }
    });
    return el;
  }

  /**
   *
   * @param timeoutInSeconds
   * @return
   */
  public T isNotSelected(long timeoutInSeconds) {
    el.logAction("WAIT UNTIL NOT SELECTED");
    WebDriverWait wait = new WebDriverWait(el.getDriver(), timeoutInSeconds);
    wait.until(new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return (!el.isSelected());
      }
    });
    return el;
  }

  /**
   *
   * @param text
   * @param timeoutInSeconds
   * @return
   */
  public T isEqualTo(String text, long timeoutInSeconds) {
    el.logAction("WAIT UNTIL ELEMENT TEXT IS EQUAL TO:" + text);
    WebDriverWait wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ElementConditions.textEqualsIgnoreCase(el, text));
    return el;
  }

  /**
   *
   * @param text
   * @param timeoutInSeconds
   * @return
   */
  public T isNotEqualTo(String text, long timeoutInSeconds) {
    el.logAction("WAIT UNTIL ELEMENT TEXT IS NOT EQUAL TO:" + text);
    WebDriverWait wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ElementConditions.textEqualsIgnoreCase(el, text));
    return el;
  }

  /**
   *
   * @param text
   * @param timeoutInSeconds
   * @return
   */
  public T containsText(String text, long timeoutInSeconds) {
    el.logAction("WAIT UNTIL CONTAINS TEXT");
    WebDriverWait wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ElementConditions.textContains(el, text));
    return el;
  }

  /**
   *
   * @param text
   * @param timeoutInSeconds
   * @return
   */
  public T doesNotContainsText(String text, long timeoutInSeconds) {
    el.logAction("WAIT UNTIL DOES NOT CONTAIN TEXT");
    WebDriverWait wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ElementConditions.textDoesNotContain(el, text));
    return el;
  }

  /**
   *
   * @param timeoutInSeconds
   * @return
   */
  public T isEnabled(long timeoutInSeconds) {
    el.logAction("WAIT UNTIL ELEMENT IS ENABLED");
    WebDriverWait wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ElementConditions.isEnabled(el));
    return el;
  }

  /**
   *
   * @param timeoutInSeconds
   * @return
   */
  public T isNotEnabled(long timeoutInSeconds) {
    el.logAction("WAIT UNTIL ELEMENT IS NOT ENABLED");
    WebDriverWait wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ElementConditions.isNotEnabled(el));
    return el;
  }

  /**
   *
   * @param number
   * @param timeoutInSeconds
   * @return
   */
  public T isGreaterThan(Long number, long timeoutInSeconds) {
    el.logAction("WAIT UNTIL ELEMENT TEXT AS NUMBER IS GREATER THAN [" + number+"].");
    WebDriverWait wait;
    wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ElementConditions.isGreaterThan(el,number));
    return el;
  }

  /**
   *
   * @param number
   * @param timeoutInSeconds
   * @return
   */
  public T isLessThan(Long number, long timeoutInSeconds) {
    el.logAction("WAIT UNTIL ELEMENT TEXT AS NUMBER IS LESS THAN [" + number+"].");
    WebDriverWait wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ElementConditions.isLessThan(el,number));
    return el;
  }

  /**
   *
   * @param oldUrl
   * @param timeoutInSeconds
   * @return
   */
  public T urlChange(String oldUrl, long timeoutInSeconds) {
    el.logAction("WAIT UNTIL URL CHANGE");
    WebDriverWait wait = new WebDriverWait(new DriverSearchContextAdapter(el.getDriver(), el.getParentSearchContext()), timeoutInSeconds);
    wait.until(ExpectedConditionsAdditional.urlChange(oldUrl));
    return el;
  }

  /**
   * An expectation for checking that an element is either invisible or not
   * present on the DOM.
   *
   * @return
   */
  private ExpectedCondition<Boolean> isNotDisplayed() {
    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return !(el.isDisplayed());
      }
    };

  }

}
