package org.syftkog.web.test.framework;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 *
 * @author BenjaminLimb
 */
public class DriverManagerListener implements ITestListener {

  static DriverFactory factory = new DriverFactory();

  private DriverContext getDriverContext(ITestResult tr) {
    Object[] parameters = tr.getParameters();
    for (Object o : parameters) {
      if (o instanceof DriverContext) {
        return (DriverContext) o;
      }
    }
    return null;
  }

  /**
   *
   * @param context
   */
  @Override
  public void onStart(ITestContext context) {

  }

  /**
   *
   * @param context
   */
  @Override
  public void onFinish(ITestContext context) {
    factory.quitAll();
  }

  /**
   *
   * @param result
   */
  @Override
  public void onTestStart(ITestResult result) {
    DriverContext context = getDriverContext(result);
    if (context != null) {
      context.setDriverFactory(factory);
    }
  }

  /**
   *
   * @param result
   */
  @Override
  public void onTestSuccess(ITestResult result) {
    // If the driver has not already been quit or recycled, then call recycle.
    DriverContext context = getDriverContext(result);
    if (context != null && context.isDriverInitialized()) {
      Driver driver = context.getDriver();
      context.setDriver(null); // Revoke the driver
      //If the driver is still usable, recycle it. 
      if (driver.isAlive()) {
        driver.recycle();
      }
    }
  }

  /**
   *
   * @param result
   */
  @Override
  public void onTestFailure(ITestResult result) {
    DriverContext context = getDriverContext(result);
    if (context != null && context.isDriverInitialized()) {
      Driver driver = context.getDriver();
      if (driver != null) {
        driver.quit();
      }
    }
  }

  /**
   *
   * @param result
   */
  @Override
  public void onTestSkipped(ITestResult result) {

    DriverContext context = getDriverContext(result);
    if (context != null && context.isDriverInitialized()) {
      Driver driver = context.getDriver();
      if (driver != null) {
        driver.quit();
      }
    }
    //TODO: NEED TO DO SOMETHING ELSE HERE
    result.getThrowable().printStackTrace();
  }

  /**
   *
   * @param result
   */
  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    DriverContext context = getDriverContext(result);
    if (context != null && context.isDriverInitialized()) {
      Driver driver = context.getDriver();
      if (driver != null) {
        driver.quit();
      }
    }

  }

}
