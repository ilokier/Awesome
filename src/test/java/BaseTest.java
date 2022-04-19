import Configuration.AppProperties;
import Configuration.DriverHandle;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {
    private static Logger log = LoggerFactory.getLogger("BaseTest.class");
    protected static WebDriver driver;
    protected static DriverHandle driverHandle;
    private static AppProperties appProperties;

    @BeforeAll
    static void beforeAll() {
        appProperties = AppProperties.getInstance();
        driverHandle = new DriverHandle();
        driver = driverHandle.getDriver();
        log.info("<<<<<Driver initialized>>>");
        driver.get(System.getProperty("appUrl"));
    }

//    @AfterAll
//    static void quit() {
//        driver.quit();
//        log.debug("<<<<<<driver closed properly>>>>>");
//    }
}