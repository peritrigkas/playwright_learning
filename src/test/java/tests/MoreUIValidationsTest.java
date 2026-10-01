package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoreUIValidationsTest {

    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

    @AfterEach
    public void tearDown() {
        playwright.close();   // also closes the browser and pages
    }

    @Test
    public void moreUIValidationsTest() {
        Page newPage = browserContext.waitForPage(() -> page.locator(".blinkingText").first().click());
        // page.locator(".blinkingText").first().click(); //

        newPage.waitForLoadState(); // in navigate the wait is integrated. We provide explicitly because of that.
        String childText = newPage.locator(".red").innerText();
       System.out.println(childText);
        String emailId = childText.split("at ")[1].split(" ")[0];
        System.out.println(emailId);

        // Back to the main page
        page.getByLabel("Username").fill(emailId);
        page.getByLabel("Password").fill("Learning@830$3mK2");
        page.waitForTimeout(5000);
    }

    @Test
    public void moreUIValidationsTest2() {

        page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("User")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Okay")).click();
        assertTrue(page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("User")).isChecked());
        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("I Agree to the terms and conditions")).click();

        page.locator("select.form-control").selectOption("Teacher");

        page.waitForTimeout(5000);
    }

}
