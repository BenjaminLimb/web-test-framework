package org.syftkog.web.test.framework;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public interface DriverContext<T extends DriverContext> {

  /**
   *
   * @return
   */
  public Driver getDriver();

  /**
   *
   * @return
   */
  public Boolean isDriverInitialized();

  /**
   *
   * @param driver
   * @return
   */
  public T setDriver(Driver driver);

  /**
   *
   * @return
   */
  public DriverFactory getDriverFactory();

  /**
   *
   * @param factory
   * @return
   */
  public T setDriverFactory(DriverFactory factory);
}
