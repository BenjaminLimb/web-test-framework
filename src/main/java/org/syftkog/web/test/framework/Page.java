package org.syftkog.web.test.framework;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import static org.syftkog.web.test.framework.StepLogger.TRACE;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public class Page<T extends Page> implements HasDriver, HasSearchContext {

  /**
   *
   */
  public static final Logger LOG = LoggerFactory.getLogger(Page.class);

    private final Driver driver;
    private final SearchContext searchContext;

    private final String url;
    private final PageAssertions<T> assertThatPage = new PageAssertions<>((T) this);
    private final PageWaits<T> waitUntil = new PageWaits<>((T) this);

  /**
   *
   * @return
   */
  public PageAssertions<T> assertPage() {
        return assertThatPage;
    }

  /**
   *
   * @return
   */
  public PageWaits<T> waitUntil() {
        return waitUntil;
    }

  /**
   *
   * @return
   */
  public T waitUntilLoaded() {
        return waitUntil().loaded();
    }

  /**
   *
   * @return
   */
  public Boolean isLoadConditionMet() {
        return true;
    }

    /**
     *
     * @param hasDriver
     * @param url
     *
     */
    public Page(HasDriver hasDriver, String url) {
        this.driver = hasDriver.getDriver();
        this.searchContext = hasDriver.getDriver();
        this.url = url;

    }

    /**
     *
     * @param hasDriver
     * @param hasSearchContext
     * @param url
     *
     */
    public Page(HasDriver hasDriver, HasSearchContext hasSearchContext, String url) {
        this.driver = hasDriver.getDriver();
        this.searchContext = hasSearchContext.getSearchContext();
        this.url = url;

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
  public T addPageToDriverHistory() {
        getDriver().addPageToDriverHistory(this);
        return (T) this;
    }

  /**
   *
   * @return
   */
  public T load() {
        navigateTo();
        waitUntil.loaded();
        return (T) this;
    }

  /**
   *
   * @return
   */
  public T navigateTo() {
        getDriver().logStep("GOTO: \"" + url + "\"");
        getDriver().navigate().to(url);
        getDriver().addPageToDriverHistory(this);
        return (T) this;
    }

  /**
   *
   * @return
   */
  public T prepareToNavigateToNewUrl() {
        getDriver().prepareToNavigateToNewUrl();
        return (T) this;
    }

  /**
   *
   * @return
   */
  public T waitForNavigateToNewUrl() {
        getDriver().waitForNavigateToNewUrl();
        return (T) this;
    }

  /**
   *
   * @return
   */
  public String getUrl() {
        return url;
    }

  /**
   *
   * @return
   */
  public T refreshPage() {
        getDriver().navigate().refresh();
        waitUntil.loaded();
        return (T) this;
    }

  /**
   *
   * @return
   */
  public Boolean isDocumentReadyState() {
        return executeJavascript(("return document.readyState")).equals("complete");
    }

  /**
   *
   * @return
   */
  public Boolean isCorrectPage() {
        try {
            URL currentURL = new URL(getDriver().getCurrentUrl());
            String actualPath = currentURL.getPath();
            String expectedPath = new URL(url).getPath();
            getDriver().getStepLogger().log(TRACE, "IsCorrectPage returning " + actualPath.contains(expectedPath) + " ActualPath:" + actualPath + " ExpectedPath:" + expectedPath);
            return actualPath.contains(expectedPath);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

  /**
   *
   * @return
   */
  public Boolean isJQueryInactive() {
        return (Long) executeJavascript("return jQuery.active") == 0;
    }

  /**
   *
   * @return
   */
  public Long jQueryActiveCount() {
        return (Long) executeJavascript("return jQuery.active");
    }

  /**
   *
   * @return
   */
  public Boolean isLoaded() {
        Assert.assertNotNull(url, "isLoaded cannot be called without having a URL to check against.");
        return isCorrectPage() && isLoadConditionMet();
    }

  /**
   *
   * @param script
   * @return
   */
  public Object executeJavascript(String script) {
        getDriver().getStepLogger().log(TRACE, "Execute Javascript: " + script);
        Object ob = getDriver().executeScript(script);
        getDriver().getStepLogger().log(TRACE, "Javascript returned:" + ((ob == null) ? "null" : ob.toString()));
        return ob;
    }

    /**
     * Returns the number of active jQuery AJAX requests.
     *
     * @see
     * http://hustoknow.blogspot.com/2010/10/how-jqueryactive-code-works.html
     * @return number of active jQuery AJAX requests
     */
    public Integer getJQueryPendingAJAXRequests() {
        //TODO: wrap this in a try catch and return null if jquery is not defined.
        int active = Integer.parseInt(executeJavascript("return jQuery.active").toString());

        return active;
    }

    /**
     * takeScreenShot takes a screen shot of the current browser and saves it as
     * a png.
     *
     * @param fileNamePNG
     */
    public void takeScreenShot(String fileNamePNG) {
        LOG.info("Saved screenshot to file " + fileNamePNG);
        File scrFile = getDriver().getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(fileNamePNG));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

  /**
   *
   * @return
   */
  @Override
    public SearchContext getSearchContext() {
        return searchContext;
    }
    
    

}
