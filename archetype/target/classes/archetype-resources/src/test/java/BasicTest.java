package ${package};

import org.syftkog.web.test.framework.PropertiesDataProvider;
import org.syftkog.web.test.framework.TestCaseContext;
import org.testng.annotations.Test;


/**
 * An empty test
 * 
 */


public class BasicTest {
  
  @Test(groups = {""}, dataProvider = "getTestConfigurations", dataProviderClass = PropertiesDataProvider.class)
  public void testExample(TestCaseContext context) {
    
    GoogleHomePage page = new GoogleHomePage(context);
    page.load();
     
  }
}
