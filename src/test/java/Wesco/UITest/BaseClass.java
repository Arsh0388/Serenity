package Wesco.UITest;

import Wesco.WebPage.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseClass {
    protected WebDriver driver;
    protected WebDriverWait waitUntil;

    protected HomePage homePage;

    private static final String BASE_URL = "https://buy.wesco.ca/";

    @BeforeEach
    public void setUp() {
        // perform the setup.
        ChromeOptions options = new ChromeOptions();
        // add chrome arguments to initialize chrome
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--incognito");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", "/Users/arsharora/IdeaProjects/Serenity/download_files\n"); // Change path // setting default directory
        prefs.put("download.prompt_for_download", false);
        prefs.put("plugins.always_open_pdf_externally", true); // Force download for PDFs
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);

        // set page load timeouts and script running timeouts
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        // initialize the very home page
        homePage = new HomePage(driver);
        // open the Google Chrome and navigate to home web page.
        driver.get(BASE_URL);
        driver.manage().window().maximize(); // full screen of browser.


    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit(); // close the driver.
        }
    }


    // open new tab
//driver.switchTo().newWindow(WindowType.TAB);
// driver.navigate().back();     // go back in browser history
//driver.navigate().forward();  // go forward in browser history
//driver.navigate().refresh();  // refresh current page
    // switch back to main page
//driver.switchTo().defaultContent();
}
