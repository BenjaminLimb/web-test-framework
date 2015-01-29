package org.syftkog.web.test.framework;


/**
 *
 * @author BenjaminLimb
 */
public class TestCaseContextCarrier implements HasTestCaseContext {

    private TestCaseContext testCaseContext;

  /**
   *
   * @return
   */
  @Override
    public TestCaseContext getTestCaseContext() {
        return testCaseContext;
    }

  /**
   *
   * @param testCaseContext
   */
  public void setTestCaseContext(TestCaseContext testCaseContext) {
        this.testCaseContext = testCaseContext;
    }

}
