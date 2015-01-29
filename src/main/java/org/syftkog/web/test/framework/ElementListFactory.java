package org.syftkog.web.test.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.openqa.selenium.WebElement;

/**
 *
 * @author BenjaminLimb
 */
public class ElementListFactory {

  /**
   *
   * @param <T>
   * @param clazz
   * @param hasDriver
   * @param hasSearchContext
   * @param name
   * @param listSelector
   * @param patternSelector
   * @return
   */
  public static <T extends Element> ElementList<T> create(final Class clazz, HasDriver hasDriver, HasSearchContext hasSearchContext, String name, String listSelector, String patternSelector) {
    ElementFactory<T> factory = new ElementFactory<T>() {
      @Override

      public T make(HasDriver hasDriver, HasSearchContext hasSearchContext, String name, String selector, WebElement element, WebElementFinder finder) {
        try {
          Element el = null;

          // If the element doesn't have the expected constructor, try another.
          try {
            Constructor con = clazz.getConstructor(HasDriver.class, HasSearchContext.class, String.class, String.class);
            el = ((Element) con.newInstance(hasDriver, hasSearchContext, name, selector));
          } catch (NoSuchMethodException exA) {
            Constructor con = clazz.getConstructor(HasDriver.class, String.class, String.class);
            el = ((Element) con.newInstance(hasDriver, name, selector));
          }

          el.initializeTo(element);
          el.setElementFinder(finder);

          return (T) el;

        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
          throw new RuntimeException(ex);
        }
      }
    };

    return new ElementList<>(factory, hasDriver, hasSearchContext, name, listSelector, patternSelector);
  }

  /**
   * When elements can be found by the global context, we can just use the
   * driver as the context.
   *
   * @param <T>
   * @param clazz
   * @param hasDriver
   * @param name
   * @param listSelector
   * @param patternSelector
   * @return
   */
  public static <T extends Element> ElementList<T> create(final Class clazz, HasDriver hasDriver, String name, String listSelector, String patternSelector) {
    return create(clazz, hasDriver, hasDriver.getDriver(), name, listSelector, patternSelector);
  }

  /**
   * Create a list of elements. Depend on the list to provide the
   * WebElementFinder for elements to find their underling WebElement.
   *
   * @param <T>
   * @param clazz
   * @param hasDriver
   * @param hasContext
   * @param name
   * @param listSelector
   * @return
   */
  public static <T extends Element> ElementList<T> create(final Class clazz, HasDriver hasDriver, HasSearchContext hasContext, String name, String listSelector) {
    return create(clazz, hasDriver, hasContext, name, listSelector, null);
  }

  /**
   * Create a list of elements. Depend on the list to provide the
   * WebElementFinder for elements to find their underling WebElement.
   *
   * When elements can be found by the global context, we can just use the
   * driver as the context.
   *
   * @param <T>
   * @param clazz
   * @param hasDriver
   * @param name
   * @param listSelector
   * @return
   */
  public static <T extends Element> ElementList<T> create(final Class clazz, HasDriver hasDriver, String name, String listSelector) {
    return create(clazz, hasDriver, hasDriver.getDriver(), name, listSelector, null);
  }

}
