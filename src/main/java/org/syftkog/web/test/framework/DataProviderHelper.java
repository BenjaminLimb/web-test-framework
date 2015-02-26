package org.syftkog.web.test.framework;

import java.util.Set;

/**
 * This class assists in the creation of a TestNG Data Provider.
 * @author BenjaminLimb
 */
public class DataProviderHelper {

  /**
   * Transform a set of objects into a list feed through the DataProvider.
   * @param setA
   * @return
   */
  public static Object[][] transformDataSetIntoSingleParameterDataProvider(Set setA) {
        Object[][] objectToReturn = new Object[setA.size()][1];
        int pos = 0;
        for (Object obj : setA) {
            objectToReturn[pos++][0] = obj;
        }
        return objectToReturn;
    }

  /**
   * Permutate two sets of data objects used to feed the DataProvider. 
   * @param setA
   * @param setB
   * @return
   */
  public static Object[][] permutateDataSetsIntoTwoParameterDataProvider(Set setA, Set setB) {

        Object[][] returnObjects = new Object[setA.size() * setB.size()][2];
        int pos = 0;
        for (Object a : setA) {
            for (Object b : setB) {
                returnObjects[pos][0] = a;
                returnObjects[pos][1] = b;
                pos++;
            }
        }
        return returnObjects;
    }

}
