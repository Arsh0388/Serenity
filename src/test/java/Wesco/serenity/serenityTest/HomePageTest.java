//package Wesco.serenity.serenityTest;
//
//import Wesco.serenity.PageObjects.HomePageObject;
//import net.serenitybdd.annotations.Managed;
//import net.serenitybdd.junit5.SerenityJUnit5Extension;
//import net.serenitybdd.screenplay.Actor;
//import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
//import net.serenitybdd.screenplay.actions.Click;
//import net.serenitybdd.screenplay.actions.Open;
//import net.serenitybdd.screenplay.annotations.CastMember;
//import net.serenitybdd.screenplay.ensure.Ensure;
//import net.serenitybdd.screenplay.questions.page.TheWebPage;
//import net.serenitybdd.screenplay.waits.WaitUntil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.openqa.selenium.WebDriver;
//
//@ExtendWith(SerenityJUnit5Extension.class)
//public class HomePageTest {
//    @CastMember(name = "Arsh")
//    private Actor arsh;
//    private HomePageObject homePageObject = new HomePageObject();
//
//    @Managed(driver = "chrome")
//    WebDriver driver;
//    @BeforeEach
//    void setUp() {
//        arsh = Actor.named("Arsh");
//        arsh.can(BrowseTheWeb.with(driver));
//    }
//    @Test
//    public void homePageIsDisplayed() {
//        arsh.attemptsTo(
//          Open.url("https://www.buy.wesco.ca")
//        );
//        String title = TheWebPage.title().answeredBy(arsh);
//
//        arsh.attemptsTo(
//                Ensure.thatTheCurrentPage().title().contains("WESCO") // way to check whether we are on correct page or not !
//        );
//
//        // CLICK ON categories button
//
//    }
//}
