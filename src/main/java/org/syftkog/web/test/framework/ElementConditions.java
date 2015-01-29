/*
 * Copyright 2015 benjaminlimb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syftkog.web.test.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 *
 * @author benjaminlimb
 */
public class ElementConditions {

  /**
   * An expectation for checking if the element is enabled.
   *
   * @param el
   * @return
   */
  public static ExpectedCondition<Boolean> isEnabled(final Element el) {
    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return el.isEnabled();
      }
    };
  }

  /**
   * An expectation for checking if the element is not enabled.
   *
   * @param el
   * @return
   */
  public static ExpectedCondition<Boolean> isNotEnabled(final Element el) {
    return ExpectedConditions.not(isEnabled(el));
  }

  /**
   * An expectation for checking if the element text as a number is greater than
   * the given number.
   *
   * @param el
   * @param number
   * @return
   */
  public static ExpectedCondition<Boolean> isGreaterThan(final Element el, final Long number) {
    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return el.getNumberAsLong() > number;
      }
    };
  }

  /**
   * An expectation for checking if the element text as a number is less than
   * the given number.
   *
   * @param el
   * @param number
   * @return
   */
  public static ExpectedCondition<Boolean> isLessThan(final Element el, final Long number) {
    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        return el.getNumberAsLong() < number;
      }
    };
  }

  /**
   * An expectation for checking if the given text is present in the specified
   * element or element value attribute. (Case insensitive)
   *
   * @param el
   * @param text
   * @return
   */
  public static ExpectedCondition<Boolean> textContains(final Element el, final String text) {
    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        String textToContain = text.toLowerCase();

        String elementText = el.getText();
        if (elementText != null) {
          elementText = elementText.toLowerCase();
          if (elementText.contains(textToContain)) {
            return true;
          }
        }

        String elementValueText = el.getAttribute("value");
        if (elementValueText != null) {
          elementValueText = elementValueText.toLowerCase();
          if (elementValueText.contains(textToContain)) {
            return true;
          }
        }

        return false;
      }

    };
  }

  /**
   * An expectation for checking if the given text not is present in the
   * specified element nor element value attribute. (Case insensitive)
   *
   * @param el
   * @param text
   * @return
   */
  public static ExpectedCondition<Boolean> textDoesNotContain(final Element el, final String text) {
    return ExpectedConditions.not(textContains(el, text));
  }

  /**
   * An expectation for checking if the given text is equal to the specified
   * element or element value attribute. (Case insensitive)
   *
   * @param el
   * @param text
   * @return
   */
  public static ExpectedCondition<Boolean> textEqualsIgnoreCase(final Element el, final String text) {
    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {

        String textExpected = text.toLowerCase();

        String elementText = el.getText();
        if (elementText != null) {
          elementText = elementText.toLowerCase();
          if (elementText.equalsIgnoreCase(textExpected)) {
            return true;
          }
        }

        String elementValueText = el.getAttribute("value");
        if (elementValueText != null) {
          elementValueText = elementValueText.toLowerCase();
          if (elementValueText.equalsIgnoreCase(textExpected)) {
            return true;
          }
        }
        
        //TODO: TEST THE IMPACT OF THIS ADDITION
        String elementSelectValue = el.getSelectboxValue();
        if (elementSelectValue != null) {
          elementValueText = elementValueText.toLowerCase();
          if (elementValueText.equalsIgnoreCase(textExpected)) {
            return true;
          }
        }
        
        
        return false;
      }

    };
  }

  /**
   *
   * @param el
   * @param text
   * @return
   */
  public static ExpectedCondition<Boolean> textDoesNotEqualIgnoreCase(final Element el, final String text) {
    return ExpectedConditions.not(textEqualsIgnoreCase(el, text));
  }
}
