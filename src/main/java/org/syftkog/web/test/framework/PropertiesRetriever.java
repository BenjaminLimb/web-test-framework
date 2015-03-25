package org.syftkog.web.test.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author benjaminlimb
 */
public class PropertiesRetriever {

  /**
   *
   */
  public final static Logger LOG = LoggerFactory.getLogger(PropertiesRetriever.class);

  /**
   *
   */
  public static Properties props = loadPropertiesFile();

  /**
   *
   * @return
   */
  public static Properties loadPropertiesFile() {
    Properties properties = new Properties();

    try {
      File propsFile = new File("src/test/resources/propertiesRetriever.properties");
      if (propsFile.exists()) {
        properties.load(new FileInputStream(propsFile));
        LOG.trace("Loaded properties file " + "src/test/resources/propertiesRetriever.properties");
      }

      File propsFileLocal = new File("src/test/resources/propertiesRetriever-local.properties");
      if (propsFileLocal.exists()) {
        properties.load(new FileInputStream(propsFileLocal));
        LOG.trace("Loaded properties file " + "src/test/resources/propertiesRetriever-local.properties");
      }
    } catch (IOException ex) {
      LOG.error("IOException", ex);
    }

    return properties;
  }

  /**
   *
   * @param propertyName
   * @param defaultValue
   * @return
   */
  public static Integer getInteger(String propertyName, Integer defaultValue) {

    String value = getString(propertyName, null);
    if (value == null || value.equals("")) {
      return defaultValue;
    }
    try {
      Integer intValue = Integer.parseInt(value);
      return intValue;
    } catch (Exception ex) {
      throw new RuntimeException("Error parsing [" + value + "] into int.", ex);
    }
  }

  /**
   *
   * @param propertyName
   * @param defaultValue
   * @return
   */
  public static Boolean getBoolean(String propertyName, Boolean defaultValue) {

    String value = getString(propertyName, null);
    if (value == null || value.equals("")) {
      return defaultValue;
    }

    if (value.toLowerCase().contains("true") || value.toLowerCase().contains("yes") || value.contains("1")) {
      return true;
    } else if (value.toLowerCase().contains("false") || value.toLowerCase().contains("no") || value.contains("0")) {
      return false;
    } else {
      throw new RuntimeException("Error parsing [" + value + "] into int.");
    }
  }

  /**
   *
   * @param property
   * @return
   */
  public static String getString(String property) {
    String value = props.getProperty(property);
    if (value == null || value.equals("")) {
      value = System.getProperty(property);
    }
    if (value == null || value.equals("")) {
      value = System.getenv(property);
    }

    return value;

  }

  /**
   * Get a System Property or Environment Variable, or Property from
   * config.properties whichever is not null and not empty. If neither is
   * specified, return the default value.
   *
   * @param property
   * @param defaultValue
   * @return
   */
  public static String getString(String property, String defaultValue) {

    String value = System.getProperty(property);

    if (value == null || value.equals("")) {
      value = System.getenv(property);
    }
    if (value == null || value.equals("")) {
      value = props.getProperty(property);
    }
    if (value == null || value.equals("")) {
      value = defaultValue;
    }
    return value;
  }
}
