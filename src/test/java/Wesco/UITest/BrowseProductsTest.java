package Wesco.UITest;

import Wesco.WebPage.AbrasiveBeltsAndWireWheels;
import Wesco.WebPage.AbrasiveBeltsProduct;
import Wesco.WebPage.CategoriesPage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

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
    @ParameterizedTest
    @ValueSource(ints = {1})
    public void testAbrasiveAndPolish(int itemIndex) throws InterruptedException {
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
        AbrasiveBeltsProduct abrasiveBeltsProduct = new AbrasiveBeltsProduct(driver);
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
                ((JavascriptExecutor) driver).executeScript(
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
}
