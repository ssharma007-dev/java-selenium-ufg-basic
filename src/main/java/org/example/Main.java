import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;

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

        Eyes eyes = new Eyes(new ClassicRunner());

        Configuration config = new Configuration();
        config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        //config.setBranchName("feature-login-flow-2"); //checkpoint branch, current test branch where results will be stored
        //config.setParentBranchName("feature-login-flow"); //baseline branch, baseline source branch for comparison. If it does not exist then falls back to default branch

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

       // eyes.setStitchMode(StitchMode.CSS); // CSS Scroll
       // eyes.setStitchMode(StitchMode.SCROLL); //Manual Scroll
       // eyes.setHostOS("Linux");

        try {
            eyes.open(
                    driver,
                    "Applitools Demo App",
                    "Login Page Test",
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
            driver.quit();
        }
    }
}