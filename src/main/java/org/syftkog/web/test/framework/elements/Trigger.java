package org.syftkog.web.test.framework.elements;

import org.syftkog.web.test.framework.Element;
import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.HasSearchContext;

/**
 * Trigger elements are links (not necessarily "a" tags) that hide / show
 * content. Triggers work based on the presence/absence of the class of the
 * elementToObserve (default itself). when and only when it is expanded.
 *
 * @author BenjaminLimb
 */
public class Trigger extends Element<Trigger> {

  private String triggerClass = "triggered";
  private Element elementToObserve = this;
  private Boolean expandedWhenClassPresent = true;
  private Boolean clickUsingJavascript = true;

  /**
   *
   * @param driver
   * @param context
   * @param name
   * @param selector
   * @param triggerClass
   */
  public Trigger(HasDriver driver, HasSearchContext context, String name, String selector, String triggerClass) {
    super(driver, context, name, selector);
    this.triggerClass = triggerClass;
  }

  /**
   *
   * @param driver
   * @param name
   * @param selector
   * @param triggerClass
   */
  public Trigger(HasDriver driver, String name, String selector, String triggerClass) {
    super(driver, name, selector);
    this.triggerClass = triggerClass;
  }

  /**
   *
   * @param elementToObserve
   * @return
   */
  public Trigger setElementToObserve(Element elementToObserve) {
    this.elementToObserve = elementToObserve;
    return this;
  }

  /**
   *
   * @param expandedWhenClassPresent
   * @return
   */
  public Trigger setExpandedWhenClassPresent(Boolean expandedWhenClassPresent) {
    this.expandedWhenClassPresent = expandedWhenClassPresent;
    return this;
  }

  /**
   *
   * @param desiredValue
   */
  public Trigger setTrigger(Boolean desiredValue) {
    if (desiredValue) {
      getDriver().logStep("TRIGGER ON: " + toString());
    } else {
      getDriver().logStep("TRIGGER OFF: " + toString());
    }

    if (desiredValue != getTriggerState()) {
      if (this.clickUsingJavascript) {
        getDriver().executeScript("$('" + getElementSelector() + "').eq(0).mousedown().mouseup().click();");
      } else {
        click();
      }
    }
    return this;
  }

  /**
   *
   * @return
   */
  public Boolean getTriggerState() {
    Boolean value = elementToObserve.getAttribute("class").contains(triggerClass) == expandedWhenClassPresent;
    return value;
  }

  /**
   *
   */
  public Trigger expand() {
    setTrigger(true);
    return this;
  }

  /**
   *
   */
  public Trigger collapse() {
    setTrigger(false);
    return this;
  }

  /**
   *
   */
  public Trigger setOn() {
    setTrigger(true);
    return this;
  }

  /**
   *
   */
  public Trigger setOff() {
    setTrigger(false);
    return this;
  }

  /**
   *
   * @return
   */
  public Boolean isClickUsingJavascript() {
    return clickUsingJavascript;
  }

  /**
   *
   * @param clickUsingJavascript
   * @return
   */
  public Trigger setClickUsingJavascript(Boolean clickUsingJavascript) {
    this.clickUsingJavascript = clickUsingJavascript;
    return this;
  }

  /**
   *
   * @return
   */
  public String getTriggerClass() {
    return triggerClass;
  }

  /**
   *
   * @param triggerClass
   * @return
   */
  public Trigger setTriggerClass(String triggerClass) {
    this.triggerClass = triggerClass;
    return this;
  }

}
