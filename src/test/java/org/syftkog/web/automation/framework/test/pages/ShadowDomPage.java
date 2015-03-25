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
package org.syftkog.web.automation.framework.test.pages;

import org.syftkog.web.test.framework.GeneralUtils;
import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.Page;
import org.syftkog.web.test.framework.elements.Text;

/**
 *
 * @author benjaminlimb
 */
public class ShadowDomPage extends Page<ShadowDomPage> {

  /**
   *
   */
  public static String url = "file://" + GeneralUtils.getPath() + "/src/test/resources/shadow.html";

  public Text shadowTest = new Text(this,"Shadow Text","[data-test='inShadow']");
  /**
   *
   * @param hasDriver
   */
  public ShadowDomPage(HasDriver hasDriver) {
    super(hasDriver, url);    
  }

  
}
