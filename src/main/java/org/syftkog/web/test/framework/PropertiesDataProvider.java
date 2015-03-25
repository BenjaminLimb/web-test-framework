package org.syftkog.web.test.framework;

import java.util.HashSet;
import java.util.Set;
import org.testng.annotations.DataProvider;

/**
 *
 * @author BenjaminLimb
 */
public class PropertiesDataProvider {

  /**
   *
   * @return
   */
  @DataProvider(parallel = true)
  public static Object[][] getTestConfigurations() {
    Set<TestCaseParameters> configurationsToTest = getTestCaseParametersSet();
    return DataProviderHelper.transformDataSetIntoSingleParameterDataProvider(configurationsToTest);
  }

  /**
   *
   * @return
   */
  public static Set<TestCaseParameters> getTestCaseParametersSet() {
    try {
      Set<TestCaseParameters> returnSet = new HashSet<>();

      String[] browserList = PropertiesRetriever.getString("propertiesDataProvider.browsersToTest", "ff").split(",");
      String[] languageList = PropertiesRetriever.getString("propertiesDataProvider.languagesToTest", "en").split(",");
      String[] windowDimensions = PropertiesRetriever.getString("propertiesDataProvider.windowDimensionsToTest", "").split(",");

      String environmentType = PropertiesRetriever.getString("propertiesDataProvider.environment", null);
      String authenticate = PropertiesRetriever.getString("propertiesDataProvider.authenticate", null);
      
      String auth_user = PropertiesRetriever.getString("propertiesDataProvider.authentication.username", null);
      String auth_pass = PropertiesRetriever.getString("propertiesDataProvider.authentication.password", null);
      String auth_key = PropertiesRetriever.getString("propertiesDataProvider.authentication.key", null);

      for (String browserVersionPlatformKey : browserList) {
        for (String languageCode : languageList) {
          for (String windowDimension : windowDimensions) {
            TestCaseParameters params = new TestCaseParameters();
            params.setBrowserVersionPlatform(BrowserVersionPlatform.fromKey(browserVersionPlatformKey));

            if (languageCode.length() > 1) {
              Language language = Language.getFromCode(languageCode);
              params.setLanguage(language);
            }

            if (windowDimension.toLowerCase().contains("x")) {
              params.setWindowSize(SeleniumHelperUtil.toDimension(windowDimension));
            }

            if (environmentType != null) {
              params.setEnvironment(EnvironmentType.valueOf(environmentType.toUpperCase()).load());
            }

            if (authenticate != null) {
              params.setAuthenticate(authenticate.equalsIgnoreCase("true"));
            }
             if (auth_user != null) {               
               Authentication auth = new Authentication(auth_user,auth_pass);
               auth.setKey(auth_key);
               
               params.setAuthentication(auth);              
            }

            returnSet.add(params);
          }
        }
      }
      return returnSet;
    } catch (RuntimeException ex) { // Since the data provider doesn't have a good way to track exceptions that are thrown. Print anything that comes up to the console.
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }
}
