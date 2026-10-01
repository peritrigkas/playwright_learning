package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MockWebTest {

    Playwright playwright;
    Page page;
    Browser browser;

    @BeforeEach
    public void setUp() {

        playwright = Playwright.create(); // create a new instance of Playwright
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");

    }

    @AfterEach
    public void tearDown() {
        playwright.close();   // also closes the browser and pages
    }

    @Test
    public void testSomething() {


        System.out.println(page.title());

        assertThat(page).hasTitle("EventHub — Discover & Book Events");


        page.getByLabel("Email")
                .fill("student@example.com");

        page.getByLabel("Password")
                .fill("secret123");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();


        // Mock the resposne with Route
        page.route("**/api/events**", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setPath(Paths.get("src/test/resources/events_6.json")));
        });
        page.navigate("https://eventhub.rahulshettyacademy.com/events");

        page.waitForTimeout(4000);

        Locator eventCards = page.getByTestId("event-card");
        assertEquals(6, eventCards.count());
        assertTrue(page.locator(".mx-1").first().isVisible());
//                assertThat(page.locator(".mx-1")).isVisible();
//        Assertions.assertTrue(page.locator(".mx-1").isVisible());
// resolves to two elements. We added . first() above to solve the problem

    }
}
