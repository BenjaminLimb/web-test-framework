package org.syftkog.web.test.framework;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

/**
 *
 * @author BenjaminLimb
 */
public enum BrowserVersionPlatform {

  /**
   *
   */
  WIN7IE8("ie8", "internet explorer", "8", "Window 7"),

  /**
   *
   */
  WIN7IE9("ie9", "internet explorer", "9", "Window 7"),

  /**
   *
   */
  WIN7IE10("ie10", "internet explorer", "10", "Window 7"),

  /**
   *
   */
  WIN7IE11("ie11", "internet explorer", "8", "Window 7"),

  /**
   *
   */
  WIN7FF("ff", "firefox", "any", "Windows 7"),

  /**
   *
   */
  WIN7CHOME("chrome", "chrome", "any", "Windows 7"),

  /**
   *
   */
  HTMLUNIT("htmlunit", "htmlunit", "any", "any"),

  /**
   *
   */
  PHANTOMJS("phantomjs", "phantomjs", "any", "any"),

  /**
   *
   */
  MOCK("mock", "mock", "any", "any");

    private String key;
    private String browserName;
    private String browserVersion;
    private String platform;

    BrowserVersionPlatform(String key, String browserName, String browserVersion, String platform) {
        this.key = key;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.platform = platform;
    }

  /**
   *
   * @return
   */
  public String getKey() {
        return key;
    }

  /**
   *
   * @return
   */
  public String getBrowserName() {
        return browserName;
    }

  /**
   *
   * @return
   */
  public String getBrowserVersion() {
        return browserVersion;
    }

  /**
   *
   * @return
   */
  public String getPlatform() {
        return platform;
    }

  /**
   *
   * @param key
   * @return
   */
  public static BrowserVersionPlatform fromKey(String key) {
        for (BrowserVersionPlatform bvp : BrowserVersionPlatform.values()) {
            if (bvp.key.equalsIgnoreCase(key)) {
                return bvp;
            }
        }
        throw new RuntimeException(key + " is not a valid BrowserVersionPlatform key");
    }

  /**
   *
   * @return
   */
  public DesiredCapabilities toDesiredCapabilities() {
        Assert.assertNotNull(browserName);
        if (browserName != "mock") {
            Assert.assertNotNull(browserVersion);
            Assert.assertNotNull(platform);
        }
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", browserName);
        caps.setCapability("version", browserVersion);
        caps.setCapability("platform", platform);
        return caps;
    }
}
