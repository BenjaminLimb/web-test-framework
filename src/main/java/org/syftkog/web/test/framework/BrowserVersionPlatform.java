package org.syftkog.web.test.framework;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

/**
 * This is an enum to communicate a specific browser specifications.
 * @author BenjaminLimb
 */
public enum BrowserVersionPlatform {

  /**
   *
   */
  WIN7IE8("ie8", "internet explorer", "8", Platform.VISTA),

  /**
   *
   */
  WIN7IE9("ie9", "internet explorer", "9", Platform.VISTA),

  /**
   *
   */
  WIN7IE10("ie10", "internet explorer", "10", Platform.VISTA),

  /**
   *
   */
  WIN7IE11("ie11", "internet explorer", "8", Platform.VISTA),

  /**
   *
   */
  WIN7FF("ff", "firefox", "any", Platform.VISTA),

  /**
   *
   */
  WIN7CHOME("chrome", "chrome", "any", Platform.VISTA),

  /**
   *
   */
  HTMLUNIT("htmlunit", "htmlunit", "any", Platform.ANY),

  /**
   *
   */
  PHANTOMJS("phantomjs", "phantomjs", "any", Platform.ANY),

  /**
   *
   */
  MOCK("mock", "mock", "any", Platform.ANY);

    private String key;
    private String browserName;
    private String browserVersion;
    private Platform platform;

    BrowserVersionPlatform(String key, String browserName, String browserVersion, Platform platform) {
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
  public Platform getPlatform() {
        return platform;
    }

  /**
   * Get the BrowserVersionPlatform based on the enum value. Case insensitive.
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
   * Convert the BroserVersionPlatform to a DesiredCapabilities object.
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
