/*
 * Copyright 2015 benjaminlimb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syftkog.misc.sauce;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.syftkog.web.test.framework.DesiredCapabilitiesFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.PropertiesRetriever;
import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 *
 * @author benjaminlimb
 */
public class SauceTest {

  @Test
  public void testSauceDriver() {
    DriverFactory df = new DriverFactory();

    String username = PropertiesRetriever.getString("saucelabs.username", null);
    String password = PropertiesRetriever.getString("saucelabs.accessKey", null);
    if (username == null || password == null) {
      throw new SkipException("Cannot Test Sauce without saucelabs.username and saucelabs.accessKey set");
    }

    DesiredCapabilities dc = DesiredCapabilitiesFactory.ff();
    dc.setCapability("desiredEnvironment", "SAUCE");
    Driver driver = df.getDriver(dc);
    driver.navigate().to("https://saucelabs.com/");
    driver.quit();
  }

}
