package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import pages.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FrameworkBuildTest extends BaseTest {


    @Test
    public void testSomething() {

        String eventTitle = "Event Peri";

        LoginPage loginPage = new LoginPage(page, baseurl);
        DashboardPage dashboardPage = loginPage.loginToApplication();

        dashboardPage.waitForEventsToLoad();



        // Step 1 Create Event from Admin Page
        AdminEventsPage adminEventsPage = new AdminEventsPage(page);
        adminEventsPage.navigateToAdminEventsPage();
        adminEventsPage.createEvent(
                eventTitle,
                "This is a test event",
                "Conference",
                "My City",
                "Venue",
                "2026-10-18T12:30",
                "190",
                "300");
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Event")).click();
//        assertThat(page.getByText("Event created successfully")).isVisible();

        EventsPage eventsPage = new EventsPage(page);
        eventsPage.goToEventsPage();

        eventsPage.waitForEventToLoad();
        Locator targetCard = eventsPage.findEventCard(eventTitle);
        int seatsNumberBefore = eventsPage.getSeatsCount(targetCard);
        BookingFormPage bookingFormPage = eventsPage.clickOnEventCard(targetCard);


// Book the tickets functionality
        bookingFormPage.clickAndConfirm(
                "Test User",
                "testuser@gmail.com",
                "07987654321");

        String booking_ref = page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View My Bookings")).click();



    }

}
