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
public class Image extends Element<Image> {

  public void assertValidResponseCodeForSrc() {
    this.logAction("Assert valid http src response code:");
        Assert.assertTrue(getResponseCodeForSrc() < 400);
    }
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
  
   /**
   *
   * @return
   */
  public Integer getResponseCodeForSrc() {

    String src = getAttribute("src");
    return GeneralUtils.getResponseCode(src);

  }
}
