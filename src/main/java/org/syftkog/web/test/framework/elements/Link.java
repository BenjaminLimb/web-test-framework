package org.syftkog.web.test.framework.elements;

import org.syftkog.web.test.framework.Element;
import org.syftkog.web.test.framework.GeneralUtils;
import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.HasSearchContext;
import org.testng.Assert;

/**
 *
 * @author BenjaminLimb
 */
public class Link extends Element<Link> {

  /**
   *
   * @param driver
   * @param context
   * @param name
   * @param selector
   */
  public Link(HasDriver driver, HasSearchContext context, String name, String selector) {
    super(driver, context, name, selector);
  }

  /**
   *
   * @param driver
   * @param name
   * @param selector
   */
  public Link(HasDriver driver, String name, String selector) {
    super(driver, name, selector);
  }

  /**
   *
   */
  public void assertValidResponseCodeForHref() {
    String href = getAttribute("href");
    this.logAction("Assert response code for href[" + href + "] < 400:");
    Assert.assertTrue(!href.contains("undefined"), "href should not contain the string 'undefined'");
    Assert.assertTrue(!href.contains("null"), "href should not contain the string 'null'");
    Integer responseCodeForHref = getResponseCodeForHref(href);

    Assert.assertTrue(responseCodeForHref < 400, "Assert "+responseCodeForHref + " < 400");
  }

  /**
   * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
   *
   * @return
   */
  public Integer getResponseCodeForHref(String href) {
    return GeneralUtils.getResponseCode(href);
  }

}
