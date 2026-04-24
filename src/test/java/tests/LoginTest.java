package tests;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.*;

public class LoginTest {

    WebDriver driver;
    Eyes eyes;

    @BeforeMethod
    public void setup() {

        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        ChromeOptions options = new ChromeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);

        eyes = new Eyes(new ClassicRunner());

        Configuration config = new Configuration();
        config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        eyes.setConfiguration(config);
    }

    @Test
    public void loginTest() {

        eyes.open(
                driver,
                "Applitools Demo App",
                "Login Test (Classic)",
                new RectangleSize(1200, 800)
        );

        driver.get("https://demo.applitools.com/");

        // Login page check
        eyes.check("Login Page", Target.window().fully());

        // Perform login
        driver.findElement(By.id("username")).sendKeys("demo");
        driver.findElement(By.id("password")).sendKeys("demo123");
        driver.findElement(By.id("log-in")).click();

        // Dashboard check
        eyes.check("Dashboard Page", Target.window().fully());

        eyes.close();
    }

    @AfterMethod
    public void tearDown() {
        if (eyes != null) {
            eyes.abortIfNotClosed();
        }
        if (driver != null) {
            driver.quit();
        }
    }
}