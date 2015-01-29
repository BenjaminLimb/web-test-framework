package org.syftkog.web.test.framework;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import static org.syftkog.web.test.framework.StepLogger.WARN;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public class ElementList<T extends Element> extends AbstractSequentialList<T> {

  /**
   *
   */
  public final Logger LOG = LoggerFactory.getLogger(ElementList.class);

  /**
   *
   */
  public static final long IMPLICIT_WAIT_TIME_IN_SECONDS = Long.parseLong(PropertiesRetriever.getString("element.implicitWaitTimeInSeconds", "10"));
    private List<T> elementList = null;
    private List<WebElement> webElementList = null;
    private final ElementFactory<T> factory;

    private final String listSelector;
    private final String patternSelector;

    private final String baseName;

    private final HasDriver hasDriver;
    private final Driver driver;
    private final HasSearchContext hasSearchContext;
    private final SearchContext searchContext;

  /**
   *
   * @param factory
   * @param hasDriver
   * @param hasSearchContext
   * @param baseName
   * @param listSelector
   * @param patternSelector
   */
  public ElementList(ElementFactory<T> factory, HasDriver hasDriver, HasSearchContext hasSearchContext, String baseName, String listSelector, String patternSelector) {
        this.hasDriver = hasDriver;
        this.driver = hasDriver.getDriver();
        this.hasSearchContext = hasSearchContext;
        this.searchContext = hasSearchContext.getSearchContext();
        this.factory = factory;
        this.baseName = baseName;

        this.listSelector = listSelector;
        this.patternSelector = patternSelector;
    }

    private ElementList initialize() {
        if (elementList == null) {
            elementList = findAndCreateChildren();
        }
        Assert.assertNotNull(elementList);
        return this;
    }

  /**
   * TODO: This is a hack.. It would be better for the object to just deal with stale elements.      
   * @return
   */
  public ElementList reInitialize() {
        this.elementList = findAndCreateChildren();
        return this;
    }

    private List<T> findAndCreateChildren() {
        webElementList = searchContext.findElements(By.cssSelector(listSelector));

        List<T> children = new ArrayList<>();
        for (int i = 0; i < webElementList.size(); i++) {
            String selector = patternSelector;
            if (selector != null) {
                selector = selector.replace("###", String.valueOf(i + 1));
            }
            
            // Create a way for the element to find itself again in the list if it becomes stale such as in the case of a page refresh.
            final int elementIndex = i;
            WebElementFinder finder = new WebElementFinder() {
                @Override
                public WebElement find() {
                    return getWebElement(elementIndex);
                }
            };

            T child = factory.make(hasDriver, hasSearchContext, baseName, selector, webElementList.get(i), finder);

            children.add(child);
        }
        return children;
    }

    @Override
    public int size() {
        reInitialize();
        return elementList.size();
    }

    private WebElement getWebElement(final int index) {
        return new webDriverRetryUntilTimeout<WebElement>() {
            @Override
            WebElement commandsToRun() {
                if (index >= webElementList.size()) {
                    throw new NoSuchElementException("('" + listSelector + "[" + index + "]')");
                } else {                  
                    return webElementList.get(index);
                }
            }
        }.run();
    }

    @Override
    public T get(final int index) {
        return new webDriverRetryUntilTimeout<T>() {
            @Override
            T commandsToRun() {
                if (index >= elementList.size()) {
                    throw new NoSuchElementException("('" + listSelector + "[" + index + "]')");
                } else {
                    
                  T gElement = elementList.get(index);
                  try{
                    gElement.isDisplayed();
                  }catch(Exception ex){
                    System.out.println();
                  }
                    return gElement;
                }
            }
        }.run();
    }

  /**
   *
   * @return
   */
  public T chooseRandom() {
        return chooseRandom(1).get(0);
    }

  /**
   *
   * @param count
   * @return
   */
  public List<T> chooseRandom(final int count) {
        get(count - 1); // make sure all are present.
        if (count > size()) {
            throw new NoSuchElementException("('" + listSelector + " contains " + elementList.size() + " however you asked elements up to index [" + count + "]')");
        } else {
            List<T> randomList = new ArrayList<T>();
            randomList.addAll(elementList);
            Collections.shuffle(randomList);
            return randomList.subList(0, count);
        }

    }

  /**
   *
   * @param text
   * @return
   */
  public T getByContainsText(final String text) {
        Assert.assertNotNull(text, "text cannot be null.");
        driver.logStep("FIND BY TEXT" + " : \"" + text + "\"");
        return new webDriverRetryUntilTimeout<T>() {
            @Override
            T commandsToRun() {
                for (T el : elementList) {
                    String elText = el.getText();
                    if (elText != null && elText.contains(text)) {
                      
                      
                      
                      
                      
                      
                        return el;
                    }
                }
                elementList = null; // reset the children if not found.
                throw new NoSuchElementException("d" + listSelector + " with text \"" + text + "\"");
            }
        }.run();

    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new webDriverRetryUntilTimeout<ListIterator<T>>() {
            @Override
            ListIterator<T> commandsToRun() {
                return elementList.listIterator();
            }
        }.run();
    }

  /**
   *
   * @return
   */
  public SearchContext getSearchContext() {
        return searchContext;
    }

  /**
   *
   * @param <T>
   */
  protected abstract class webDriverRetryUntilTimeout<T> {

    /**
     *
     * @return
     */
    public T run() {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            long timeout = System.currentTimeMillis() + IMPLICIT_WAIT_TIME_IN_SECONDS * 1000;

            while (System.currentTimeMillis() < timeout) {
                try {
                    initialize();
                    return commandsToRun();
                } catch (NotFoundException ex) {
                    driver.getStepLogger().log(WARN, parentToString() + "\n" + ex.toString());
                    elementList = null; // Reset the list on failure to find.
                    Driver.sleepForMilliseconds(100);
                } catch (WebDriverException ex) {
                    driver.getStepLogger().log(WARN, parentToString() + "\n" + ex.toString());
                    elementList = null;
                    Driver.sleepForMilliseconds(100);
                }
            }

            //Last Try before timeout and rethrow
            try {
                initialize();
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
  public String parentToString() {
        return toString();
    }

    @Override
    public String toString() {
        return "ElementList:\"" + this.baseName + "\" ListSelector:\"" + this.listSelector + "\" PatternSelector:\"" + this.patternSelector + "\"";
    }

}
