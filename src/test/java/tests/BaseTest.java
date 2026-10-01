package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    Playwright playwright;
    Page page;
    Browser browser;
    String baseurl;
    @BeforeEach
    public void setUp() throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        prop.load(fis);
        String browserName = prop.getProperty("browser");
        playwright = Playwright.create();
        if(browserName.equals("firefox")) {
            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else if(browserName.equals("webkit")) {
            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else {
            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }


//        prop.setProperty("browser", "chromium");
         // create a new instance of Playwright
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        baseurl = prop.getProperty("qa.baseurl");


    }

    @AfterEach
    public void tearDown() {
        playwright.close();   // also closes the browser and pages
    }
}
