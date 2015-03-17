package org.syftkog.web.test.framework;

import org.openqa.selenium.WebElement;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public interface ElementFactory<T extends Element> {

  /**
   *
   * @param hasDriver
   * @param hasSearchContext
   * @param name
   * @param selector
   * @param element
   * @param finder
   * @return
   */
  public T make(HasDriver hasDriver, HasSearchContext hasSearchContext, String name, String selector, WebElement element, WebElementFinder finder);
}
