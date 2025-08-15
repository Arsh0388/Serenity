package Wesco.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AbrasiveBeltsProduct {
    List<WebElement> products = new ArrayList<>();

    WebDriver driver;
    WebDriverWait wait;
    int number = 1;
    // not required => a lot more complex
    // String basicPath = "c-product-search-result.c-product-search-result--sku:nth-child(" + number +") div.c-product-search-result__header a.c-product-search-result__name:nth-child";
    // Add these fields near your other locators
    private final By downloadsSection = By.id("Downloads");
    private final By downloadLinks = By.cssSelector("#Downloads ul li a");
    // simple and plain
    private final By productLinks = By.cssSelector(
            ".c-product-search-result.c-product-search-result--sku .c-product-search-result__header a.c-product-search-result__name"
    );
    public AbrasiveBeltsProduct(WebDriver receiveDriver) {
        driver = receiveDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(4)); // explicit wait
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
    public List<WebElement> getProducts(int ignoredTotalProducts) {
        // No brittle nth-child loop; just collect what exists
        // use presence of AllElementLocatedBy to get all the elements .
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productLinks));
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
            wait.until(ExpectedConditions.elementToBeClickable(a)).click();
        }
        System.out.println("Clicked " + links.size() + " download link(s).");
        return links.size();
    }


}
