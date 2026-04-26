package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    By balance = By.id("amount");

    public boolean isLoaded() {
        return driver.findElement(balance).isDisplayed();
    }
}