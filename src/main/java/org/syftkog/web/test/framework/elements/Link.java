package org.syftkog.web.test.framework.elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.syftkog.web.test.framework.Element;
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
        Assert.assertTrue(getResponseCodeForHref() < 400);
    }

    /**
     * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
     *
     * @return
     */
    public Integer getResponseCodeForHref() {

        String href = getAttribute("href");
        try {
            URL urlToTest = new URL(getAttribute("href"));
            HttpURLConnection huc = (HttpURLConnection) urlToTest.openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            return huc.getResponseCode();
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Invalid URL:" + href + toString(), ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }


    
}
