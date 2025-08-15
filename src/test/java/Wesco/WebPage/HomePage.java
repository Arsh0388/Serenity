package Wesco.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    // we are going to check the filter options
    // define the locators on home page and identify the View All categories button
    // initialize webdriver
    WebDriver driver;
    WebDriverWait waitUntil;

    public HomePage(WebDriver receiveDriver) {
        // initialize constructor with driver
        driver = receiveDriver;
        waitUntil = new WebDriverWait(driver, Duration.ofSeconds(4)); // explicit wait
    }
    private By viewAllCategoriesButton = By.cssSelector(
            ".c-refinement-spotlight.c-refinement-spotlight--grid .view-more a.button.button-secondary.button-small" // multiple classes attached together
            );
    // locate the nested button to view all categories
    private By branchLocatorLearnMore = By.cssSelector(
            ".c-content-block" +
            ".c-content-block__cta" +
            " a.button-transparent");

    public void clickOnViewAllCategories() {
        // wait for the page to get loaded
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
                driver.findElement(viewAllCategoriesButton)
        );
        waitUntil.until(ExpectedConditions.visibilityOfElementLocated(viewAllCategoriesButton))
                .click(); // wait until the button is visible; avoids flaky test

    }
    public void clickOnBranchLocatorLearnMore() {
        waitUntil.until(ExpectedConditions.visibilityOfElementLocated(branchLocatorLearnMore))
                .click(); // wait to branch locator. to be visible.
    }
}
