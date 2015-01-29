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
import org.syftkog.web.test.framework.elements.Selectbox;
import org.syftkog.web.test.framework.elements.Textbox;

/**
 *
 * @author benjaminlimb
 */
public class LocalTestPage extends Page {

  /**
   *
   */
//  public static String url = "http://www.w3schools.com/tags/tryhtml_select.htm";
  public static String url = "file://" + GeneralUtils.getPath() + "/src/test/resources/index.html";

  public Selectbox selectBox = new Selectbox(this, "Select box", "select");
  public Textbox first = new Textbox(this,"Textbox","#firstId");

  /**
   *
   * @param hasDriver
   */
  public LocalTestPage(HasDriver hasDriver) {
    super(hasDriver, url);
    //public static String path = new java.io.File(".").getCanonicalPath();
  }

  
}
