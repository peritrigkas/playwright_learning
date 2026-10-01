package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UIValidation {

    Playwright playwright;
    Page page;
    Browser browser;

    @BeforeEach
    public void setUp(){

        playwright = Playwright.create(); // create a new instance of Playwright
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://rahulshettyacademy.com/AutomationPractice/");
    }

    @AfterEach
    public void tearDown(){
        browser.close();
        playwright.close();
    }

    @Test
    public void testUIValidation(){

        page.getByPlaceholder("Hid/Show Example").isVisible();

        page.locator("#hide-textbox").click();
       assertThat(page.locator("#displayed-text")).isHidden();
       page.onDialog(Dialog::accept);

       page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Alert")).click();

       page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Mouse Hover")).hover();
       page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Top")).click();

       FrameLocator framePage = page.frameLocator("#courses-iframe");
       framePage.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("Learning Paths")).click();
       String textFrame = framePage.locator(".inner-box h1").innerText();
       System.out.println(textFrame);


//       page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("learning-path")).click();
    }

    @Test
    public void testUIValidation2(){

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")));
    }
}
