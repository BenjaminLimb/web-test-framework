package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.TestCaseParameters;
import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import org.syftkog.web.test.framework.MockWebDriver;
import org.syftkog.web.automation.framework.test.pages.GoogleHomePage;
import org.syftkog.web.automation.framework.test.pages.TwitterHomePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.syftkog.web.test.framework.PropertiesRetriever;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class DriverTest {

  /**
   *
   */
  @Test(groups = "unit")
  public void testBasicInit() {
    WebDriver mockDriver = new MockWebDriver();
    TestCaseParameters params = new TestCaseParameters().setBrowserVersionPlatform(BrowserVersionPlatform.MOCK);
    DesiredCapabilities capabilities = params.toDesiredCapabilities();
    Driver reusableDriver = new Driver(mockDriver, capabilities);
  }
  
  @Test(groups = "local")
  public void testFFLocal() {
    WebDriver driver = new FirefoxDriver();
    driver.navigate().to("https://google.com");
  }
  
 @Test(groups = "local")
  public void testChromeLocal() {
    ChromeOptions ops = new ChromeOptions();    
    String pathToBinary = PropertiesRetriever.getString("webdriver.chrome.driver", null);   
    System.setProperty("webdriver.chrome.driver",pathToBinary);
    
    WebDriver driver = new ChromeDriver(ops);
    driver.navigate().to("https://google.com");
  }

  /**
   *
   */
  @Test(groups = "local")
  public void testBasicInitFFandQuit() {
    WebDriver driver = new FirefoxDriver();
    TestCaseParameters params = new TestCaseParameters().setBrowserVersionPlatform(BrowserVersionPlatform.WIN7FF);
    DesiredCapabilities capabilities = params.toDesiredCapabilities();

    //ExecutorService executorService = Executors.newFixedThreadPool(1);
    Driver reusableDriver = new Driver(driver, capabilities);
    reusableDriver.quit();

//        executorService.shutdown();
//        try {
//            executorService.awaitTermination(60, TimeUnit.SECONDS);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
  }

  /**
   *
   */
  @Test(groups = "unit")
  public void testNavigateBack() {
    Driver driver = new DriverFactory().getDriver(BrowserVersionPlatform.WIN7FF);
    
    GoogleHomePage googleHomePage = new GoogleHomePage(driver);
    googleHomePage.load();
    
    TwitterHomePage twitterHomePage = new TwitterHomePage(driver);
    twitterHomePage.load();
    
    driver.navigateBack();
    
    googleHomePage.assertPage().loaded();
    
    driver.quit();
    
  }

  /**
   *
   */
  @Test(groups = "unit")
  public void testNavigationDetection() {
    Driver driver = new DriverFactory().getDriver(BrowserVersionPlatform.WIN7FF);
    
    driver.navigate().to("https://google.com");
    driver.prepareToNavigateToNewUrl();
    
    driver.setImplicitWaitTimeInSeconds(1);

    // Test that the navigate to new url times out correctly.
    try {
      driver.waitForNavigateToNewUrl();
      Assert.fail();
    } catch (TimeoutException ex) {
    }
    
    driver.setImplicitWaitTimeInSeconds(4);
    driver.navigate().to("https://twitter.com");
    
    driver.waitForNavigateToNewUrl();
    
    driver.quit();
  }
  
}
