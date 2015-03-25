package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import org.syftkog.web.automation.framework.test.pages.ShadowDomPage;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class ShadowDomTest {

  //@Test
    public void shadowTest() {
        final DriverFactory driverFactory = new DriverFactory(1).setRecylceDrivers(Boolean.FALSE);
                
        Driver driver = driverFactory.getDriver(BrowserVersionPlatform.WIN7CHOME);
        ShadowDomPage page = new ShadowDomPage(driver).load();        
        page.shadowTest.getText();
        driverFactory.quitAll();
    }

}
