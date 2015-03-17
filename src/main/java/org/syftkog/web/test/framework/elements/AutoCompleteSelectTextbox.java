package org.syftkog.web.test.framework.elements;

import org.syftkog.web.test.framework.Element;
import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.HasSearchContext;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syftkog.web.test.framework.SeleniumHelperUtil;

/**
 *
 * @author BenjaminLimb
 */
public class AutoCompleteSelectTextbox extends Element<AutoCompleteSelectTextbox> {

  /**
   *
   */
  public static final Logger LOG = LoggerFactory.getLogger(Element.class.getName());

  /**
   *
   * @param hasDriver
   * @param hasSearchContext
   * @param name
   * @param selector
   */
  public AutoCompleteSelectTextbox(HasDriver hasDriver, HasSearchContext hasSearchContext, String name, String selector) {
        super(hasDriver, hasSearchContext, name, selector);
    }

  /**
   *
   * @param hasDriver
   * @param name
   * @param selector
   */
  public AutoCompleteSelectTextbox(HasDriver hasDriver, String name, String selector) {
        super(hasDriver, name, selector);
    }

  /**
   *
   * @param itemToSelect
   * @return
   */
  public Element select(String itemToSelect) {

        SeleniumHelperUtil.waitForJQueryNotActive(getDriver(), 10);
        getDriver().logStep("SET: " + this.toString() + " to " + "\"" + itemToSelect + "\"");

        clear();
        sendKeys(itemToSelect);
        SeleniumHelperUtil.waitForJQueryNotActive(getDriver(),10);
        sendKeys(Keys.TAB);

        String newValue = getAttribute("value");

        // Fix for autoselecting the wrong value when multiple entries match the desired string. This will down arrow through them till it gets set correctly.
        if (!newValue.equals(itemToSelect)) {
            clear();
            sendKeys(itemToSelect);

        }
        int cycleLimiter = 0; //TODO: This is not the most efficient way to do this... 
        while (!newValue.equals(itemToSelect) && cycleLimiter < 10) {
            sendKeys(Keys.ARROW_DOWN);
            newValue = getAttribute("value");
            cycleLimiter++;
        }

        if (!newValue.equals(itemToSelect)) {

            LOG.error("\"Value could not be set. Attempted to set \"" + toString()
                    + "\" to " + itemToSelect + ". Value currently is " + newValue);
            throw new RuntimeException("Value could not be set. Attempted to set \"" + this.toString()
                    + " to " + itemToSelect + ". Value currently is " + newValue);

        }

        // System.out.println(getDriver().executeJavascript("return $(':focus').blur()"));
        return this;
    }

  /**
   *
   * @return
   */
  @Override
    public String getText() {
        return getAttribute("value");
    }

}
