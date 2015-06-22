package org.syftkog.web.test.framework;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.simple.JSONValue;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author BenjaminLimb
 */
public class SauceLabsWebDriver extends RemoteWebDriver {

  private final static String SAUCE_USERNAME = PropertiesRetriever.getString("saucelabs.username", null);
  private final static String SAUCE_ACCESS_KEY = PropertiesRetriever.getString("saucelabs.accessKey", null);

  private final SauceRESTApi sauceREST;

  /**
   *
   * @param url
   * @param sauceSupportedCapabilities
   */
  public SauceLabsWebDriver(URL url, Capabilities sauceSupportedCapabilities) {
    super(url, sauceSupportedCapabilities);

    sauceREST = new SauceRESTApi(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

  }

  /**
   *
   * @param name
   */
  public void updateName(String name) {
    Map<String, Object> updates = new HashMap<>();
    updates.put("name", name);
    sauceREST.updateJobInfo(this.getSessionId().toString(), updates);
  }

  /**
   *
   * @param tags
   */
  public void updateTags(ArrayList<String> tags) {
    Map<String, Object> updates = new HashMap<>();
    updates.put("tags", tags);
    sauceREST.updateJobInfo(this.getSessionId().toString(), updates);
  }

//  public void markSauceJobAsPassed() {
//    Map<String, Object> updates = new HashMap<>();
//    updates.put("passed", true);
//    sauceREST.updateJobInfo(getSessionId().toString(), updates);
//  }
  /**
   *
   * @param steps
   */
  public void markSauceJobAsPassed(String steps) {
    String escapedSteps = StringEscapeUtils.escapeJson(steps);

    Map<String, Object> updates = new HashMap<>();
    updates.put("passed", true);
    String toJSONString = JSONValue.toJSONString(escapedSteps);
    //updates.put("custom-data", "{steps:\"" + escapedSteps + "\"}");
    //updates.put("custom-data", toJSONString);
    sauceREST.updateJobInfo(getSessionId().toString(), updates);
  }

  /**
   *
   * @param steps
   * @param error
   */
  public void markSauceJobAsFailed(String steps, String error) {
    String escapedSteps = StringEscapeUtils.escapeJson(steps);
    String escapedError = StringEscapeUtils.escapeJson(error);
    Map<String, Object> updates = new HashMap<>();
    updates.put("passed", false);
    updates.put("custom-data", "{steps:\"" + escapedSteps + "\"}");
    //updates.put("custom-data", escapedSteps);    
    //updates.put("custom-data", "{steps:" + escapedSteps + ",error:" + escapedError + "}");

    //updates.put("tags", "Failed on test");
    sauceREST.updateJobInfo(getSessionId().toString(), updates);

  }

  /**
   *
   * @return
   */
  public String getSauceJobLink() {
    return sauceREST.getPublicJobLink(getSessionId().toString());
  }

}

//Map<String, Object> updates = new HashMap<>();
//updates.put("passed", false);
//updates.put("custom-data", "{steps:" + escapedSteps + ",error:" + escapedError + "}");
//  
//  Map<String, Object> updates = new HashMap<>();
//    //updates.put("passed", false);
//    //updates.put("custom-data", "{steps:" + escapedSteps + ",error:" + escapedError + "}");
//    //context.getName()    
//    updates.put("tags", context.getTags());
//
//    driver.getSauceREST().updateJobInfo(driver.getSessionId(), updates);
