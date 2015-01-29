package org.syftkog.web.test.framework;

import org.testng.annotations.DataProvider;

/**
 *
 * @author BenjaminLimb
 */
public class DriverProvider {

  /**
   *
   * @return
   */
  @DataProvider(parallel = true)
  public static Object[][] driverContext() {
    Object[][] obj = new Object[1][1];
    obj[0][0] = new DriverContextContainer();
    return obj;
  }
}
