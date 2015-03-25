package org.syftkog.web.test.framework;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import static org.syftkog.web.test.framework.StepLogger.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidCookieDomainException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnableToSetCookieException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * @author BenjaminLimb
 * @param <T>
 */
public class Element<T extends Element> implements Locatable, WrapsElement, SearchContext, Coordinates, HasDriver, HasSearchContext {

  /**
   *
   */
  public final Logger LOG = LoggerFactory.getLogger(Element.class);

  /**
   *
   */
  public static final long IMPLICIT_WAIT_TIME_IN_SECONDS = Long.parseLong(PropertiesRetriever.getString("element.implicitWaitTimeInSeconds", "10"));

  /**
   *
   */
  public static final long ELEMENT_RETRY_INTERVAL_MILLISECONDS = Long.parseLong(PropertiesRetriever.getString("element.actionRetryIntervalInMilliseconds", "100"));

  private final String elementName;
  private final String elementSelector;
  private final Driver driver;
  private final SearchContext searchContext;
  private WebElement element;
  private WebElementFinder elementFinder;

  private final ElementAssertions<T> assertThatElement = new ElementAssertions<>((T) this);
  private final ElementWaits<T> waitUntil = new ElementWaits<>((T) this);

  /**
   *
   * @return
   */
  public ElementAssertions assertElement() {
    return assertThatElement;
  }

  /**
   *
   * @return
   */
  public ElementWaits waitUntil() {
    return waitUntil;
  }

  /**
   *
   * @param hasDriver
   * @param name
   * @param selector
   */
  public Element(HasDriver hasDriver, String name, String selector) {
    this.driver = hasDriver.getDriver();
    this.searchContext = hasDriver.getDriver();
    this.elementName = name;
    this.elementSelector = selector;
  }

  /**
   *
   * @param hasDriver
   * @param hasSearchContext
   * @param name
   * @param selector
   */
  public Element(HasDriver hasDriver, HasSearchContext hasSearchContext, String name, String selector) {
    this.driver = hasDriver.getDriver();
    this.searchContext = hasSearchContext.getSearchContext();
    this.elementName = name;
    this.elementSelector = selector;
  }

  /**
   *
   * @return
   */
  @Override
  public WebElement getWrappedElement() {
    return element;
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
    return this;
  }

  /**
   *
   * @return
   */
  protected By getBy() {
    Assert.assertNotNull(elementSelector, "Element selector is null.");
    return SeleniumHelperUtil.convertToBy(elementSelector);
  }

  /**
   *
   * @param element
   * @return
   */
  public Element initializeTo(WebElement element) {
    this.element = element;
    return this;
  }

  private WebElement findWebElement() {
    if (elementSelector == null && elementFinder != null) {
      return elementFinder.find();
    }

    Assert.assertNotNull(elementSelector, "Cannot find an element if the selector is null and no elementFinder has been specified.");
    return searchContext.findElement(getBy());
  }

  private void initializeElementIfNull() {
    if (element == null) {
      initializeElement();
    }
  }

  private void initializeElement() {
    this.element = findWebElement();
    Assert.assertNotNull(element);
  }

  /**
   *
   * @return
   */
  public String getName() {
    return elementName;
  }

  /**
   *
   * @return
   */
  public String getType() {
    return this.getClass().getSimpleName();
  }

  /**
   *
   * @return
   */
  public Element prepareToNavigateToNewUrl() {
    getDriver().prepareToNavigateToNewUrl();
    return this;
  }

  /**
   *
   * @return
   */
  public Element waitForNavigateToNewUrl() {
    logAction("WAIT FOR NAVIGATE TO NEW URL");
    getDriver().waitForNavigateToNewUrl();
    return this;
  }

  /**
   *
   * @param keysToSend
   * @return
   */
  public Element sendKeys(final CharSequence keysToSend) {
    StringBuilder b = new StringBuilder(keysToSend);

    logActionWithParameter("TYPE", "\"" + displayKeys(b.toString() + "\""));

    new RetryUntilTimeout<Object>() {
      @Override
      Object commandsToRun() {
        element.sendKeys(keysToSend);
        return null;
      }
    }.run();

    return this;
  }

  /**
   *
   * @return
   */
  protected String elementStates() {
    if (element != null) {
      Boolean enabled = element.isEnabled();
      Boolean displayed = element.isDisplayed();
      Boolean selected = element.isSelected();

      return "\r\n     " + "Element Name:\"" + this.elementName + "\""
              + "\r\n     " + "Element Type: " + getType()
              + "\r\n     " + "Selector:\"" + this.elementSelector + "\""
              + "\r\n     " + "Enabled:" + enabled
              + "\r\n     " + "Displayed:" + displayed
              + "\r\n     " + "Selected:" + selected;
    } else {
      return "Element was not initialized.";
    }
  }

