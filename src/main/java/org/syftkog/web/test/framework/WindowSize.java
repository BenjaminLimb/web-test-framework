package org.syftkog.web.test.framework;

import org.openqa.selenium.Dimension;

/**
 *
 * @author BenjaminLimb
 */
public class WindowSize {

  /**
   *
   */
  public static int mobile = 768;

  /**
   *
   * @param dimension
   * @return
   */
  public static Boolean isMobile(Dimension dimension) {
        return (dimension != null && dimension.getWidth() <= mobile);

    }
}
