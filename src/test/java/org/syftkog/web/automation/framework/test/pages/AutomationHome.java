package org.syftkog.web.automation.framework.test.pages;

import org.syftkog.web.test.framework.ElementList;
import org.syftkog.web.test.framework.ElementListFactory;
import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.Page;
import org.syftkog.web.test.framework.elements.Container;

/**
 *
 * @author BenjaminLimb
 */
public class AutomationHome extends Page {

  /**
   *
   */
  public static String url = "http://syftkog.com/automation/";

  /**
   *
   */
  public ElementList<Container> fruits = ElementListFactory.create(Container.class, this, "List of Fruits", "#fruitList li", "#fruitList li:nth-of-type(###)");

  /**
   *
   * @param hasDriver
   */
  public AutomationHome(HasDriver hasDriver) {
        super(hasDriver, url);
    }

}
