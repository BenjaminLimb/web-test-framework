package ${package};

import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.Page;

/**
 *
 * @author BenjaminLimb
 */
public class GoogleHomePage extends Page<GoogleHomePage> {

  public static String path = "google.com";

  public GoogleHomePage(HasDriver hasDriver) {
    super(hasDriver, path);
  }

}
