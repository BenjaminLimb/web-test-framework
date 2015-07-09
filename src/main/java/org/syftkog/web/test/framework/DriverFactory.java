package org.syftkog.web.test.framework;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * TODO: Consider using the rest API. https://saucelabs.com/docs/rest#resources
 *
 * @author BenjaminLimb
 */
public class DriverFactory implements HasDriver {

  /**
   *
   */
  public static final Logger LOG = LoggerFactory.getLogger(DriverFactory.class);

  /**
   *
   */
  public static enum DriverRunEnvironment {

    /**
     *
     */
    LOCAL,
    /**
     *
     */
    SAUCE,
    /**
     *
     */
    GRID,
    /**
     *
     */
    UNSPECIFIED
  };

  // Singleton Setup
  private static class DriverFactoryInit {

    public static final DriverFactory instance = new DriverFactory();
  }

  /**
   *
   * @return
   */
  public static DriverFactory getInstance() {
    return DriverFactoryInit.instance;
  }

  private final static String IE_DRIVER_PATH = PropertiesRetriever.getString("webdriver.ie.driver", null);
  private final static String PHANTOM_JS_DRIVER_PATH = PropertiesRetriever.getString("webdriver.phantomjs.path", null);
  private final static String CHROME_DRIVER_PATH = PropertiesRetriever.getString("webdriver.chrome.driver", null);
  private final static String CHROME_PROFILE_PATH = PropertiesRetriever.getString("webdriver.chrome.profile.path", null);

  //SAUCE LABS CONFIGURATION
  //https://docs.saucelabs.com/reference/test-configuration/#maximum-test-duration
  private final static Boolean RECORD_VIDEO = PropertiesRetriever.getBoolean("saucelabs.recordVideo", false);
  private final static Boolean VIDEO_UPLOAD_ON_PASS = PropertiesRetriever.getBoolean("saucelabs.videoUploadOnPass", false);
  private final static Integer SAUCE_MAX_DURATION = PropertiesRetriever.getInteger("saucelabs.maxDuration", 1800);
  private final static Integer SAUCE_COMMAND_TIMEOUT = PropertiesRetriever.getInteger("saucelabs.commandTimeout", 300);
  private final static Integer SAUCE_IDLE_TIMEOUT = PropertiesRetriever.getInteger("saucelabs.idleTimeout", 90);
  private final static String SAUCE_USERNAME = PropertiesRetriever.getString("saucelabs.username", null);
  private final static String SAUCE_ACCESS_KEY = PropertiesRetriever.getString("saucelabs.accessKey", null);

  private final static String GRID_URL = PropertiesRetriever.getString("driverfactory.grid.url", "http://localhost:4444/wd/hub");

  private final static Integer DEFAULT_MAX_DRIVER_COUNT = PropertiesRetriever.getInteger("driverfactory.maxDriverCount", 10);
  private final static long RETRY_GET_DRIVER_SLEEP_TIME_IN_MILLISECONDS = Long.parseLong(PropertiesRetriever.getString("driverfactory.retySleepTime", "1000"));
  private final DriverRunEnvironment driverEnvironment = DriverRunEnvironment.valueOf(PropertiesRetriever.getString("driverfactory.environment", "LOCAL"));
  private Boolean recylceDrivers = PropertiesRetriever.getBoolean("driverfactory.recycle", false);

  /**
   *
   * @return
   */
  public Boolean isRecylceDrivers() {
    return recylceDrivers;
  }

  /**
   *
   * @param recylceDrivers
   * @return
   */
  public DriverFactory setRecylceDrivers(Boolean recylceDrivers) {
    this.recylceDrivers = recylceDrivers;
    return this;
  }

  private final ConcurrentHashMap<Integer, Driver> instantiatedDrivers;
  private final ConcurrentHashMap<Integer, Driver> availableDrivers;
  private final Semaphore availableDriverAccess = new Semaphore(1);
  private final ExecutorService driverExecutorService;
  private final Semaphore driverTotalCountLimiter;

  /**
   *
   * @param maxDriverCount
   */
  public DriverFactory(int maxDriverCount) {
    this.driverTotalCountLimiter = new Semaphore(maxDriverCount);
    this.driverExecutorService = Executors.newCachedThreadPool();
    this.availableDrivers = new ConcurrentHashMap<>();
    this.instantiatedDrivers = new ConcurrentHashMap<>();

  }

  /**
   *
   */
  public DriverFactory() {
    this(DEFAULT_MAX_DRIVER_COUNT);
  }

  /**
   *
   * @return
   */
  public int getInstantiatedDriverCount() {
    return instantiatedDrivers.size();
  }

  /**
   *
   * @return
   */
  public int getIdleDriverCount() {
    return availableDrivers.size();
  }

