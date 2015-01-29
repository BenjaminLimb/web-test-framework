package org.syftkog.web.test.framework;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 *
 * @author BenjaminLimb
 */
public class TestCaseListener implements ITestListener {

    private static TestCaseContext getTestCaseContext(ITestResult tr) {
        Object[] parameters = tr.getParameters();
        for (Object o : parameters) {
            if (o instanceof TestCaseContext) {
                return (TestCaseContext) o;
            }
        }
        return null;
    }

    /**
     *
     * @param itr
     * @return
     */
    public String getName(ITestResult itr) {
        TestCaseContext context = getTestCaseContext(itr);

        if (context != null) {
            return context.getName();
        } else {
            return null;
        }
    }

    /**
     * Transform TestCaseParameters into TestCaseContexts
     *
     * @param itr
     */
    @Override
    public void onTestStart(ITestResult itr) {
        Object[] parameters = itr.getParameters();
        TestCaseContext context = null;

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof TestCaseParameters) {
                TestCaseParameters params = (TestCaseParameters) parameters[i];
                context = new TestCaseContext(params);
                context.setName(itr.getTestClass().getName() + "." + itr.getMethod().getMethodName());
                context.setTestResult(itr);
                parameters[i] = context;
            }
        }

        if (context != null) {
            context.getStepLogger().log("STARTING TEST " + getName(itr));
        }
    }

    /**
     *
     * @param itr
     */
    @Override
    public void onTestSuccess(ITestResult itr) {
        TestCaseContext context = getTestCaseContext(itr);
        if (context != null) {
            context.getStepLogger().log("TEST SUCCESS " + getName(itr));
        }
    }

    /**
     *
     * @param itr
     */
    @Override
    public void onTestFailure(ITestResult itr) {
        TestCaseContext context = getTestCaseContext(itr);
        if (context != null) {
            if (context.isDriverInitialized()) {
                Driver driver = context.getDriver();
                try {
                    String currentUrl = driver.getCurrentUrl();
                    context.getStepLogger().log("ERROR ON " + currentUrl);

                } catch (Exception ex) {

                };
            }
            context.getStepLogger().log("ERROR " + itr.getThrowable().getMessage());
            context.getStepLogger().log("TEST FAILURE " + getName(itr));
        }
    }

    /**
     *
     * @param itr
     */
    @Override
    public void onTestSkipped(ITestResult itr) {
        itr.getThrowable().printStackTrace();
        TestCaseContext context = getTestCaseContext(itr);
        if (context != null) {
            context.getStepLogger().log("TEST SKIPPED " + getName(itr));
        }
    }

    /**
     *
     * @param itr
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult itr) {
        TestCaseContext context = getTestCaseContext(itr);
        if (context != null) {
            context.getStepLogger().log("TEST FAILED BUT WITH SUCCESS PERCENTAGE " + getName(itr));
        }
    }

    /**
     *
     * @param itc
     */
    @Override
    public void onStart(ITestContext itc) {

    }

    /**
     *
     * @param itc
     */
    @Override
    public void onFinish(ITestContext itc) {

    }

}
