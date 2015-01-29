package org.syftkog.web.test.framework;

/**
 *
 * @author BenjaminLimb
 */
public class Session {

  private String sessionId = null;
  private EnvironmentType environmentType;

  /**
   *
   * @return
   */
  public String getSessionId() {
    return sessionId;
  }

  /**
   *
   * @param sessionId
   */
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  /**
   *
   * @return
   */
  public EnvironmentType getEnvironmentType() {
    return environmentType;
  }

  /**
   *
   * @param environmentType
   */
  public void setEnvironmentType(EnvironmentType environmentType) {
    this.environmentType = environmentType;
  }

}
