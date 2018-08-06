package apitestsuites;

import commonutilities.LogConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite Class
 */
@RunWith(Suite.class)
@SuiteClasses({
        UnitTest.class})
public class AllTests extends LogConfig {

    @BeforeClass
    public static void beforeClass() {
        LogConfig.getLogger().info("Starting Suite");
    }

    @AfterClass
    public static void afterClass() {
        LogConfig.getLogger().info("Done with the Suite");
    }

}
