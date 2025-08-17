package Wesco.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CategoriesPage {
    WebDriver driver;
    WebDriverWait wait;

    // these same
//    private final By sectionAbrasivesAndPolishing = By.cssSelector(
//                "div.c-refinement-spotlight__refinements > a.refinement:nth-of-type(1)"); // selects the direct first  child .
//
//    private final By sectionAudioAndVisual = By.cssSelector(
//                "div.c-refinement-spotlight__refinements > a.refinement:nth-of-type(2)"); // selects the direct second child
//
//    private final By sectionAutomationAndControl = By.cssSelector(
//            "div.c-refinement-spotlight__refinements > a.refinement:nth-of-type(3)"); // selects the direct third child

    // now provide the functions through which we can interact with these

    // get all the provided elements
    private By categoriesLocator  = By.cssSelector("div.c-refinement-spotlight__refinements > a.refinement");
    private List<WebElement> categories;
    private By searchCategoriesItemLocator = By.cssSelector("a.refinement"); // a tag with class refinement loads all the elements
    private List<WebElement> categoriesItems;
    // select the search button and enter text
    private By searchField = By.name("q"); // q should be unique.
    // get the list of   all elements
    private By searchButton = By.className("c-search-field__button");
    public CategoriesPage(WebDriver webDriver) {
        driver = webDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        categories = driver.findElements(categoriesLocator);
        categoriesItems = driver.findElements(searchCategoriesItemLocator); // locates all the elements
    }

    private void click(int index) {
        // scroll into view optional but, it avoids flaky tests =>
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", categories.get(index)); // scroll into the center point of the view
        wait.until(ExpectedConditions.elementToBeClickable(categories.get(index))).click();
    }
    public void clickSectionAbrasivesAndPolish() {
        click(0);
    }

    public void clickSectionAudioAndVisual() {
        click(1);
    }

    public void clickSectionAutomationAndControl() {
        click(2);
    }

    public void enterSearchField(String search) {
        driver.findElement(searchField).sendKeys(search);
        driver.findElement(searchButton).click();

    }


    public void selectSearchFieldProducts(int index) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoriesItemLocator,index));
        categoriesItems = driver.findElements(searchCategoriesItemLocator);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});",categoriesItems.get(index));  //
        wait.until(ExpectedConditions.elementToBeClickable(categoriesItems.get(index))).click();
    }

}
