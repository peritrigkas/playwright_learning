package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasicsTest {
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

        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");

//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Event")).click();
//        assertThat(page.getByText("Event created successfully")).isVisible();

        page.getByPlaceholder("Event title").fill("Event Peri");
        page.getByPlaceholder("Describe the event…").fill("This is a test event");
        page.getByLabel("Category").selectOption("Conference");
        page.getByLabel("City").fill("Cardiff");
        page.getByLabel("Venue").fill("CIA");
        page.getByLabel("Event Date & Time").fill("2026-10-18T12:30");
        page.getByLabel("Price ($)").fill("189");
        page.getByLabel("Total Seats").fill("200");
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Event")).click();
        page.locator("#add-event-btn").click();
        assertThat(page.getByText("Event created!")).isVisible();
//        page.waitForTimeout(5000);

        page.navigate("https://eventhub.rahulshettyacademy.com/events");
        page.waitForTimeout(5000);
        Locator eventCards =  page.getByTestId("event-card");
        Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText("Peri"));
        assertThat(targetCard).isVisible(); // 5 secs wait by default when assertion happens

       String seats = targetCard.getByText("seats").innerText();
       System.out.println(seats);

       targetCard.locator("#book-now-btn").click();
//       page.waitForTimeout(5000);

// Book the tickets functionality
        page.getByLabel("Full Name").fill("Test User");
        page.getByLabel("Email").fill("testuser@gmail.com");
        page.getByLabel("Phone Number").fill("07987654321");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();
        assertThat(page.getByText("Your tickets are reserved.")).isVisible();

        String booking_ref = page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View My Bookings")).click();



    }

}
