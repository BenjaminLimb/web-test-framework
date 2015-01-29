package org.syftkog.web.test.framework;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;

/**
 *
 * @author BenjaminLimb
 */
public class Driver implements WebDriver, JavascriptExecutor, WrapsDriver, HasInputDevices, TakesScreenshot, HasDriver, HasStepLogger, HasSearchContext, SauceOnDemandSessionIdProvider {

  /**
   *
   */
  public final Logger LOG = LoggerFactory.getLogger(Driver.class);
  private final ExecutorService executorService;
  private WebDriver driver;
  private final Capabilities driverCapabilities;

  private final Stack<Page> pageHistory;
  private final Stack<String> urlHistory; // Used for detecting page changes. Never removed from.

  private final HashMap<String, Object> properties;

  private Environment environment;

  private StepLogger stepLogger;
  private DriverFactory factory;

  private ITestResult testResult;

  //Lifecycle controls
  private long driverIdleTimeoutMilliseconds = Long.parseLong(PropertiesRetriever.getString("driver.idleTimeoutMilliseconds", "10")) * 1000;
  private Boolean keepAlive = true;
  private Boolean inUse = true;
  private Boolean dirty = false;
  private long lastInUse = System.currentTimeMillis();

  //END lifecycle control    
  private long implicitWaitTimeInSeconds = Long.parseLong(PropertiesRetriever.getString("driver.implicitWaitTimeInSeconds", "10"));

  /**
   *
   * @param driver
   * @param driverCapabilities
   * @param executorService
   * @param factory
   */
  public Driver(WebDriver driver, Capabilities driverCapabilities, ExecutorService executorService, DriverFactory factory) {
    this.urlHistory = new Stack<>();
    this.pageHistory = new Stack<>();
    this.driver = driver;
    this.stepLogger = new StepLogger();
    this.driverCapabilities = driverCapabilities;
    this.executorService = GeneralUtils.replaceNull(executorService, Executors.newSingleThreadExecutor());
    this.executorService.execute(new KeepAliveMonitor());
    this.factory = factory;
    this.properties = new HashMap<>();
  }

  /**
   *
   * @param driver
   * @param driverCapabilities
   */
  public Driver(WebDriver driver, Capabilities driverCapabilities) {
    this(driver, driverCapabilities, null, null);
  }

  /**
   *
   * @param driver
   * @param driverCapabilities
   * @param executorService
   */
  public Driver(WebDriver driver, Capabilities driverCapabilities, ExecutorService executorService) {
    this(driver, driverCapabilities, executorService, null);
  }

  /**
   *
   * @return
   */
  @Override
  public WebDriver getWrappedDriver() {
    return driver;
  }

  /**
   *
   * @param text
   */
  public void logStep(String text) {
    stepLogger.log(text);
  }

  /**
   *
   * @return
   */
  public Capabilities getCapabilities() {
    return driverCapabilities;
  }

  /**
   *
   * @return
   */
  public Page getPreviousPage() {
    return pageHistory.lastElement();
  }

  /**
   *
   * @param page
   * @return
   */
  public Driver addPageToDriverHistory(Page page) {
    pageHistory.push(page);
    return this;
  }

  /**
   *
   * @param url
   */
  @Override
  public void get(String url) {
    urlHistory.add(url);
    driver.get(url);
  }

