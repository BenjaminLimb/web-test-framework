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
package org.syftkog.web.automation.framework.test;

import org.syftkog.web.automation.framework.test.pages.LocalTestPage;
import org.syftkog.web.test.framework.DesiredCapabilitiesFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.DriverFactory;
import org.testng.annotations.Test;

/**
 *
 * @author benjaminlimb
 */
public class ElementConditionsTest {

  @Test(groups = "unit")
  public void testGetValue() {
    DriverFactory factory = new DriverFactory();
//    Driver driver = factory.getDriver(DesiredCapabilitiesFactory.htmlunit());
    Driver driver = factory.getDriver(DesiredCapabilitiesFactory.ff());
    LocalTestPage page = new LocalTestPage(driver);
    page.load();
    String text = page.selectBox.getText();
    page.selectBox.selectByValue("red");
    page.selectBox.assertElement().equals("Red");
    page.selectBox.selectByVisibleText("Orange");
    page.selectBox.assertElement().equals("Orange");
    
    page.first.setText("My Text String");
    page.first.assertElement().containsText("Text");
    page.first.assertElement().doesNotContainText("texts");
    page.first.assertElement().equals("mY tExT STRING");    
    
    driver.quit();    
    
  }
}
