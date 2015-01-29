package org.syftkog.web.automation.framework.test.pages;

import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.Page;

/**
 *
 * @author BenjaminLimb
 */
public class NPSPage extends Page<NPSPage> {

  /**
   *
   */
  public static String url = "http://www.nps.gov/";

  /**
   *
   * @param hasDriver
   */
  public NPSPage(HasDriver hasDriver) {
        super(hasDriver, url);
    }

}
