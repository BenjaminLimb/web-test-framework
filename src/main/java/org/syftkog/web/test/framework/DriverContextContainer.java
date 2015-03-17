package org.syftkog.web.test.framework;

/**
 *
 * @author BenjaminLimb
 */
public class DriverContextContainer implements DriverContext<DriverContextContainer> {

  Driver driver;
  DriverFactory factory;

  /**
   *
   * @return
   */
  @Override
  public Driver getDriver() {
    if (driver == null) {
      driver = getDriverFactory().getDriver();
    }
    return driver;
  }

  /**
   *
   * @param driver
   * @return
   */
  @Override
  public DriverContextContainer setDriver(Driver driver) {
    this.driver = driver;
    return this;
  }

  /**
   *
   * @return
   */
  @Override
  public DriverFactory getDriverFactory() {
    // if no ehancedWebDriver factory is specified, use the singleton instance.
    if (factory == null) {
      factory = DriverFactory.getInstance();
    }
    return factory;
  }

  /**
   *
   * @param factory
   * @return
   */
  @Override
  public DriverContextContainer setDriverFactory(DriverFactory factory) {
    this.factory = factory;
    return this;
  }

  /**
   *
   * @return
   */
  @Override
  public Boolean isDriverInitialized() {
    return driver != null;
  }

}
