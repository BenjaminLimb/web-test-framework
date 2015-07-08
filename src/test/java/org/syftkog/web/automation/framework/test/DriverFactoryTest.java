package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.DesiredCapabilitiesFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.syftkog.web.test.framework.PropertiesRetriever;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class DriverFactoryTest {

  DriverFactory factory = new DriverFactory();

  /**
   *
   */
  @Test(groups = "unit")
  public void testGetDriver() {
    WebDriver driver = factory.getDriver();
    Assert.assertNotNull(driver);
    driver.quit();
  }

  @Test(groups = "local")
  public void testGetDriverChrome() {
    DesiredCapabilities caps = DesiredCapabilitiesFactory.chrome();    
    WebDriver driver = factory.getDriver(caps);
    Assert.assertNotNull(driver);
    driver.quit();
  }
  
  @Test(groups = "local")
  public void testGetPhantomJS() {
//    
//    DesiredCapabilities caps = new DesiredCapabilities();
//          caps.setJavascriptEnabled(true); //if you don't set this to true you will probably be sorry
////          caps.setCapability("phantomjs.page.settings.userAgent", agent); //This is for the purpose of spoofing other agents in order for some sites to works or to test IE and mobile actions coded in the web page
//            caps.setCapability("phantomjs.binary.path",PHANTOM_JS_DRIVER_PATH); //Path to directory on Electric Commander
////          caps.setCapability("handlesAlerts", true); //Currently does not support alert boxes (Will pass OK or accept, via JavaScript, to every alert that occurs, if set to true)
//          WebDriver driver = new PhantomJSDriver(caps); //will be treated as any other driver from here on out
////          driver.manage().window().setSize(new Dimension(320, 480)); //Set your size window if you desire (i.e. for mobile size testing and other)
//
//    
    
//    
    DesiredCapabilities caps = DesiredCapabilitiesFactory.phantomJS();
//    PhantomJSDriver phantomJSDriver = new PhantomJSDriver(caps);
//    phantomJSDriver.navigate().to("http://google.com");
//    
    WebDriver driver = factory.getDriver(caps);
    Assert.assertNotNull(driver);
    driver.quit();
  }

  /**
   *
   */
  @Test(groups = "unit")
  public void testGetDriverWithCapabilitites() {
    DesiredCapabilities caps = DesiredCapabilitiesFactory.htmlunit();

    WebDriver driver = factory.getDriver(caps);
    Assert.assertNotNull(driver);
    driver.quit();
  }

}
