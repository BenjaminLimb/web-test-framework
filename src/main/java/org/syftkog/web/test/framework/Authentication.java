package org.syftkog.web.test.framework;

/**
 *
 * @author BenjaminLimb
 */
public class Authentication {

  private String domain;
  private String username;
  private String password;
  private String key;

  /**
   *
   */
  public Authentication() {
    this.domain = PropertiesRetriever.getString("authentication.domain");
    this.username = PropertiesRetriever.getString("authentication.username");
    this.password = PropertiesRetriever.getString("authentication.password");
    this.key = PropertiesRetriever.getString("authentication.key");
  }

  /**
   *
   * @param encryptionKey
   */
  public void encrypt(String encryptionKey) {
    TrippleDES trippleDES = new TrippleDES(encryptionKey);
    password = trippleDES.encrypt(password);
    key = trippleDES.encrypt(key);
  }

  /**
   *
   * @param encryptionKey
   */
  public void decrypt(String encryptionKey) {
    TrippleDES trippleDES = new TrippleDES(encryptionKey);
    domain = trippleDES.decrypt(domain);
    username = trippleDES.decrypt(username);
    password = trippleDES.decrypt(password);
    key = trippleDES.decrypt(key);
  }

  /**
   *
   * @param username
   * @param password
   */
  public Authentication(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   *
   * @return
   */
  public String getUsername() {
    return username;
  }

  /**
   *
   * @param username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   *
   * @return
   */
  public String getPassword() {
    return password;
  }

  /**
   *
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   *
   * @return
   */
  public String getKey() {
    return key;
  }

  /**
   *
   * @param key
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   *
   * @return
   */
  public String getDomain() {
    return domain;
  }

  /**
   *
   * @param domain
   */
  public void setDomain(String domain) {
    this.domain = domain;
  }

  @Override
  public String toString() {
    String separator = System.getProperty("line.separator");
    StringBuilder sb = new StringBuilder();
    sb.append("domain:").append(domain);
    sb.append(separator);
    sb.append("username:").append(username);
    sb.append(separator);
    sb.append("password:").append(password);
    sb.append(separator);
    sb.append("key:").append(key);
    sb.append(separator);

    return sb.toString();
  }

}
