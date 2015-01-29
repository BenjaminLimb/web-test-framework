package org.syftkog.web.test.framework;

import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import static org.mockito.Mockito.*;
import org.slf4j.Logger;
import org.testng.Assert;

/**
 *
 * @author BenjaminLimb
 */
public class MockWebDriver implements WebDriver {

    WebDriver driver = mock(WebDriver.class);

    Boolean quit = false;
    Logger LOG = LoggerFactory.getLogger(MockWebDriver.class);

  /**
   *
   * @param string
   */
  @Override
    public void get(String string) {
        System.out.println(this + " get(" + string + ")");
        driver.get(string);
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
        return driver.findElements(by);
    }

  /**
   *
   * @param by
   * @return
   */
  @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
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
        Assert.assertFalse(quit, "Driver has already beeen quit");
        driver.quit();
        quit = true;
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
        Navigation nav = mock(Navigation.class);
        when(driver.navigate()).thenReturn(nav);
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
