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

import org.syftkog.web.test.framework.PropertiesRetriever;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author benjaminlimb
 */
public class PropertiesRetrieverTest {

  /**
   *
   */
  @Test(groups = "unit")
  public void getPropertiesRetriever() {
    String key = "environments.production.default.host";
    String expected = "http://syftkog.org";
    String actual = PropertiesRetriever.getString(key, null);
    Assert.assertEquals(actual, expected);
    System.out.println();
  }
  
 
  
  
}
