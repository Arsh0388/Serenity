package Veeva;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class jobApplyScript {
    WebDriver driver;
    WebDriverWait wait;

    private By careersLink = By.cssSelector(" div.veeva-footer-2024__content-upper-right > div.veeva-footer-2024__content-upper-link-group:nth-child(5) > ul > li:nth-child(7) > a");
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        // initialize browser with incognito tab.
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));


        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3)); // wait for the page to get loaded.
        driver.get("https://www.veeva.com/"); // go to Veeva solutions .
        driver.manage().window().maximize();
    }

    @Test
    public void goToCareersPage() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", wait.until(ExpectedConditions.presenceOfElementLocated(careersLink)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(careersLink)).click();

    }
}
