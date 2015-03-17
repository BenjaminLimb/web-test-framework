package org.syftkog.web.test.framework;

import com.sun.jna.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This class is intentionally not implementing the Platform ENUM because it is
 * not always used in examples by Sauce Labs.
 *
 * @author BenjaminLimb
 */
public class DesiredCapabilitiesFactory {

    /**
     * Mock Driver for testing
     *
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities mock() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "mock");
        return capabilities;
    }

    /**
     * Mock Driver for testing
     *
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities htmlunit() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "htmlunit");
        return capabilities;
    }

    /**
     * Windows 8 - Internet Explorer 10
     *
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities ie10() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("platform",Platform.WINDOWS);
        capabilities.setCapability("version", "10");
        return capabilities;
    }

    /**
     * Windows 8.1 - Internet Explorer 11
     *
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities ie11() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("platform", Platform.WINDOWS);
        capabilities.setCapability("version", "11");
        return capabilities;
    }

    /**
     * Windows 7 - Firefox
     *
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities ff() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("platform", Platform.WINDOWS);
        // capabilities.setCapability("version", "25");
        return capabilities;
    }

    /**
     * Windows 7 - Safari version 5
     *
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities safari5win() {
        DesiredCapabilities capabilities = DesiredCapabilities.safari();
        capabilities.setCapability("platform", Platform.WINDOWS);
        capabilities.setCapability("version", "5");
        return capabilities;
    }

    /**
     * Google Chrome
     *
     * @return
     */
    public static DesiredCapabilities chrome() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("platform", Platform.WINDOWS);
//        capabilities.setCapability("version", "35");
        return capabilities;
    }

    /**
     * OS X 10.8 - version 6 - IPHONE - Portrait
     *
     * @deprecated - ISSUES: doesn't support ENTER key. Needs investigation.
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities iPhone() {
        DesiredCapabilities capabilities = DesiredCapabilities.iphone();
        capabilities.setCapability("platform", "OS X 10.8");
        capabilities.setCapability("version", "6");
        capabilities.setCapability("device-orientation", "portrait");
        return capabilities;
    }

    /**
     * OS X 10.8 - version 6 - Portrait
     *
     * @deprecated - ISSUES: doesn't support ENTER key. Needs investigation.
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities iPad() {
        DesiredCapabilities capabilities = DesiredCapabilities.ipad();
        capabilities.setCapability("platform", "OS X 10.8");
        capabilities.setCapability("version", "6");
        capabilities.setCapability("device-orientation", "portrait");
        return capabilities;
    }

    /**
     * OS X 10.6 Safari 5
     *
     * @return DesiredCapabilities instance for browser
     */
    public static DesiredCapabilities safari5mac() {
        DesiredCapabilities capabilities = DesiredCapabilities.safari();
        capabilities.setCapability("platform", "OS X 10.6");
        capabilities.setCapability("version", "5");
        return capabilities;
    }

  /**
   *
   * @param key
   * @return
   */
  public static DesiredCapabilities getByString(String key) {
        if (key.equals("ie10")) {
            return ie10();
        } else if (key.equals("ie11")) {
            return ie11();
        } else if (key.equals("ff")) {
            return ff();
        } else if (key.equals("chrome")) {
            return chrome();
        } else if (key.equalsIgnoreCase("htmlunit")) {
            return DesiredCapabilities.htmlUnitWithJs();
        } else {
            throw new RuntimeException("Error: \"" + key + "\" is not a supported browser key.");
        }
    }

}
