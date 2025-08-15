package Wesco.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoriesPage {
    WebDriver driver;
    WebDriverWait wait;

    // these same
    private final By sectionAbrasivesAndPolishing = By.cssSelector(
                "div.c-refinement-spotlight__refinements > a.refinement:nth-of-type(1)"); // selects the direct first  child .

    private final By sectionAudioAndVisual = By.cssSelector(
                "div.c-refinement-spotlight__refinements > a.refinement:nth-of-type(2)"); // selects the direct second child

    private final By sectionAutomationAndControl = By.cssSelector(
            "div.c-refinement-spotlight__refinements > a.refinement:nth-of-type(3)"); // selects the direct third child

    // now provide the functions through which we can interact with these
    public CategoriesPage(WebDriver webDriver) {
        driver = webDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    private void click(By locator) {
        // scroll into view optional but, it avoids flaky tests =>
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", driver.findElement(locator)); // scroll into the center point of the view
        wait.until(ExpectedConditions.elementToBeClickable(sectionAbrasivesAndPolishing)).click();
    }
    public void clickSectionAbrasivesAndPolish() {
        click(sectionAbrasivesAndPolishing);
    }

    public void clickSectionAudioAndVisual() {
        click(sectionAudioAndVisual);
    }

    public void clickSectionAutomationAndControl() {
        click(sectionAutomationAndControl);
    }


}