  private Driver getNewLocalDriver(Capabilities capabilities) {
    String browser = capabilities.getBrowserName();

    WebDriver driver = null;
    if (browser.equalsIgnoreCase("firefox")) {
      driver = new FirefoxDriver();
    } else if (browser.equalsIgnoreCase("internet explorer") && IE_DRIVER_PATH != null) {
      driver = new InternetExplorerDriver();
    } else if (browser.equalsIgnoreCase("chrome") && CHROME_DRIVER_PATH != null) {

      ChromeOptions options = new ChromeOptions();
      //options.setBinary(CHROME_DRIVER_PATH); // THIS DOESN'T WORK
      System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
      options.addArguments("--start-maximized");

      if (CHROME_PROFILE_PATH != null) {
        //TODO: Fix this -  wasn't working as desired 
        String userProfile = CHROME_PROFILE_PATH;
        options.addArguments("user-data-dir=" + userProfile);
        // END TODO
      }

      driver = new ChromeDriver(options);

    } else if (browser.equalsIgnoreCase("htmlunit")) {
      driver = new HtmlUnitDriver(true);
    } else if (browser.equalsIgnoreCase("phantomjs")) {
      DesiredCapabilities phantomCapabilites = new DesiredCapabilities();     
      phantomCapabilites.setJavascriptEnabled(true); 
      phantomCapabilites.setCapability("phantomjs.binary.path", PHANTOM_JS_DRIVER_PATH);
      driver = new PhantomJSDriver(phantomCapabilites);
    } else if (browser.equalsIgnoreCase("mock")) {
      driver = new MockWebDriver();
    } else {
      driver = new FirefoxDriver();
    }
    return new Driver(driver, capabilities, driverExecutorService, this);
  }

  private Driver getNewSauceDriver(Capabilities desiredCapabilities) {
    if (desiredCapabilities.getBrowserName().equalsIgnoreCase("mock")) {
      return new Driver(new MockWebDriver(), desiredCapabilities, driverExecutorService, this);
    }

//    Date date = new Date();
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:ss.S");
//    String formattedDate = sdf.format(date);
//    
    DesiredCapabilities sauceSupportedCapabilities = new DesiredCapabilities();

    sauceSupportedCapabilities.setCapability("name", desiredCapabilities.getCapability("name"));

    sauceSupportedCapabilities.setCapability("platform", desiredCapabilities.getCapability("platform"));
    sauceSupportedCapabilities.setCapability("browserName", desiredCapabilities.getCapability("browserName"));

    if (desiredCapabilities.getCapability("tags") != null) {
      ArrayList<String> tags = (ArrayList<String>) desiredCapabilities.getCapability("tags");
      sauceSupportedCapabilities.setCapability("tags", tags);
    }

    sauceSupportedCapabilities.setCapability("build", TestRunEnvironment.getRunId());

    Object version = desiredCapabilities.getCapability("version");
    if (version != null && !version.toString().equals("") && !version.toString().equalsIgnoreCase("any")) {
      sauceSupportedCapabilities.setCapability("version", version);
    }

    Object screenResultion = desiredCapabilities.getCapability("screen-resolution");
    if (screenResultion != null && !screenResultion.toString().equals("") && !screenResultion.toString().equalsIgnoreCase("any")) {
      sauceSupportedCapabilities.setCapability("screen-resolution", screenResultion);
    }

    //sauceSupportedCapabilities.setCapability("record-video", recordVideo);
    //sauceSupportedCapabilities.setCapability("video-upload-on-pass", videoUploadOnPass);
//        sauceSupportedCapabilities.setCapability("max-duration",sauceMaxDuration);
//        sauceSupportedCapabilities.setCapability("command-timeout",sauceCommandTimeout);
//        sauceSupportedCapabilities.setCapability("idle-timeout",sauceIdleTimeout);
    String urlString = "http://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

    try {
      URL url = new URL(urlString);
      WebDriver remoteWebDriver = new SauceLabsWebDriver(url, sauceSupportedCapabilities);
      return new Driver(remoteWebDriver, desiredCapabilities, driverExecutorService, this);
    } catch (MalformedURLException ex) {
      throw new RuntimeException("Invalid Sauce URL" + urlString);
    }
  }

  //TODO  NOT YET TESTED
  private Driver getNewGridDriver(Capabilities desiredCapabilities) {
    DesiredCapabilities cap = new DesiredCapabilities();
    cap.setBrowserName("firefox");

    try {
      URL url = new URL(GRID_URL);
      WebDriver remoteWebDriver = new RemoteWebDriver(url, cap);
      return new Driver(remoteWebDriver, desiredCapabilities, driverExecutorService, this);
    } catch (MalformedURLException ex) {
      throw new RuntimeException("Invalid GRID URL" + GRID_URL);
    }

  }

  /**
   *
   * @param driver
   */
  public void returnDriver(Driver driver) {
    if (recylceDrivers) {
      availableDrivers.put(driver.hashCode(), driver);
    } else {
      driver.quit();
    }

  }

