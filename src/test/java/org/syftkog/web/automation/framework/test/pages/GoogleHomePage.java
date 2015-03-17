package org.syftkog.web.automation.framework.test.pages;

import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.Page;
import org.syftkog.web.test.framework.elements.Textbox;


/**
 *
 * @author BenjaminLimb
 */
public class GoogleHomePage extends Page<GoogleHomePage> {

  /**
   *
   */
  public static String url = "https://google.com";

  /**
   *
   */
  public Textbox searchBox = new Textbox(this,"Search Textbox","[name='q']");
    
  /**
   *
   * @param hasDriver
   */
  public GoogleHomePage(HasDriver hasDriver) {
        super(hasDriver, url);
    }

}
