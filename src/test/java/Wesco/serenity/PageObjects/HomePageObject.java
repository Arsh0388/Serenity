//package Wesco.serenity.PageObjects;
//
//import net.serenitybdd.annotations.DefaultUrl;
//import net.serenitybdd.core.pages.PageObject;
//import net.serenitybdd.core.pages.WebElementFacade;
//import net.serenitybdd.junit5.SerenityJUnit5Extension;
//
//import net.serenitybdd.screenplay.Performable;
//import net.serenitybdd.screenplay.Task;
//import net.serenitybdd.screenplay.actions.Scroll;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.openqa.selenium.support.FindBy;
//
//import java.time.Duration;
//
//// This represents the page object class of home page
//@ExtendWith(SerenityJUnit5Extension.class)
//@DefaultUrl("https://buy.wesco.ca")
//public class HomePageObject extends PageObject {
//
//    // use it as  page object modal
//    @FindBy(css = ".c-refinement-spotlight.c-refinement-spotlight--grid .view-more a.button.button-secondary.button-small")
//    private WebElementFacade categoriesButton;
//
//    @FindBy(css = ".c-content-block .c-content-block__cta a.button-transparent")
//    private WebElementFacade branchLocator;
//
//    public Performable clickOnCategoriesButton() {
//        // click on categories button
//        Scroll.to(categoriesButton); // scroll to button
//        return Task.where("selecitng categories button " , categoriesButton.withTimeoutOf(Duration.ofSeconds(4)).waitUntilClickable().click());
//    }
//
//    public Performable clickOnBranchLocator() {
//        Scroll.to(branchLocator); // scroll to branch locator button
//        return Task.where("clicks on button" ,branchLocator.withTimeoutOf(Duration.ofSeconds(4)).waitUntilClickable().click()); // wait until button is clickable;
//    }
//
//}
