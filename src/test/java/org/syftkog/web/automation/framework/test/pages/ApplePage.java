package org.syftkog.web.automation.framework.test.pages;

import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.Page;

/**
 *
 * @author BenjaminLimb
 */
public class ApplePage extends Page {

  /**
   *
   */
  public static String url = "http://syftkog.com/automation/apple.html";

  /**
   *
   * @param hasDriver
   */
  public ApplePage(HasDriver hasDriver) {
        super(hasDriver, url);
    }

}
