package org.syftkog.web.automation.framework.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

  @Test(groups = "unit")
  public void testSerialize() {
    TestCaseParameters params = new TestCaseParameters();
    params.setPlatform("firefox");
    Gson gson = new GsonBuilder().create();
    String toJson = gson.toJson(params);
    System.out.println(toJson);         
  }

}
