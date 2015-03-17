package org.syftkog.web.automation.framework.test;


import org.syftkog.web.test.framework.TestCaseParameters;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class TestCaseParametersTest {

  /**
   *
   */
  @Test(groups = "unit")
    public void testCreateTestParameters() {
        TestCaseParameters params = new TestCaseParameters();
        params.setPlatform("firefox");

    }
}
