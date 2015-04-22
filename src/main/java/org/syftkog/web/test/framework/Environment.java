package org.syftkog.web.test.framework;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author BenjaminLimb
 */
public class Environment {

  /**
   *
   */
  public transient final Logger LOG = LoggerFactory.getLogger(Environment.class);

  @com.google.gson.annotations.Expose
  private final EnvironmentType environmentType;

  @com.google.gson.annotations.Expose
  private final URI uri;

  /**
   *
   * @param environmentType
   * @param uri
   */
  public Environment(EnvironmentType environmentType, URI uri) {
    this.environmentType = environmentType;
    this.uri = uri;
  }

  /**
   *
   * @param environmentType
   * @param url
   */
  public Environment(EnvironmentType environmentType, String url) {
    this.environmentType = environmentType;
    this.uri = URI.create(url);
  }

  /**
   *
   * @param path
   * @return
   */
  public String constructURLForPath(String path) {
    try {
      URL base = uri.toURL();
      return new URL(base, path).toString();
    } catch (MalformedURLException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   *
   * @param sURL
   * @return
   * @throws MalformedURLException
   */
  public String fixHost(String sURL) throws MalformedURLException {
    URL url = new URL(sURL);
    String file = url.getFile();
    return this.constructURLForPath(file);
  }

  /**
   *
   * @return
   */
  public URL getURL() {
    try {
      return uri.toURL();
    } catch (MalformedURLException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   *
   * @return
   */
  public String getHost() {
    return getURL().getHost();
  }

  /**
   *
   * @return
   */
  public EnvironmentType getEnvironmentType() {
    return environmentType;
  }

}
