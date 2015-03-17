package org.syftkog.web.test.framework;

import java.util.ArrayList;

/**
 *
 * @author BenjaminLimb
 */
public interface TagContext {

  /**
   *
   * @return
   */
  public ArrayList<String> getTags();

  /**
   *
   * @param tag
   */
  public void addTag(String tag);
}
