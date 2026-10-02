package pages;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AdminEventsPage {

    Page page;
    private static final String EVENT_TITLE_PLACEHOLDER = "Title";
    private static final String EVENT_DESCRIPTION_PLACEHOLDER = "Describe the event…";
    private static final String EVENT_CATEGORY_LABEL = "Category";
    private static final String EVENT_CITY_LABEL = "City";
    private static final String EVENT_VENUE_LABEL = "Venue";
    private static final String EVENT_DATE_LABEL = "Event Date & Time";
    private static final String EVENT_PRICE_LABEL = "Price ($)";
    private static final String EVENT_TOTAL_SEATS_LABEL = "Total Seats";
    private static final String CREATE_EVENT_BUTTON_LOCATOR = "#add-event-btn";

    public AdminEventsPage(Page page) {
        this.page = page;
    }

    public void navigateToAdminEventsPage() {

       page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
       page.waitForTimeout(3000);

    }

    public void createEvent(String title, String description, String category, String city, String venue, String date, String price, String totalSeats  ) {
        page.getByLabel(EVENT_TITLE_PLACEHOLDER).fill(title);
        page.getByPlaceholder(EVENT_DESCRIPTION_PLACEHOLDER).fill(description);
        page.getByLabel(EVENT_CATEGORY_LABEL).selectOption(category);
        page.getByLabel(EVENT_CITY_LABEL).fill(city);
        page.getByLabel(EVENT_VENUE_LABEL).fill(venue);
        page.getByLabel(EVENT_DATE_LABEL).fill(date);
        page.getByLabel(EVENT_PRICE_LABEL).fill(price);
        page.getByLabel(EVENT_TOTAL_SEATS_LABEL).fill(totalSeats);
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Event")).click();
        page.locator(CREATE_EVENT_BUTTON_LOCATOR).click();
        assertThat(page.getByText("Event created!")).isVisible();

    }

}
