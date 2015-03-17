package org.syftkog.web.test.framework;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 *
 * @author BenjaminLimb
 */
public class SauceListener implements ITestListener {

  private static Logger LOG = LoggerFactory.getLogger(SauceListener.class);

  private Driver getDriver(ITestResult itr) {
    Object[] parameters = itr.getParameters();
    for (Object object : parameters) {
      if (object instanceof DriverContext) {
        DriverContext driverContext = (DriverContext) object;

        if (driverContext.isDriverInitialized()) {
          Driver driver = driverContext.getDriver();
          return driver;
        }
      }
    }
    return null;
  }

  private SauceLabsWebDriver getSauceDriver(ITestResult itr) {
    Driver driver = getDriver(itr);
    if (driver != null && driver.getWrappedDriver() instanceof SauceLabsWebDriver) {
      SauceLabsWebDriver sauceDriver = (SauceLabsWebDriver) driver.getWrappedDriver();
      return sauceDriver;
    }
    return null;
  }

  private ArrayList<String> getTags(ITestResult itr) {
    Object[] parameters = itr.getParameters();
    for (Object o : parameters) {
      if (o instanceof TagContext) {
        TagContext tags = (TagContext) o;
        return tags.getTags();
      }
    }
    return new ArrayList<>();
  }

  /**
   *
   * @param itr
   */
  @Override
  public void onTestStart(ITestResult itr) {
//    ArrayList<String> tags = getTags(itr);
//    SauceLabsWebDriver driver = getSauceDriver(itr);
//    if (tags != null && tags.size() > 1 && driver != null) {
//      driver.updateTags(tags);
//
//    } else {
//      LOG.debug("DRIVER WAS NOT an instance of SauceLabsWebDriver or tags were empty");
//    }
  }

  /**
   *
   * @param itr
   */
  public void updateTags(ITestResult itr) {
    SauceLabsWebDriver driver = getSauceDriver(itr);
    if (driver != null) {
      ArrayList<String> tags = getTags(itr);
      if (tags != null && tags.size() > 1) {
        driver.updateTags(tags);

        // driver.updateName(itr.getTestClass().getName() + "." + itr.getMethod().getMethodName());
      }
    }
  }

  /**
   *
   * @param itr
   */
  @Override
  public void onTestSuccess(ITestResult itr) {
    updateTags(itr);

    SauceLabsWebDriver driver = getSauceDriver(itr);
    if (driver != null) {

      driver.markSauceJobAsPassed(getSteps(itr));
    }
  }

  /**
   *
   * @param itr
   * @return
   */
  public String getSteps(ITestResult itr) {
    Driver rawDriver = this.getDriver(itr);
    if (rawDriver != null) {
      String steps = rawDriver.getStepLogger().getText();
      return steps;
    }
    return "";
  }

  /**
   *
   * @param itr
   */
  @Override
  public void onTestFailure(ITestResult itr) {
    updateTags(itr);
    Driver rawDriver = getDriver(itr);
    SauceLabsWebDriver driver = getSauceDriver(itr);
    if (driver != null) {
      String error = itr.getThrowable().toString();
      driver.markSauceJobAsFailed(getSteps(itr), error);
    }
  }

  /**
   *
   * @param itr
   */
  @Override
  public void onTestSkipped(ITestResult itr) {

  }

  /**
   *
   * @param itr
   */
  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult itr) {

  }

  /**
   *
   * @param itc
   */
  @Override
  public void onStart(ITestContext itc) {

  }

  /**
   *
   * @param itc
   */
  @Override
  public void onFinish(ITestContext itc) {

  }

}
