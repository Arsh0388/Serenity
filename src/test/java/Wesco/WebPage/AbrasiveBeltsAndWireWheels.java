package Wesco.WebPage;

import net.serenitybdd.core.pages.WebElementResolverByLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbrasiveBeltsAndWireWheels {

    WebDriver driver;
    WebDriverWait wait;
    private By abrasiveBelts = By.cssSelector("div.c-refinement-spotlight__refinements > a.refinement:nth-of-type(1)"); //select first  a tag which is direct child of div

    private By wireWheels = By.cssSelector("div.c-refinement-spotlight__refinements > a.refinement:nth-of-type(2)"); // select second a tag which is direct child of div

    public AbrasiveBeltsAndWireWheels(WebDriver receiveDriver) {
        driver = receiveDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    public void click(By el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block : 'center'});", driver.findElement(el));
        wait.until(ExpectedConditions.visibilityOfElementLocated(el)).click();
    }

    public void clickAbrasiveBelts() {
        click(abrasiveBelts);
    }

    public void clickWireWheels() {
        click(wireWheels);
    }

}