  /**
   *
   * @return
   */
  public Page navigateBack() {
    logStep("Navigate BACK using browser.");
    driver.navigate().back();

    if (!pageHistory.empty()) {
      pageHistory.pop();
      if (!pageHistory.empty()) {
        Page lastPage = pageHistory.peek();
        return lastPage;
      } else {
        LOG.debug("Cannot return previous page if it is not already stored in the driver. Did you click and navigate to a new page without pushing it to the page history?");
        return null;
      }
    }
    return null;
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
  public Driver prepareToNavigateToNewUrl() {
    urlHistory.push(getCurrentUrl());
    return this;
  }

  /**
   *
   * @return
   */
  public Driver waitForNavigateToNewUrl() {
    return waitUntilUrlChange(urlHistory.peek(), implicitWaitTimeInSeconds);
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

  private synchronized void terminate() {
    Assert.assertTrue(inUse == false);
    if (driver != null) {
      try {
        driver.quit();
        if (factory != null) {
          factory.reportThatDriverQuitSuccessfully(this);
        }
        driver = null; // set the driver null so we don't try to use it anymore.
      } catch (UnsupportedCommandException ex) {
        LOG.error(ex.toString());
      }
    }

  }

  // Closes a single browser window.
  /**
   *
   */
  @Override
  public void close() {
    driver.close();
  }

  // Immediately quit the driver;
  /**
   *
   */
  @Override
  public void quit() {
    inUse = false;
    keepAlive = false;
    terminate();
  }

  /**
   *
   */
  public void recycle() {
    Assert.assertNotNull(factory, "Recycle should only be used if the driver factory is set!");
    inUse = false;
    if (factory != null) {
      factory.returnDriver(this);
    } else {
      keepAlive = false;
    }
  }

  /**
   *
   * @return
   */
  public Driver setInUse() {
    Assert.assertFalse(inUse, "A driver shouldn't be set in use twice without being recycled first.");
    inUse = true;
    return this;
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
  public WebDriver.TargetLocator switchTo() {
    return driver.switchTo();
  }

  /**
   *
   * @return
   */
  @Override
  public WebDriver.Navigation navigate() {
    return driver.navigate();
  }

  /**
   *
   * @return
   */
  @Override
  public WebDriver.Options manage() {
    return driver.manage();
  }

  /**
   *
   * @param script
   * @param args
   * @return
   */
  @Override
  public Object executeScript(String script, Object... args) {
    return ((JavascriptExecutor) driver).executeScript(script, args);
  }

  /**
   *
   * @param script
   * @param args
   * @return
   */
  @Override
  public Object executeAsyncScript(String script, Object... args) {
    return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
  }

  /**
   *
   * @return
   */
  @Override
  public Keyboard getKeyboard() {
    return ((HasInputDevices) driver).getKeyboard();
  }

  /**
   *
   * @return
   */
  @Override
  public Mouse getMouse() {
    return ((HasInputDevices) driver).getMouse();
  }

  /**
   *
   * @param oldUrl
   * @param timeoutInSeconds
   * @return
   */
  public Driver waitUntilUrlChange(String oldUrl, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(this, timeoutInSeconds);
    wait.until(ExpectedConditionsAdditional.urlChange(oldUrl));
    return this;
  }

  /**
   *
   * @return
   */
  public long getImplicitWaitTimeInSeconds() {
    return implicitWaitTimeInSeconds;
  }

  /**
   *
   * @param implicitWaitTimeInSeconds
   */
  public void setImplicitWaitTimeInSeconds(long implicitWaitTimeInSeconds) {
    this.implicitWaitTimeInSeconds = implicitWaitTimeInSeconds;
  }

  /**
   *
   * @return
   */
  public Boolean isAlive() {
    return keepAlive;
  }

  /**
   *
   * @return
   */
  public Boolean isInUse() {
    return inUse;
  }

  /**
   *
   * @return
   */
  public long getDriverIdleTimeoutMilliseconds() {
    return driverIdleTimeoutMilliseconds;
  }

  /**
   *
   * @param driverIdleTimeoutMilliseconds
   */
  public void setDriverIdleTimeoutMilliseconds(long driverIdleTimeoutMilliseconds) {
    this.driverIdleTimeoutMilliseconds = driverIdleTimeoutMilliseconds;
  }

  /**
   *
   * @param <X>
   * @param ot
   * @return
   * @throws WebDriverException
   */
  @Override
  public <X> X getScreenshotAs(OutputType<X> ot) throws WebDriverException {
    return (X) driver;
  }

  // Allow the driver to be reused.
  /**
   *
   * @return
   */
  public Driver clean() {
    dirty = false;
    return this;
  }

  /**
   *
   * @return
   */
  public Driver dirty() {
    dirty = true;
    return this;
  }

  /**
   *
   * @param hasContext
   * @return
   */
  public Driver applyTestCaseContext(WrapsTestCaseContext hasContext) {
    TestCaseContext testCaseContext = hasContext.getWrappedTestCastContext();
    stepLogger = testCaseContext.getStepLogger();
    environment = testCaseContext.parameters().getEnvironment();
    Dimension windowSize = testCaseContext.parameters().getWindowSize();
    if (windowSize != null) {
      manage().window().setSize(windowSize);
    }
    return this;
  }

  /**
   *
   * @return
   */
  @Override
  public Driver getDriver() {
    return this;
  }

  /**
   *
   * @return
   */
  @Override
  public SearchContext getSearchContext() {
    return this;
  }

  /**
   *
   * @return
   */
  @Override
  public StepLogger getStepLogger() {
    return stepLogger;
  }

  /**
   *
   * @param stepLogger
   */
  public void setStepLogger(StepLogger stepLogger) {
    this.stepLogger = stepLogger;
  }

  /**
   *
   * @return
   */
  @Override
  public String getSessionId() {
    if (driver instanceof RemoteWebDriver) {
      return ((RemoteWebDriver) driver).getSessionId().toString();
    }
    return null;
  }

  private class KeepAliveMonitor implements Runnable {

    @Override
    public void run() {
      while (keepAlive) {
        long elapsedMilli = System.currentTimeMillis() - lastInUse;

        if (inUse) {
          lastInUse = System.currentTimeMillis();
        } else if (dirty) {
          LOG.debug("DRIVER IS DIRTY SO IT WILL NOT BE REUSED.");
          keepAlive = false;
        } else if (elapsedMilli > getDriverIdleTimeoutMilliseconds()) {
          if (factory != null && !factory.tryRemoveAvailableDriver(Driver.this)) {
            continue;
          }
          LOG.debug("DRIVER TIMEOUT REACHED: " + driverIdleTimeoutMilliseconds / 1000.0 + " seconds " + driver.toString());
          keepAlive = false;
          break;
        }

        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          LOG.error("InterruptedException", ex);
        }
      }
      terminate();
    }

  };

  /**
   *
   * @param miliseconds
   */
  public static void sleepForMilliseconds(long miliseconds) {
    try {
      Thread.sleep(miliseconds);
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   *
   * @param miliseconds
   */
  public static void sleepForMilliseconds(int miliseconds) {
    try {
      Thread.sleep(miliseconds);
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   *
   * @return
   */
  public ITestResult getTestResult() {
    return testResult;
  }

  /**
   *
   * @param testResult
   */
  public void setTestResult(ITestResult testResult) {
    this.testResult = testResult;
  }

  /**
   *
   * @param environment
   */
  public void goToEnvironment(Environment environment) {
    if (!driver.getCurrentUrl().contains(environment.getHost())) {
      driver.navigate().to(environment.getURL());
    }
  }

  /**
   *
   * @param c
   */
  public void setCookie(Cookie c) {
    dirty();// If we do any cookie manipulation then we are going to prevent the driver from reuse unless it's cleaned up.

    driver.manage().addCookie(c);

    Cookie cookieToRead = driver.manage().getCookieNamed(c.getName());
    Assert.assertNotNull(cookieToRead);
    Assert.assertTrue(cookieToRead.getValue().equalsIgnoreCase(c.getValue()));
  }

  /**
   *
   * @param name
   */
  public void deleteCookie(String name) {
    driver.manage().deleteCookieNamed(name);
    driver.navigate().refresh();
  }

  /**
   *
   * @return
   */
  public Environment getEnvironment() {
    return environment;
  }

  /**
   *
   * @param environment
   */
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }
}
