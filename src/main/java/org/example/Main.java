package org.example;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;

import com.applitools.eyes.visualgrid.BrowserType;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.applitools.eyes.visualgrid.model.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

    public static void main(String[] args) {

        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless", "true")
        );

        ChromeOptions options = new ChromeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
            System.out.println("Running in HEADLESS mode");
        } else {
            System.out.println("Running in HEADED mode");
        }

        WebDriver driver = new ChromeDriver(options);

        // UFG Runner
        VisualGridRunner runner = new VisualGridRunner(5);
        Eyes eyes = new Eyes(runner);

        BatchInfo batch = new BatchInfo("Selenium Java UFG");
        //batch.setId(System.getenv("APPLITOOLS_BATCH_ID"));

        Configuration config = new Configuration();
        config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        // Multi-browser setup
        config.addBrowser(1200, 800, BrowserType.CHROME);
        config.addBrowser(1200, 800, BrowserType.FIREFOX);

        //IOS
        config.addDeviceEmulation(DeviceName.iPhone_14_Plus, ScreenOrientation.PORTRAIT);

        //Android
        config.addDeviceEmulation(DeviceName.Pixel_5, ScreenOrientation.PORTRAIT);

        //config.setBranchName("feature-login-flow-2");
        //config.setParentBranchName("feature-login-flow");

        /*
                First run (no baseline in your branch)
                -> config.setParentBranchName("main");
                -> Uses main’s baseline as reference
                -> Creates a new baseline in feature-login-flow
                -> If main branch does not exist, then compared against default

                Subsequent runs
                -> baseline already exists in feature-login-flow
                -> It ignores parentBranchName
                -> Compares against branch’s own baseline

                For real workflow
                --> First run on main
                --> Then create feature branch
                --> ensures proper baseline inheritance
         */

        eyes.setConfiguration(config);

        try {
            eyes.open(
                    driver,
                    "Applitools Demo App",
                    "Login Page Test - UFG",
                    new RectangleSize(1200, 800)
            );

            driver.get("https://demo.applitools.com/");

            eyes.check("Login Page", Target.window().fully());

            driver.findElement(By.id("username")).sendKeys("demo");
            driver.findElement(By.id("password")).sendKeys("demo123");
            driver.findElement(By.id("log-in")).click();

            eyes.check("User successfully logs in and dashboard is displayed", Target.window().fully());

            eyes.close();

        } finally {

            eyes.abortIfNotClosed();

            runner.getAllTestResults(false);

            driver.quit();
        }
    }
}