  /**
   *
   * @param driver
   * @return
   */
  public Boolean tryRemoveAvailableDriver(Driver driver) {
    availableDriverAccess.acquireUninterruptibly();
    Driver driverToRemove = availableDrivers.get(driver.hashCode());
    availableDriverAccess.release();
    return driverToRemove != null;
  }

  /**
   *
   * @param driver
   */
  public void reportThatDriverQuitSuccessfully(Driver driver) {
    instantiatedDrivers.remove(driver.hashCode());
    driverTotalCountLimiter.release();
  }

  /**
   *
   * @return
   */
  public ExecutorService getDriverExecutorService() {
    return driverExecutorService;
  }

  /**
   * Iterate through the available drivers and look for one that matches the
   * desired capabilities.
   *
   * @param desiredCapabilities
   * @return
   */
  private Driver getDriverFromQueueIfAvailable(Capabilities desiredCapabilities) {
    availableDriverAccess.acquireUninterruptibly();

    for (Entry<Integer, Driver> entry : availableDrivers.entrySet()) {
      Driver driver = entry.getValue();
      Capabilities capabilities = driver.getCapabilities();
      if (SeleniumHelperUtil.capabilitiesOfDesiredMatchAvailable(desiredCapabilities, capabilities)) {
        availableDrivers.remove(driver.hashCode());
        driver.setInUse();
        availableDriverAccess.release();
        return driver;
      }
    }

    availableDriverAccess.release();

    return null;
  }

  private Driver tryGetNewDriver(Capabilities desiredCapabilities) {

    if (!driverTotalCountLimiter.tryAcquire()) { // Limit the number of drivers
      return null;
    }

    String desiredEnvironment = (String) desiredCapabilities.getCapability("desiredEnvironment");
    DriverRunEnvironment environment = null;

    if (desiredEnvironment != null) {
      environment = DriverRunEnvironment.valueOf(desiredEnvironment);
    }

    if (environment == null || environment == DriverRunEnvironment.UNSPECIFIED) {
      environment = driverEnvironment;
    }

    Driver aDriver = null;

    switch (environment) {
      case LOCAL:
        aDriver = getNewLocalDriver(desiredCapabilities);
        break;
      case SAUCE:
        aDriver = getNewSauceDriver(desiredCapabilities);
        break;
      case GRID:
        aDriver = getNewGridDriver(desiredCapabilities);
        break;
      default:
        aDriver = getNewLocalDriver(desiredCapabilities);
        break;
    }

    instantiatedDrivers.put(aDriver.hashCode(), aDriver);

    return aDriver;
  }

  /**
   *
   * @return
   */
  @Override
  public Driver getDriver() {
    Driver driver = getDriver(BrowserVersionPlatform.WIN7FF);
    return driver;
  }

  /**
   *
   * @param bpv
   * @return
   */
  public Driver getDriver(BrowserVersionPlatform bpv) {
    return getDriver(bpv.toDesiredCapabilities());
  }

  /**
   *
   * @param carrier
   * @return
   */
  public Driver getDriver(WrapsTestCaseContext carrier) {
    TestCaseContext context = carrier.getWrappedTestCastContext();
    Driver driver = getDriver(context.toDesiredCapabilities());
    driver.applyTestCaseContext(carrier);
    if (driver.getWrappedDriver() instanceof SauceLabsWebDriver) {
      SauceLabsWebDriver d = (SauceLabsWebDriver) driver.getWrappedDriver();
      d.updateName(context.getName());
    }

    return driver;
  }

  /**
   *
   * @param desiredCapabilities
   * @return
   */
  public Driver getDriver(Capabilities desiredCapabilities) {
    Driver driver = null;

    while (driver == null) {
      driver = getDriverFromQueueIfAvailable(desiredCapabilities);
      if (driver == null) {
        driver = tryGetNewDriver(desiredCapabilities);
      }
      if (driver == null) {
        try {
          Thread.sleep(RETRY_GET_DRIVER_SLEEP_TIME_IN_MILLISECONDS);
        } catch (InterruptedException ex) {
          LOG.error("TIMEOUT", ex);
        }
      }
    }

    return driver;
  }

  /**
   * Quit all drivers.
   */
  public void quitAll() {
    int activeDrivers = instantiatedDrivers.size();
    LOG.debug("QUIT ALL  " + activeDrivers + " ACTIVE DRIVER(S).");

    for (Driver driver : instantiatedDrivers.values()) {
      driver.quit();
    }

    driverExecutorService.shutdown();
    try {
      driverExecutorService.awaitTermination(60, TimeUnit.SECONDS);
    } catch (InterruptedException ex) {
      LOG.error("ERROR", ex);
    }
  }

  /**
   *
   * @return
   */
  public DriverRunEnvironment getDriverEnvironment() {
    return driverEnvironment;
  }

}
