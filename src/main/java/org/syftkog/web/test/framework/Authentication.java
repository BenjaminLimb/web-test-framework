package org.syftkog.web.test.framework;

/**
 * This class is used for storing and managing authentication credentials. It
 * has the ability to encrypt and decrypt the password and key although it is a
 * development in progress and will most likely be changed in the future.
 *
 * @author BenjaminLimb
 */
public class Authentication {

  @com.google.gson.annotations.Expose
  private String domain;
  
  @com.google.gson.annotations.Expose
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
   * @param username
   * @param password
   */
  public Authentication(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Encrypt the password using TrippleDES
   *
   * @param encryptionKey
   */
  public void encrypt(String encryptionKey) {
    TrippleDES trippleDES = new TrippleDES(encryptionKey);
    password = trippleDES.encrypt(password);
    key = trippleDES.encrypt(key);
  }

  /**
   * Decrypt the password using TrippleDES
   *
   * @param encryptionKey
   */
  public void decrypt(String encryptionKey) {
    TrippleDES trippleDES = new TrippleDES(encryptionKey);
    password = trippleDES.decrypt(password);
    key = trippleDES.decrypt(key);
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

  /**
   * Format the credentials for printing. 
   * @return 
   */
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
