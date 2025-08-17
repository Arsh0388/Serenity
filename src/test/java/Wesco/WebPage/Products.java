package Wesco.WebPage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Products {

    WebDriver driver;
    WebDriverWait wait;

    // not required => a lot more complex
    // String basicPath = "c-product-search-result.c-product-search-result--sku:nth-child(" + number +") div.c-product-search-result__header a.c-product-search-result__name:nth-child";

    // Add these fields near your other locators
    private final By downloadsSection = By.id("Downloads");
    private final By downloadLinks    = By.cssSelector("#Downloads ul li a");

    // simple and plain
    private final By productLinks = By.cssSelector(
            ".c-product-search-result.c-product-search-result--sku .c-product-search-result__header a.c-product-search-result__name"
    );

    public Products(WebDriver receiveDriver) {
        driver = receiveDriver;
        wait   = new WebDriverWait(driver, Duration.ofSeconds(10)); // explicit wait
    }

    //
    public int getTotalResults() {
        WebElement resultCountElement = driver.findElement(By.cssSelector("div.result-count"));
        String resultText = resultCountElement.getText();

        // Extract only the number using regex
        String numberOnly = resultText.replaceAll("\\D+", ""); // removes all non-digits
        int count = Integer.parseInt(numberOnly);

        return count;
    }

    // No brittle nth-child loop; just collect what exists
    // use presenceOfAllElementsLocatedBy to get all the elements .
    public List<WebElement> getProducts(int ignoredTotalProducts) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productLinks));
    }

    // Click the Nth product robustly (re-find + refreshed + JS fallback)
    public void clickProductByIndex(int index) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(productLinks, index));
        List<WebElement> items = driver.findElements(productLinks);
        WebElement target = items.get(index);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", target);

        WebElement clickable = wait.until(
                ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(target))
        );

        try {
            clickable.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickable);
        }
    }

    public int clickAllDownloadsIfPresent() {
        if (driver.findElements(downloadsSection).isEmpty()) {
            System.out.println("No Downloads section on this product.");
            return 0;
        }
        List<WebElement> links = driver.findElements(downloadLinks);
        for (WebElement a : links) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", a);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(a))).click();
        }
        System.out.println("Clicked " + links.size() + " download link(s).");
        return links.size();
    }
}
