package org.syftkog.web.test.framework;

import org.syftkog.web.test.framework.ExpectedConditionsAdditional;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author BenjaminLimb
 */
public class SeleniumHelperUtil {

    /**
     * convertToBy: is a method that follows the general guidlines of Selenium
     * Element Locators as defined at:
     * http://release.seleniumhq.org/selenium-remote-control/0.9.2/doc/dotnet/Selenium.ISelenium.html
     * We use this method to simplify the "find element by" commands:
     * waitForElementPresent("#ID .CLASS div a[ATTRIBUTE=\"something\"]) or
     * waitForElementPresent("//div/") or waitForElementPresent("id=happy") or
     * just about any other combination... see comments in-line.
     *
     * @param selectorString
     * @return the By
     */
    public static By convertToBy(String selectorString) {
        if (selectorString == null || selectorString.isEmpty()) {
            throw new InvalidSelectorException("Invalid parameter: " + selectorString);
        }

        String[] parts = selectorString.split("=");
        By findBy;

        if (parts.length > 1) {
            String getType = parts[0];
            String key = selectorString.substring(getType.length() + 1); //Separate the type and the key on the '=' character

            if (getType.equalsIgnoreCase("css")) { //Check to see if it is CSS
                findBy = By.cssSelector(key);  //"css=#something.somethingElse" should findBy By.cssSelector <id="something" class="somethingElse">
            } else if (getType.equalsIgnoreCase("identifier") || getType.equalsIgnoreCase("id")) { //Check to see if it is an ID
                findBy = By.id(key);  //"identifier=something" should findBy By.id <id="something">
            } else if (getType.equalsIgnoreCase("link")) { //Check to see if it is a LINK
                findBy = By.linkText(key);  //"link=something" should findBy By.linkText <a href="http://www.google.com/search?q=something">something</a>
            } else if (getType.equalsIgnoreCase("name")) { //Check to see if it is a NAME
                findBy = By.name(key);  //"name=something" should findBy By.name <id="dude" name="something">
            } else if (getType.equalsIgnoreCase("tag")) { //Check to see if it is a TAG
                findBy = By.tagName(key);  //"tag=a" should findBy By.tagName <a href="http://www.google.com/search?q=something">something</a>
            } else if (getType.equalsIgnoreCase("xpath")) { //Check to see if it is an XPATH
                findBy = By.xpath(key);  //"xpath=//div/article/div[4]/div[3]" should findBy By.xpath first div, first article in first div, 4 divs down inside article, 3 divs down inside the 4th div down
            } else {
                findBy = null; //If it is neither identified as CSS, an ID, a LINK, a NAME, a TAG, or an XPATH, then make it NULL
            }
        } else {
            findBy = null; //If it is empty, make it NULL
        }

        // if findBy is NULL, check to see if it is XPATH with a single "/" (more generic search), or check if it is an ID,
        // Class, Attribute, Tag, etc... and if not, assume it is CSS, try anyway... or run it as ID and fail.
        if (findBy == null) { //CHeck to see if findBy is a NULL, which means the string isn't identified, or it was empty.
            if (selectorString.startsWith("/")) { //Check to see if it is a default XPATH
                findBy = By.xpath(selectorString);  //If it starts with "//" or "/" it is an XPATH    (// = more specific, / = less specific)
            } else if (selectorString.startsWith("#") || selectorString.startsWith(".") || selectorString.startsWith("[")) { //Check to see if it is an ID (#), a CLASS (.) or an ATTRIBUTE ([)
                findBy = By.cssSelector(selectorString); //If any of these (CLASS, ID, ATTRIBUTE) then treat it like CSS
            } else if (!selectorString.isEmpty()) { //If it is NOT XPATH and NOT an ID, CLASS or ATTRIBUTE, then treat it like CSS, it may be a tag or other element
                findBy = By.cssSelector(selectorString);
            } else {  //FAILSAFE: if it is not any of the above, use this to fail the selection.
                findBy = By.id(selectorString);
            }
        }
        return findBy;
    }

  /**
   *
   * @param desiredCapabilities
   * @param availableCapabilities
   * @return
   */
  public static boolean capabilitiesOfDesiredMatchAvailable(Capabilities desiredCapabilities, Capabilities availableCapabilities) {

//          if(!desiredCapabilities.getBrowserName().equalsIgnoreCase(availableCapabilities.getBrowserName())){
//              return false;
//          }
//          if(!desiredCapabilities.getVersion().equalsIgnoreCase(availableCapabilities.getVersion())){
//              return false;
//          }
        // this check to for all capabilities , not just browser and version
        for (Map.Entry<String, ?> entry : desiredCapabilities.asMap().entrySet()) {
            String key = entry.getKey();
            Object value = (Object) entry.getValue();
            if (value != null && !value.toString().toLowerCase().equals("") && !value.toString().toLowerCase().equals("any")) {
                Object availableCapability = availableCapabilities.getCapability(key);
                if (availableCapability == null || !availableCapability.toString().equalsIgnoreCase(value.toString())) {
                    return false;
                }
            }
        }

        return true;
    }

  /**
   *
   * @param dimension
   * @return
   */
  public static Dimension toDimension(String dimension) {
        String[] sizes = dimension.toLowerCase().split("x");
        return new Dimension(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));
    }

  /**
   *
   * @param driver
   * @param urlPart
   * @param secondsToWait
   */
  public static void waitForUrlToContain(WebDriver driver, String urlPart, Integer secondsToWait) {
        Wait<WebDriver> wait = new WebDriverWait(driver, secondsToWait);
        wait.until(ExpectedConditionsAdditional.urlToContain(urlPart));
    }

  /**
   *
   * @param driver
   * @param secondsToWait
   */
  public static void waitForJQueryExists(WebDriver driver, Integer secondsToWait) {
        Wait<WebDriver> wait = new WebDriverWait(driver, secondsToWait);
        wait.until(ExpectedConditionsAdditional.jQueryExists());
    }

  /**
   *
   * @param driver
   * @param secondsToWait
   */
  public static void waitForJQueryNotActive(WebDriver driver, Integer secondsToWait) {
        Wait<WebDriver> wait = new WebDriverWait(driver, secondsToWait);
        wait.until(ExpectedConditionsAdditional.jQueryNotActive());
    }

  /**
   *
   * @param driver
   * @param secondsToWait
   */
  public static void waitForJQueryExistsAndNotActive(WebDriver driver, Integer secondsToWait) {
        Wait<WebDriver> wait = new WebDriverWait(driver, secondsToWait);
        wait.until(ExpectedConditionsAdditional.jQueryExists());
        wait.until(ExpectedConditionsAdditional.jQueryNotActive());
    }

    /**
     * It may take half a second for this action to complete.
     *
     * @param driver
     * @param el
     */
    public static void scrollIntoViewThenClick(WebDriver driver, WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
        //TODO: This may need a wait time for stability.
        el.click();
    }

  /**
   *
   * @param sc
   * @param by
   * @return
   */
  public static List<WebElement> findVisibleElements(SearchContext sc, By by) {
        List<WebElement> elements = sc.findElements(by);
        Iterator<WebElement> it = elements.iterator();
        while (it.hasNext()) {
            WebElement el = it.next();
            if (!el.isDisplayed()) {
                it.remove();
            }
        }
        return elements;
    }

}
