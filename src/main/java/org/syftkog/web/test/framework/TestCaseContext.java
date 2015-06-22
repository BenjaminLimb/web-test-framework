package org.syftkog.web.test.framework;

import java.util.ArrayList;
import org.syftkog.web.test.framework.retry.RetryTestContext;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.SkipException;

/**
 *
 * @author BenjaminLimb
 */
public class TestCaseContext implements WrapsTestCaseContext, HasDriver, TagContext, HasStepLogger, DriverContext<TestCaseContext>, RetryTestContext<TestCaseContext> {

  private TestCaseParameters testCaseParameters;
  private transient DriverFactory driverFactory;
  private transient Driver driver;
  private transient StepLogger stepLogger = new StepLogger();
  private transient int currentAttempt = 1;
  private  String name;
  private transient ITestResult testResult;
  private ArrayList<String> tags;

  /**
   *
   * @param params
   */
  public TestCaseContext(TestCaseParameters params) {
    this.testCaseParameters = params;
  }

  /**
   *
   * @param bvp
   */
  public TestCaseContext(BrowserVersionPlatform bvp) {
    testCaseParameters = new TestCaseParameters(bvp);
  }

  /**
   *
   */
  public TestCaseContext() {
    testCaseParameters = new TestCaseParameters();
  }

  /**
   *
   * @return
   */
  @Override
  public DriverFactory getDriverFactory() {
    // if no Driver factory is specified, use the singleton instance.
    if (driverFactory == null) {
      driverFactory = DriverFactory.getInstance();
    }
    return driverFactory;
  }

  /**
   *
   * @param driverFactory
   * @return
   */
  @Override
  public TestCaseContext setDriverFactory(DriverFactory driverFactory) {
    this.driverFactory = driverFactory;
    return this;
  }

  /**
   *
   * @return
   */
  @Override
  public StepLogger getStepLogger() {
    return stepLogger;
  }

  /**
   *
   * @param stepLogger
   */
  public void setStepLogger(StepLogger stepLogger) {
    this.stepLogger = stepLogger;
  }

  /**
   *
   * @return
   */
  @Override
  public Integer getMaxRetryCount() {
    if (parameters().getMaxAttempts() != null) {
      return this.parameters().getMaxAttempts() +1;
    } else {
      return null;
    }
  }

  /**
   *
   * @return
   */
  @Override
  public Boolean isDriverInitialized() {
    return driver != null;
  }

  /**
   *
   * @return
   */
  @Override
  public Driver getDriver() {
    if (driver == null) {
      driver = getDriverFactory().getDriver(this);
    }
    return driver;
  }

  /**
   *
   * @param driver
   * @return
   */
  @Override
  public TestCaseContext setDriver(Driver driver) {
    this.driver = driver;
    return this;
  }

  /**
   *
   * @return
   */
  @Override
  public TestCaseContext getWrappedTestCastContext() {
    return this;
  }

  /**
   *
   * @return
   */
  public TestCaseParameters parameters() {
    return testCaseParameters;
  }

  /**
   *
   * @param testCaseParameters
   */
  public void setTestCaseParameters(TestCaseParameters testCaseParameters) {
    this.testCaseParameters = testCaseParameters;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   *
   * @return
   */
  public int getCurrentAttempt() {
    return currentAttempt;
  }

  /**
   *
   * @return
   */
  @Override
  public TestCaseContext retry() {
    this.getStepLogger().log("RETRYING");
    driver = null; // If we retry, then we must get a new driver becasue the driver was quit or dismissed.        
    currentAttempt++;
    return this;
  }

  /**
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *
   * @return
   */
  public ITestResult getTestResult() {
    return testResult;
  }

  /**
   *
   * @param testResult
   */
  public void setTestResult(ITestResult testResult) {
    this.testResult = testResult;
  }

  /**
   *
   * @return
   */
  public String getTestName() {
    return testResult.getTestClass().getName() + "." + testResult.getMethod().getMethodName();
  }

  /**
   *
   * @return
   */
  @Override
  public ArrayList<String> getTags() {
    ArrayList<String> tags = new ArrayList<>();
//    if (testResult != null) {
//      tags.add(testResult.getTestClass().getName() + "." + testResult.getMethod().getMethodName());
//    }
    tags.addAll(parameters().getTags());
    return tags;
  }

  /**
   *
   * @param tag
   */
  @Override
  public void addTag(String tag) {
    tags.add(tag);
  }

  /**
   *
   * @return
   */
  public DesiredCapabilities toDesiredCapabilities() {
    DesiredCapabilities caps = testCaseParameters.toDesiredCapabilities();
    caps.setCapability("name", name);
    return caps;
  }

  /**
   *
   * @param env
   */
  public void assertEnvironment(EnvironmentType env) {
    if (parameters().getEnvironment().getEnvironmentType() != env) {
      throw new SkipException("This test is restricted to run in " + env);
    }
  }

}
