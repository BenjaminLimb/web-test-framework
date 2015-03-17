package org.syftkog.web.test.framework.elements;

import org.syftkog.web.test.framework.Element;
import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.HasSearchContext;

/**
 *
 * @author BenjaminLimb
 */
public class Textbox extends Element<Textbox> {

  /**
   *
   * @param driver
   * @param context
   * @param name
   * @param selector
   */
  public Textbox(HasDriver driver, HasSearchContext context, String name, String selector) {
        super(driver, context, name, selector);
    }

  /**
   *
   * @param driver
   * @param name
   * @param selector
   */
  public Textbox(HasDriver driver, String name, String selector) {
        super(driver, name, selector);
    }

    /**
     * For textboxes we override the getText for a getAttribute.
     *
   * @return 
     */
    @Override
    public String getText() {
        return getAttribute("value");
    }

  /**
   *
   * @param text
   * @return
   */
  public Element setText(final String text) {
        clear();
        sendKeys(text);
        return this;
    }

}
