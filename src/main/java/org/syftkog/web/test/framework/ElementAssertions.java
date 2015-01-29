package org.syftkog.web.test.framework;

import org.testng.Assert;

/**
 *
 * @author BenjaminLimb
 * @param <T>
 */
public class ElementAssertions<T extends Element> {

  /**
   * The time to retry an action before timing out.
   */
  public static final long IMPLICIT_WAIT_TIME_IN_SECONDS = Long.parseLong(PropertiesRetriever.getString("element.implicitWaitTimeInSeconds", "10"));

  private final T el;

  /**
   * 
   * @param el - An Element
   */
  public ElementAssertions(T el) {
    this.el = el;
  }

  /**
   *
   * @param <E> 
   * @param el
   * @return
   */
  public static <E extends Element> ElementAssertions that(E el) {
    return new ElementAssertions<>(el);
  }

  /**
   * 
   * @return
   */
  public T isEnabled() {
    el.logStep("ASSERT: " + el.toString() + " is enabled.");
    ElementWaits.until(el).isEnabled(IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  /**
   *
   * @return
   */
  public T isNotEnabled() {
    el.logStep("ASSERT: " + el.toString() + " is not enabled.");
    ElementWaits.until(el).isNotEnabled(IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  /**
   *
   * @param number
   * @return
   */
  public T isGreaterThan(Long number) {
    el.logStep("ASSERT: " + el.toString() + " is greater than [" + number +"]");
    ElementWaits.until(el).isGreaterThan(number,IMPLICIT_WAIT_TIME_IN_SECONDS);
    
    return el;
  }

  /**
   *
   * @param number
   * @return
   */
  public T isLessThan(Long number) {
    el.logStep("ASSERT: " + el.toString() + " is less than [" + number + "] Actual:" + el.getNumberAsLong());
    ElementWaits.until(el).isLessThan(number,IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  /**
   *
   * @param expectedText
   * @return
   */
  public T containsText(String expectedText) {
    el.logStep("ASSERT: " + el.toString() + " contains [" + expectedText + "]");
    ElementWaits.until(el).containsText(expectedText, IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  /**
   *
   * @param expectedText
   * @return
   */
  public T doesNotContainText(String expectedText) {
    el.logStep("ASSERT: " + el.toString() + " does not contain [" + expectedText + "]");
    ElementWaits.until(el).doesNotContainsText(expectedText, IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  //TODO: make a more meainingful error message.
  /**
   *
   * @param expectedText
   * @return
   */
  public T equals(String expectedText) {
    el.logStep("ASSERT: " + el.toString() + " equals [" + expectedText + "]");
    ElementWaits.until(el).isEqualTo(expectedText, IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

   /**
   *
   * @param expectedText
   * @return
   */
  public T doesNotEqual(String expectedText) {
    el.logStep("ASSERT: " + el.toString() + " equals [" + expectedText + "]");
    ElementWaits.until(el).isNotEqualTo(expectedText, IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }
  
  
  /**
   *
   * @return
   */
  public T isSelected() {
    el.logStep("ASSERT: " + el.toString() + " is selected.");
    ElementWaits.until(el).isSelected(IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  /**
   *
   * @return
   */
  public T isNotSelected() {
    el.logStep("ASSERT: " + el.toString() + " is not selected.");
    ElementWaits.until(el).isNotSelected(IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  /**
   *
   * @param booleanValue
   * @return
   */
  public T selectedStateIs(boolean booleanValue) {
    el.logStep("ASSERT: " + el.toString() + " selected state is " + booleanValue + ".");
    if (booleanValue) {
      ElementWaits.until(el).isSelected(IMPLICIT_WAIT_TIME_IN_SECONDS);
    } else {
      ElementWaits.until(el).isNotSelected(IMPLICIT_WAIT_TIME_IN_SECONDS);
    }
    return el;
  }

  //TODO: make a more meaningful error message.
  /**
   *
   * @return
   */
  public T isDisplayed() {
    el.logStep("ASSERT: " + el.toString() + " is displayed.");
    ElementWaits.until(el).isDisplayed(IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  //TODO: catch not found Exceptions!!!!!!!!!!!
  /**
   *
   * @return
   */
  public T isNotDisplayed() {
    el.logStep("ASSERT: " + el.toString() + " is not displayed.");
    ElementWaits.until(el).isNotDisplayed(IMPLICIT_WAIT_TIME_IN_SECONDS);
    return el;
  }

  /**
   *
   * @param selectedValue
   * @return
   */
  public T selectboxEqualsValue(String selectedValue) {
    el.logStep("ASSERT: " + el.toString() + " Selectbox value.");
    String actualValue = el.getSelectboxValue();
    Assert.assertEquals(actualValue, selectedValue);
    return el;
  }

  /**
   *
   * @return
   */
  public T srcAttributeReturnsValidResponse() {
    el.logStep("ASSERT: " + el.toString() + " src attribute returns valid response.");
    String src = el.getAttribute("src");
    int responseCode = GeneralUtils.getResponseCode(src);
    Assert.assertTrue(responseCode < 400, "Expected < 400. Response was " + responseCode);
    return el;
  }
  
  /**
   * DO NOT USE
   * @deprecated 
   * @param obj
   * @return 
   */
  @Override
  public boolean equals(Object obj){
    throw new RuntimeException("This method is not supported.");
  }

  
}
