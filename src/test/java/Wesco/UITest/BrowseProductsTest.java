package Wesco.UITest;

import Wesco.WebPage.AbrasiveBeltsAndWireWheels;
import Wesco.WebPage.Products;
import Wesco.WebPage.CategoriesPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

@ExtendWith(HandleFailureClass.class)
public class BrowseProductsTest extends BaseClass{

    private CategoriesPage categoriesPage;

    private AbrasiveBeltsAndWireWheels abrasiveBeltsAndWireWheels;
//    @Test
//    public void testBrowseProducts() throws InterruptedException {
//        homePage.clickOnViewAllCategories();
//        // WAIT FOR 5 seconds
//        Thread.sleep(2000); // put the thread to sleep to se what's actually happening
//        categoriesPage = new CategoriesPage(driver);
//        categoriesPage.clickSectionAbrasivesAndPolish();
//        Thread.sleep(3000);
//
//    }
    @Disabled()
    @ParameterizedTest
    @ValueSource(ints = {1})
    public void testAbrasiveAndWheels(int productIndex) throws InterruptedException {
        homePage.clickOnViewAllCategories();
        // WAIT FOR 5 seconds
        Thread.sleep(2000); // put the thread to sleep to se what's actually happening
        categoriesPage = new CategoriesPage(driver);
        categoriesPage.clickSectionAbrasivesAndPolish();
        Thread.sleep(3000);
        // now initialize an object of abrasive wheel class
        abrasiveBeltsAndWireWheels = new AbrasiveBeltsAndWireWheels(driver);
        // now click on wireWheels section
        abrasiveBeltsAndWireWheels.clickWireWheels(); //

    }

    // browse abrasive
    @Disabled("Temporarily turned off until bug #123 is fixed")
    @Test
    public void testAbrasiveAndPolish() throws InterruptedException {
        homePage.clickOnViewAllCategories();
        // WAIT FOR 5 seconds
        Thread.sleep(2000); // put the thread to sleep to se what's actually happening
        categoriesPage = new CategoriesPage(driver);
        categoriesPage.clickSectionAbrasivesAndPolish();
        Thread.sleep(3000);
        // now initialize an object of abrasive wheel class
        abrasiveBeltsAndWireWheels = new AbrasiveBeltsAndWireWheels(driver);
        // now click on wireWheels section
        abrasiveBeltsAndWireWheels.clickAbrasiveBelts(); //
        Thread.sleep(3000); // wait to see elements;


        // browse elements
        Products abrasiveBeltsProduct = new Products(driver);
        // get totalCount,
        int totalCount = abrasiveBeltsProduct.getTotalResults();
        // load all th elements in the list.
        List<WebElement> products = abrasiveBeltsProduct.getProducts(totalCount);

        // iterate through each element and check if downloadable file is present then download else skip.
        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);

            // Each product link should have an href to navigate
            String url = product.getAttribute("href");
            if (url == null || url.isBlank()) {
                // Fallback: click the element to navigate if no href attribute
                ((JavascriptExecutor) driver).executeScript( // javascript executor is an interface..
                        "arguments[0].scrollIntoView({block:'center'});", product);
                product.click();
            } else {
                driver.navigate().to(url);
            }

            // Now on product detail page: click all download links (if present)
            abrasiveBeltsProduct.clickAllDownloadsIfPresent();

            // Go back to the results to continue (if needed)
            driver.navigate().back();

            // Reacquire elements after navigation (DOM refreshed)
            products = abrasiveBeltsProduct.getProducts(totalCount);
        }
    }

    // use search input field to search items and browse through them
    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void testSearchproducts(int count) throws InterruptedException {
        homePage.clickOnViewAllCategories(); // navigate to all categories
        // enter into search field
        categoriesPage = new CategoriesPage(driver);
        categoriesPage.enterSearchField("wheels");
        // provides all the options to select any field and browse products
        categoriesPage.selectSearchFieldProducts(2);
        // navigates to products list
        // now we need to check products
        Thread.sleep(2000);
        Products products = new Products(driver);
        // now get all the products and search for the very first one
        int totalCount = products.getTotalResults();
        List<WebElement> items = products.getProducts(totalCount); // only get two products and click on. them
        System.out.println(items.size());
        for (int i = 0; i < items.size(); i++) {
            // get the specific element

            WebElement item = items.get(i);
            // lets perform this a little bit seperately
            String url = item.getAttribute("href");
            WebElement oldFirst = items.get(0); // remember first for staleness check
            if (url == null || url.isBlank()) {
                // Fallback: click the element to navigate if no href attribute
                ((JavascriptExecutor) driver).executeScript( // javascript executor is an interface..
                        "arguments[0].scrollIntoView({block:'center'});", item);
                item.click();
            } else {
                driver.navigate().to(url);
            }
            // one we click on item we get navigated to different site so navigate back
            driver.navigate().back();
            items = products.getProducts(totalCount);
        }
    }


}
