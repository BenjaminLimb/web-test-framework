package org.syftkog.web.test.framework;

import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This adapter allows you to override a WebDriver with a different
 * SearchContext.
 *
 * @author BenjaminLimb
 */
public class DriverSearchContextAdapter implements WebDriver {

    WebDriver driver;
    SearchContext context;

  /**
   *
   * @param driver
   * @param context
   */
  public DriverSearchContextAdapter(WebDriver driver, SearchContext context) {
        this.driver = driver;
        this.context = context;
    }

  /**
   *
   * @param url
   */
  @Override
    public void get(String url) {
        driver.get(url);
    }

  /**
   *
   * @return
   */
  @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

  /**
   *
   * @return
   */
  @Override
    public String getTitle() {
        return driver.getTitle();
    }

  /**
   *
   * @param by
   * @return
   */
  @Override
    public List<WebElement> findElements(By by) {
        return context.findElements(by);
    }

  /**
   *
   * @param by
   * @return
   */
  @Override
    public WebElement findElement(By by) {
        return context.findElement(by);
    }

  /**
   *
   * @return
   */
  @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

  /**
   *
   */
  @Override
    public void close() {
        driver.close();
    }

  /**
   *
   */
  @Override
    public void quit() {
        driver.quit();
    }

  /**
   *
   * @return
   */
  @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

  /**
   *
   * @return
   */
  @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

  /**
   *
   * @return
   */
  @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

  /**
   *
   * @return
   */
  @Override
    public Navigation navigate() {
        return driver.navigate();
    }

  /**
   *
   * @return
   */
  @Override
    public Options manage() {
        return driver.manage();
    }

}