  /**
   *
   * @param text
   * @return
   */
  protected String displayKeys(String text) {
    if (text == null) {
      return null;
    }
    text = text.replace("\r\n", "[ENTER]");
    text = text.replace("\t", "[TAB]");
    return text;
  }

  /**
   *
   * @param text
   */
  protected void logStep(String text) {
    getDriver().logStep(text);
  }

  /**
   *
   * @param action
   */
  protected void logAction(String action) {
    logStep(action + " " + toString());
  }

  /**
   *
   * @param action
   * @param parameter
   */
  protected void logActionWithParameter(String action, String parameter) {
    logStep(action + " " + displayKeys(parameter) + " in " + toString());
  }

  /**
   *
   * @param action
   * @param ex
   */
  protected void logActionFailed(String action, Exception ex) {
    logStep(action + " failed. " + ex.getMessage() + toString());
  }

  /**
   *
   * @param action
   * @param ex
   */
  protected void logActionFailedWillRetry(String action, Exception ex) {
    logStep(action + " failed. " + ex.getMessage() + " Will Retry:" + toString());
  }

  // add handling to scroll when needed.
//     WebDriverHelperUtil.scrollIntoViewThenClick(driver, link);

  /**
   *
   * @return
   */
    public Element click() {
    this.logAction("CLICK");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
//                int elementPosition = element.getLocation().getY();
//                String js = String.format("window.scroll(0, %s)", elementPosition);
//                ((JavascriptExecutor) driver).executeScript(js);
        try {
          Thread.sleep(500);
        } catch (InterruptedException ex) {
          java.util.logging.Logger.getLogger(Element.class.getName()).log(Level.SEVERE, null, ex);
        }
        element.click();
        return true;
      }
    }.run();

    return this;
  }

  /**
   *
   * @return
   */
  public Element hover() {
    this.logAction("HOVER OVER");

    new RetryUntilTimeout<Object>() {
      @Override
      Object commandsToRun() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element);
        actions.perform();
        return null;
      }
    }.run();

    return this;
  }

  //comment about this function

  /**
   *
   * @return
   */
    public Element pressShift() {
    this.logAction("SHIFT CLICK");

    new RetryUntilTimeout<Object>() {
      @Override
      Object commandsToRun() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element);
        actions.keyDown(Keys.SHIFT);
        actions.perform();
        return null;
      }
    }.run();

    return this;
  }

  /**
   *
   * @return
   */
  public Long getNumberAsLong() {
    String text = getText();

    if (text == null) {
      return null;
    }
    text = text.replace(",", ""); // remove commas TODO: this may break in internationization 

    try {
      return Long.parseLong(text);
    } catch (NumberFormatException ex) {
      getDriver().getStepLogger().log(TRACE, "ParseException: Could not parse \"" + text + "\" into Long ");
      throw new RuntimeException("\"ParseException: Could not parse \\\"\" + text+\"\\\" into Number \"");
    }
  }

  /**
   *
   * @return
   */
  public String getTagName() {
    return new RetryUntilTimeout<String>() {
      @Override
      String commandsToRun() {
        return element.getTagName();
      }
    }.run();

  }

  /**
   *
   * @return
   */
  public Point getLocation() {
    return new RetryUntilTimeout<Point>() {
      @Override
      Point commandsToRun() {
        return element.getLocation();
      }
    }.run();
  }

  /**
   *
   * @return
   */
  public Dimension getSize() {
    return new RetryUntilTimeout<Dimension>() {
      @Override
      Dimension commandsToRun() {
        return element.getSize();
      }
    }.run();
  }

  /**
   *
   * @param propertyName
   * @return
   */
  public String getCssValue(final String propertyName) {
    return new RetryUntilTimeout<String>() {
      @Override
      String commandsToRun() {
        return element.getCssValue(propertyName);
      }
    }.run();
  }

  /**
   *
   */
  public void submit() {
    logAction("SUBMIT");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        element.submit();
        return true;
      }
    }.run();
  }

  /**
   *
   * @param attributeKey
   * @return
   */
  public String getAttribute(final String attributeKey) {
    return new RetryUntilTimeout<String>() {
      @Override
      String commandsToRun() {
        return element.getAttribute(attributeKey);
      }
    }.run();
  }

  /**
   *
   * @return
   */
  public Element clear() {
    this.logAction("CLEAR");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        element.clear();
        return true;
      }
    }.run();

    return this;
  }

  /**
   *
   * @return
   */
  public String getText() {
    return new RetryUntilTimeout<String>() {
      @Override
      String commandsToRun() {
        String text = element.getText();
        if (text == null || text.equals("")) {
          text = element.getAttribute("value");
        }
        return text;

      }
    }.run();
  }

  /**
   *
   * @param text
   * @return
   */
  public Element selectByValue(final String text) {

    driver.logStep("SELECT ELEMENT BY VALUE value='" + text + "' : \"" + toString() + ") ");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        Select select = new Select(element);
        select.selectByValue(text);
        return true;
      }
    }.run();
    return this;
  }

  /**
   *
   * @param text
   * @return
   */
  public Element selectByVisibleText(final String text) {
    driver.logStep("SELECT ELEMENT BY TEXT [" + text + "' : \"" + toString() + " (" + this.elementSelector + ")");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        Select select = new Select(element);
        select.selectByVisibleText(text);
        return true;
      }
    }.run();
    return this;

  }

  /**
   *
   * @return
   */
  public String getSelectboxValue() {    
    return new RetryUntilTimeout<String>() {
      @Override
      String commandsToRun() {
        Select select = new Select(element);
        String value = select.getFirstSelectedOption().getText();
        return value;
      }
    }.run();
  }

  /**
   * Sometimes we want to fail immediately, such as when we are waiting for an
   * element to be invisible.For this reason it it not wrapped in the
   * webDriverRetry.
   *
   * @param by
   * @return
   */
  @Override
  public List<WebElement> findElements(final By by) {
    try {
      initializeElementIfNull();
      return element.findElements(by);
    } catch (StaleElementReferenceException ex) {
      getDriver().getStepLogger().log(WARN, toString() + "\n" + ex.toString());
      initializeElement();
      return element.findElements(by);
    }

  }

  /**
   * Sometimes we want to fail immediately, such as when we are waiting for an
   * element to be invisible.
   *
   * @param by
   * @return
   */
  @Override
  public WebElement findElement(final By by) {
    try {
      initializeElementIfNull();
      return element.findElement(by);
    } catch (StaleElementReferenceException ex) {
      getDriver().getStepLogger().log(WARN, toString() + "\n" + ex.toString());
      initializeElement();
      return element.findElement(by);
    }
  }

  /**
   *
   * @param where
   * @return
   */
  public Element mouseMove(final Coordinates where) {
    this.logAction("MOUSE MOVE");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        getDriver().getMouse().mouseMove(where);
        return true;
      }
    }.run();
    return this;
  }

  /**
   *
   * @param where
   * @param xOffset
   * @param yOffset
   * @return
   */
  public Element mouseMove(final Coordinates where, final long xOffset, final long yOffset) {
    this.logAction("MOUSE MOVE");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        getDriver().getMouse().mouseMove(where, xOffset, yOffset);
        return true;
      }
    }.run();
    return this;
  }

  /**
   *
   * @param relativePoint
   * @return
   */
  public Element click(Point relativePoint) {
    return click(relativePoint.getX(), relativePoint.getY());
  }

  /**
   *
   * @param xOffset
   * @param yOffset
   * @return
   */
  public Element click(final long xOffset, final long yOffset) {
    this.logAction("MOUSE CLICK at relative position: (" + xOffset + "," + yOffset + ")");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        getDriver().getMouse().mouseMove(getCoordinates(), xOffset, yOffset);
        getDriver().getMouse().mouseDown(null);
        getDriver().getMouse().mouseUp(null);

        return true;
      }
    }.run();
    return this;
  }

  /**
   *
   * @return
   */
  public Element mouseDown() {
    this.logAction("MOUSE DOWN");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        getDriver().getMouse().mouseDown(getCoordinates());
        return true;
      }
    }.run();
    return this;
  }

  /**
   *
   * @return
   */
  public Element mouseUp() {
    this.logAction("MOUSE UP");
    new RetryUntilTimeout<Boolean>() {
      @Override
      Boolean commandsToRun() {
        getDriver().getMouse().mouseUp(null);
        return true;
      }
    }.run();
    return this;
  }

  /**
   *
   * @return
   */
  @Override
  public Coordinates getCoordinates() {
    return new RetryUntilTimeout<Coordinates>() {
      @Override
      Coordinates commandsToRun() {
        return ((Locatable) element).getCoordinates();
      }
    }.run();
  }

  /**
   *
   * @return
   */
  @Override
  public Point onScreen() {
    return getCoordinates().onScreen();
  }

  /**
   *
   * @return
   */
  @Override
  public Point inViewPort() {
    return getCoordinates().inViewPort();
  }

  /**
   *
   * @return
   */
  @Override
  public Point onPage() {
    return getCoordinates().onPage();
  }

  /**
   *
   * @return
   */
  @Override
  public Object getAuxiliary() {
    return getCoordinates().getAuxiliary();
  }

  /**
   *
   * @return
   */
  public String getElementName() {
    return elementName;
  }

  /**
   *
   * @return
   */
  public String getElementSelector() {
    return elementSelector;

  }

  /**
   *
   * @param <T>
   */
  protected abstract class RetryUntilTimeout<T> {

    /**
     *
     * @return
     */
    public T run() {
      getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
      long timeout = System.currentTimeMillis() + IMPLICIT_WAIT_TIME_IN_SECONDS * 1000;

      while (System.currentTimeMillis() < timeout) {
        try {
          initializeElementIfNull();
          return commandsToRun();
        } catch (StaleElementReferenceException ex) {
          getDriver().getStepLogger().log(WARN, "StaleElementReferenceException - Refresh element to increase performance. " + parentToString());          
          element = null; // Since it's stale, we clear it and let it start the loop over immediately        
        } catch (InvalidElementStateException | InvalidCookieDomainException | NotFoundException  | UnableToSetCookieException ex) {
          getDriver().getStepLogger().log(WARN, parentToString() + "\n" + ex.toString());
          element = null;
          Driver.sleepForMilliseconds(ELEMENT_RETRY_INTERVAL_MILLISECONDS);
        } catch (WebDriverException ex) {
          if (ex.getMessage().contains("Element is not currently interactable and may not be manipulated")) {
            Driver.sleepForMilliseconds(ELEMENT_RETRY_INTERVAL_MILLISECONDS);
          } else if (ex.getMessage().contains("Element is not clickable at point")) { // needed because chrome driver doesn't throw ElementNotVisibleException when something is positioned off of the screen
            Driver.sleepForMilliseconds(ELEMENT_RETRY_INTERVAL_MILLISECONDS);
          } else {
            throw ex;
          }
        }
      }

      //Last Try before timeout
      try {
        initializeElementIfNull();
        return commandsToRun();
      } catch (WebDriverException ex) {
        throw new TimeoutException(ex);
      }
    }

    abstract T commandsToRun();
  }

  /**
   *
   * @return
   */
  public WebElementFinder getElementFinder() {
    return elementFinder;
  }

  /**
   *
   * @param elementFinder
   * @return
   */
  public Element setElementFinder(WebElementFinder elementFinder) {
    this.elementFinder = elementFinder;
    return this;
  }

  /**
   *
   * @return
   */
  public SearchContext getParentSearchContext() {
    return searchContext;
  }

  /**
   *
   * @return - is the element in the dom?
   */
  public Boolean isLoaded() {
    getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    try {
      initializeElement();
      return element != null;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  /**
   *
   * @return
   */
  public Boolean isDisplayed() {
    getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    try {
      initializeElement();
      return element.isDisplayed();
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  /**
   *
   * @return
   */
  public Boolean isNotDisplayed() {
    return !isDisplayed();
  }

  /**
   *
   * @return
   */
  public Boolean isSelected() {
    getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    try {
      initializeElement();
      return element.isSelected();
    } catch (NoSuchElementException ex) {
      return false;
    }

  }

  /**
   *
   * @return
   */
  public Boolean isEnabled() {
    getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    try {
      initializeElement();
      return element.isEnabled();
    } catch (NoSuchElementException ex) {
      return false;
    }

  }

  /**
   *
   * @return
   */
  public Boolean isNotEnabled() {
    return !isEnabled();
  }

  /**
   *
   * @param <P>
   * @param clazz
   * @param env
   * @return
   */
  public <P extends Page> P fixUrlAndGotoLink(final Class<P> clazz, final Environment env) {
    this.logAction("LOAD PAGE " + clazz);

    return new RetryUntilTimeout<P>() {
      @Override
      P commandsToRun() {
        String href = getAttribute("href");
        try {
          Assert.assertNotNull(env);
          String newhref = env.fixHost(href);
          driver.navigate().to(newhref);
          return PageFactory.createPage(getDriver(), clazz);
        } catch (MalformedURLException ex) {
          getDriver().getStepLogger().log(WARN, "INVALID HREF ON LINK:" + href);
          throw new InvalidElementStateException("INVALID HREF ON LINK:" + href);
        }
      }
    }.run();
  }

  /**
   *
   * @param <P>
   * @param clazz
   * @return
   */
  public <P extends Page> P clickAndLoadPage(Class<P> clazz) {
    click();
    this.logAction("LOAD PAGE " + clazz);
    P page = PageFactory.createPage(getDriver(), clazz);
    page.waitUntil().correctPage();
    page.waitUntilLoaded();
    return page;
  }

  /**
   *
   * @param page
   * @return
   */
  public Page clickAndLoadPage(Page page) {
    click();
    logStep("WAIT FOR " + page.getUrl());
    getDriver().addPageToDriverHistory(page);
    page.waitUntil().correctPage();
    page.waitUntilLoaded();
    return page;
  }

  /**
   *
   * @return
   */
  public String parentToString() {
    return toString();
  }

  @Override
  public String toString() {
    return "Element:\"" + this.elementName + "\" Type:\"" + this.getType() + "\" Selector:\"" + this.elementSelector + "\"";
  }
}
