package org.syftkog.web.test.framework;

import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

/**
 *
 * @author BenjaminLimb
 */
public class TestCaseParameters implements TagContext {

  private HashMap<String, Object> params;

  /**
   *
   */
  public TestCaseParameters() {
    params = new HashMap<>();
    
    // Default parameters
    setBrowserVersionPlatform(BrowserVersionPlatform.WIN7FF);
    
  }

  /**
   *
   * @param name
   * @param value
   */
  public void setParameter(String name, Object value) {
    params.put(name, value);
  }

  /**
   *
   * @param name
   * @return
   */
  public Object getParameter(String name) {
    return params.get(name);
  }

  /**
   *
   * @param bvp
   */
  public TestCaseParameters(BrowserVersionPlatform bvp) {
    params.put("browserName", bvp.getBrowserName());
    params.put("browserVersion", bvp.getBrowserVersion());
    params.put("platform", bvp.getPlatform());
  }

  /**
   *
   * @param bvp
   * @return
   */
  public TestCaseParameters setBrowserVersionPlatform(BrowserVersionPlatform bvp) {
    setParameter("browserName", bvp.getBrowserName());
    setParameter("browserVersion", bvp.getBrowserVersion());
    setParameter("platform", bvp.getPlatform());
    return this;
  }

  /**
   *
   * @return
   */
  public DesiredCapabilities toDesiredCapabilities() {
    Assert.assertNotNull(getParameter("browserName"), "Browser name must be specified.");
    if (getParameter("browserName") != "mock") {
      Assert.assertNotNull(getParameter("browserVersion"), "Browser version must be specified");
      Assert.assertNotNull(getParameter("platform"), "Platform must be specified.");
    }

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("browserName", getParameter("browserName"));
    caps.setCapability("version", getParameter("browserVersion"));
    caps.setCapability("platform", getParameter("platform"));
    return caps;
  }

  /**
   *
   * @return
   */
  public Dimension getWindowSize() {
    return (Dimension) getParameter("windowSize");
  }

  /**
   *
   * @param windowSize
   * @return
   */
  public TestCaseParameters setWindowSize(Dimension windowSize) {
    setParameter("windowSize", windowSize);
    return this;
  }

  /**
   *
   * @return
   */
  public String getBrowserName() {
    return (String) getParameter("browserName");
  }

  /**
   *
   * @param browserName
   * @return
   */
  public TestCaseParameters setBrowserName(String browserName) {
    setParameter("browserName", browserName);
    return this;
  }
  
  /**
   *
   * @return
   */
  public String getExperiments() {
    return (String) getParameter("experiments");
  }

  /**
   *
   * @param experiments
   * @return
   */
  public TestCaseParameters setExperiments(String experiments) {
    setParameter("experiments", experiments);
    return this;
  }

  /**
   *
   * @return
   */
  public String getBrowserVersion() {
    return (String) getParameter("browserVersion");
  }

  /**
   *
   * @param browserVersion
   * @return
   */
  public TestCaseParameters setBrowserVersion(String browserVersion) {
    setParameter("browserVersion", browserVersion);
    return this;
  }

  /**
   *
   * @return
   */
  public String getPlatform() {
    return  getParameter("platform").toString();
  }

  /**
   *
   * @param platform
   * @return
   */
  public TestCaseParameters setPlatform(String platform) {
    setParameter("platform", platform);
    return this;
  }

  /**
   *
   * @return
   */
  public Integer getMaxAttempts() {
    if (getParameter("maxAttempts") != null) {
      return (Integer) getParameter("maxAttempts");
    }
    return null;
  }

  /**
   *
   * @param maxAttempts
   * @return
   */
  public TestCaseParameters setMaxAttempts(Integer maxAttempts) {
    setParameter("maxAttempts", maxAttempts);
    return this;
  }

  /**
   *
   * @param environment
   * @return
   */
  public TestCaseParameters setEnvironment(Environment environment) {
    setParameter("environment", environment);
    return this;
  }

  /**
   *
   * @return
   */
  public Environment getEnvironment() {
    return (Environment) getParameter("environment");
  }

  /**
   *
   * @param authentication
   * @return
   */
  public TestCaseParameters setAuthentication(Authentication authentication) {
    setParameter("authentication", authentication);
    return this;
  }

  /**
   *
   * @return
   */
  public Authentication getAuthentication() {
    return (Authentication) getParameter("authentication");
  }

  /**
   *
   * @param authenticate
   * @return
   */
  public TestCaseParameters setAuthenticate(Boolean authenticate) {
    setParameter("authenticate", authenticate);
    return this;
  }

  /**
   *
   * @return
   */
  public Boolean getAuthenticate() {
    return (Boolean) getParameter("authenticate");
  }

  /**
   *
   * @param language
   * @return
   */
  public TestCaseParameters setLanguage(Language language) {
    setParameter("language", language);
    return this;
  }

  /**
   *
   * @return
   */
  public Language getLanguage() {
    return (Language) getParameter("language");
  }

  /**
   *
   * @return
   */
  @Override
  public ArrayList<String> getTags() {
    ArrayList<String> tags = new ArrayList<>();

    if (getBrowserName() != null && !getBrowserName().equalsIgnoreCase("any")) {
      tags.add(getBrowserName());
    }
    if (getBrowserVersion() != null && !getBrowserVersion().equalsIgnoreCase("any")) {
      tags.add(getBrowserVersion());
    }
    if (getPlatform() != null && !getPlatform().equalsIgnoreCase("any")) {
      tags.add(getPlatform());
    }

    return tags;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    if (getBrowserName() != null && !getBrowserName().equalsIgnoreCase("any")) {
      sb.append(" ").append(getBrowserName());
    }
    if (getBrowserVersion() != null && !getBrowserVersion().equalsIgnoreCase("any")) {
      sb.append(" ").append(getBrowserVersion());
    }
    if (getPlatform() != null && !getPlatform().equalsIgnoreCase("any")) {
      sb.append(" ").append(getPlatform());
    }

    return sb.toString();
  }

  /**
   *
   * @param tag
   */
  @Override
  public void addTag(String tag) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
