package org.syftkog.web.test.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author BenjaminLimb
 */
public class PageFactory {

  /**
   *
   * @param <P>
   * @param driver
   * @param clazz
   * @return
   */
  public static <P extends Page> P createPage(Driver driver, Class<P> clazz) {
        try {
            Constructor con = clazz.getConstructor(HasDriver.class);
            P page = (P) con.newInstance(driver);

            driver.addPageToDriverHistory(page);
            page.waitUntil().correctPage();
            return page;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException("Error Creating page with " + clazz + " must have simple contructor with HasDriver parameter");
        }
    }
}
