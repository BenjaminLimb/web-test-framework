package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.TestCaseContext;
import org.syftkog.web.test.framework.TestCaseParameters;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
//@Listeners(TestCaseContextInjectorListener.class)
public class TestCaseContextInjectorListenerTest {

  /**
   *
   * @return
   */
  @DataProvider
    public Object[][] getData() {
        Object[][] data = new Object[1][1];

        TestCaseParameters params = new TestCaseParameters();
        params.setBrowserVersionPlatform(BrowserVersionPlatform.MOCK);

        data[0][0] = params;
        
        return data;
    }

   // @Test(dataProvider="getData")

  /**
   *
   * @param context
   */
      public void testTestCaseContextInjectorListener(TestCaseContext context){
        System.out.println(context);
    }
}
