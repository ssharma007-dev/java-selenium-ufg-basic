package base;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    protected WebDriver driver;
    protected Eyes eyes;

    @BeforeEach
    void setup() {

        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        ChromeOptions options = new ChromeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);

        eyes = new Eyes(new ClassicRunner());

        BatchInfo batch = new BatchInfo("Java JUnit POM Demo");
        //batch.setId(System.getenv("APPLITOOLS_BATCH_ID")); //For CI/CD

        Configuration config = new Configuration();
        config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        config.setBatch(batch);

        eyes.setConfiguration(config);
    }

    @AfterEach
    void tearDown() {
        if (eyes != null) {
            eyes.abortIfNotClosed();
        }
        if (driver != null) {
            driver.quit();
        }
    }
}