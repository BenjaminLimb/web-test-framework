package org.syftkog.web.automation.framework.test;

import org.syftkog.web.test.framework.TestCaseParameters;
import org.syftkog.web.test.framework.DriverFactory;
import org.syftkog.web.test.framework.Driver;
import org.syftkog.web.test.framework.BrowserVersionPlatform;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author BenjaminLimb
 */
public class DriverFactoryConcurrencyTest {

  /**
   *
   * @throws InterruptedException
   */
  @Test(groups = {"concurrency", "unit"})
    public void driverConcurrencyBasics() throws InterruptedException {
        final DriverFactory driverFactory = new DriverFactory(1).setRecylceDrivers(Boolean.FALSE);

        ExecutorService execService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 20; i++) {
            execService.execute(new Runnable() {
                @Override
                public void run() {
                    TestCaseParameters params = new TestCaseParameters();
                    params.setBrowserName("mock");
                    //System.out.println("Initialize:" + Thread.currentThread().getId());
                    Driver driver = driverFactory.getDriver(BrowserVersionPlatform.MOCK);
                    //System.out.println("Start:" + Thread.currentThread().getId());
                    driver.navigate().to("https://google.com");
                    driver.navigate().to("https://twitter.com/");
                    //System.out.println("End:" + Thread.currentThread().getId());
                    driver.recycle();
                }
            });
        }
        execService.shutdown();
        execService.awaitTermination(120, TimeUnit.SECONDS);

        driverFactory.quitAll();
    }

  /**
   *
   * @throws InterruptedException
   */
  @Test(groups = {"concurrency", "unit"})
    public void driverConcurrencyDriversQuitAfterNotInuse() throws InterruptedException {
        final DriverFactory driverFactory = new DriverFactory(100).setRecylceDrivers(Boolean.TRUE);
        ExecutorService execService = Executors.newCachedThreadPool();

        final Set<Long> ids = new HashSet<>();

        // Every five seconsds create a driver.
        for (int i = 0; i < 2; i++) {
            execService.execute(new Runnable() {
                @Override
                public void run() {
                    ids.add(Thread.currentThread().getId());
                    Driver driver = driverFactory.getDriver(BrowserVersionPlatform.MOCK);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DriverFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    driver.recycle();
                }
            });
        }

        try {
            Field f = driverFactory.getClass().getDeclaredField("instantiatedDrivers");
            f.setAccessible(true);
            ConcurrentHashMap<Integer, Driver> instantiatedDrivers = (ConcurrentHashMap<Integer, Driver>) f.get(driverFactory);

            Thread.sleep(1000);
            System.out.println("Size at 1 second:" + instantiatedDrivers.size());

            Assert.assertTrue(instantiatedDrivers.size() == 2);
            Thread.sleep(15000);
            System.out.println("Size at 15 seconds:" + instantiatedDrivers.size());
            Assert.assertTrue(instantiatedDrivers.size() == 0);

            Assert.assertTrue(instantiatedDrivers.isEmpty());

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(DriverFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        execService.shutdown();
        execService.awaitTermination(120, TimeUnit.SECONDS);

        driverFactory.quitAll();
    }

}
