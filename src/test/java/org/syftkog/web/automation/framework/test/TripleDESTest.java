/*
 * Copyright 2014 BenjaminLimb.
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

import org.syftkog.web.test.framework.TrippleDES;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class TripleDESTest {

  /**
   *
   * @throws Exception
   */
  @Test(groups = "unit")
  public void testTripleDES() throws Exception {
    
    TrippleDES td = new TrippleDES("123498274098237089992334"); // must be 24 characters

    String target = "mySecret";
    String encrypted = td.encrypt(target);
    Assert.assertNotEquals(target, encrypted);
    String decrypted = td.decrypt(encrypted);
    Assert.assertEquals(target, decrypted);
  }

}
