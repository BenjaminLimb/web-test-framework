package org.syftkog.web.automation.framework.test.pages;

import org.syftkog.web.test.framework.ElementList;
import org.syftkog.web.test.framework.ElementListFactory;
import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.Page;
import org.syftkog.web.test.framework.elements.Container;
import org.syftkog.web.test.framework.elements.Textbox;

/**
 *
 * @author BenjaminLimb
 */
public class GoogleResultsPage extends Page<GoogleResultsPage> {

  /**
   *
   */
  public static String url = "https://google.com";

  /**
   *
   */
  public Textbox searchBox = new Textbox(this, "Search Textbox", "[name='q']");

  /**
   *
   */
  public ElementList<Container> topResuts = ElementListFactory.create(Container.class, this, "Search Results", "#ires li.g ", "#rso .srg:nth-of-type(2) .g:nth-of-type(###)");

  /**
   *
   * @param hasDriver
   * @param query
   */
  public GoogleResultsPage(HasDriver hasDriver, String query) {
        super(hasDriver, url + "#q=" + query);
    }

}
