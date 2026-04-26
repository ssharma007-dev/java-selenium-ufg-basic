package tests;

import base.BaseTest;
import pages.LoginPage;
import pages.DashboardPage;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.fluent.Target;

import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

    @Test
    void loginTest() {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        eyes.open(
                driver,
                "Applitools Demo App",
                "JUnit POM Login Test",
                new RectangleSize(1200, 800)
        );

        loginPage.open();

        eyes.check("Login Page", Target.window().fully());

        loginPage.login("demo", "demo123");

        eyes.check("Dashboard Page", Target.window().fully());

        eyes.close();
    }
}