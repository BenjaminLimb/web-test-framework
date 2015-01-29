package org.syftkog.selenium.helper;


import static org.syftkog.web.test.framework.SeleniumHelperUtil.capabilitiesOfDesiredMatchAvailable;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 *
 * @author BenjaminLimb
 */
public class CapabilititesMatchTest {

  /**
   *
   */
  @Test(groups = "unit")
    public void capabilitiesMatchTestA_simpleMatch() {
        DesiredCapabilities capsA = new DesiredCapabilities();
        capsA.setBrowserName("Firefox");

        DesiredCapabilities capsB = new DesiredCapabilities();
        capsB.setBrowserName("Internet Explorer");

        Assert.assertFalse(capabilitiesOfDesiredMatchAvailable(capsA, capsB));
    }

  /**
   *
   */
  @Test(groups = "unit")
    public void capabilitiesMatchTestB_any() {
        DesiredCapabilities desired = new DesiredCapabilities();
        desired.setBrowserName("ANY");

        DesiredCapabilities available = new DesiredCapabilities();
        available.setBrowserName("Firefox");

        Assert.assertTrue(capabilitiesOfDesiredMatchAvailable(desired, available));
    }

  /**
   *
   */
  @Test(groups = "unit")
    public void capabilitiesMatchTestC_missing() {
        DesiredCapabilities desired = new DesiredCapabilities();
        desired.setBrowserName("Firefox");

        DesiredCapabilities available = new DesiredCapabilities();
        Assert.assertFalse(capabilitiesOfDesiredMatchAvailable(desired, available));
    }

  /**
   *
   */
  @Test(groups = "unit")
    public void capabilitiesMatchTestD_null() {
        DesiredCapabilities desired = new DesiredCapabilities();
        desired.setBrowserName("Firefox");

        DesiredCapabilities available = new DesiredCapabilities();
        available.setBrowserName(null);
        Assert.assertFalse(capabilitiesOfDesiredMatchAvailable(desired, available));
    }

}
