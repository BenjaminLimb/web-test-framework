package org.syftkog.web.test.framework.elements;

import org.syftkog.web.test.framework.Element;
import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.HasSearchContext;

/**
 *
 * @author BenjaminLimb
 */
public class Image extends Element<Image> {

  /**
   *
   * @param driver
   * @param context
   * @param name
   * @param selector
   */
  public Image(HasDriver driver, HasSearchContext context, String name, String selector) {
        super(driver, context, name, selector);
    }

  /**
   *
   * @param driver
   * @param name
   * @param selector
   */
  public Image(HasDriver driver, String name, String selector) {
        super(driver, name, selector);
    }
}
