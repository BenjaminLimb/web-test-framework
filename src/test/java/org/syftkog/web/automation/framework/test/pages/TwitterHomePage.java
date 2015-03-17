package org.syftkog.web.automation.framework.test.pages;

import org.syftkog.web.test.framework.HasDriver;
import org.syftkog.web.test.framework.Page;
import org.syftkog.web.test.framework.elements.Button;
import org.syftkog.web.test.framework.elements.Textbox;

/**
 *
 * @author BenjaminLimb
 */
public class TwitterHomePage extends Page<TwitterHomePage> {

  /**
   *
   */
  public static String url = "https://twitter.com";

  /**
   *
   */
  public Textbox signInEmail = new Textbox(this, "Sign In Email", "#signin-email");

  /**
   *
   */
  public Textbox signInPassword = new Textbox(this, "Sign In Password", "#signin-password");

  /**
   *
   */
  public Button signInButton = new Button(this, "Sign In", ".submit.primary-btn");

  /**
   *
   * @param hasDriver
   */
  public TwitterHomePage(HasDriver hasDriver) {
        super(hasDriver, url);
    }

}
