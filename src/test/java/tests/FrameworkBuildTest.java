package tests;

import Utils.TestDataProvider;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.*;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

public class FrameworkBuildTest extends BaseTest {

    static Stream<Map<String, String>> eventData() throws IOException {
        return TestDataProvider.readJson("src/test/resources/testData_events.json").stream();
    }

    @ParameterizedTest
    @MethodSource("eventData")
    public void testSomething(Map<String, String> data) {

//        String eventTitle = data.get("title");

        LoginPage loginPage = new LoginPage(page, baseurl);
        DashboardPage dashboardPage = loginPage.loginToApplication();

        dashboardPage.waitForEventsToLoad();



        // Step 1 Create Event from Admin Page
        AdminEventsPage adminEventsPage = new AdminEventsPage(page);
        adminEventsPage.navigateToAdminEventsPage();
        adminEventsPage.createEvent(
                data.get("title"),
                data.get("description"),
                data.get("category"),
                data.get("city"),
                data.get("venue"),
                data.get("date"),
                data.get("price"),
                data.get("totalSeats"));
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Event")).click();
//        assertThat(page.getByText("Event created successfully")).isVisible();

        EventsPage eventsPage = new EventsPage(page);
        eventsPage.goToEventsPage();

        eventsPage.waitForEventToLoad();
        Locator targetCard = eventsPage.findEventCard(data.get("title"));
        int seatsNumberBefore = eventsPage.getSeatsCount(targetCard);
        BookingFormPage bookingFormPage = eventsPage.clickOnEventCard(targetCard);


// Book the tickets functionality
        bookingFormPage.clickAndConfirm(
                data.get("fullName"),
                data.get("email"),
                data.get("phone"));

        String booking_ref = page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View My Bookings")).click();



    }

}
