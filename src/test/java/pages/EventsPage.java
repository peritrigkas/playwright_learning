package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventsPage {

    Page page;

    public EventsPage(Page page) {
        this.page = page;
    }

    public void goToEventsPage() {
        page.navigate("https://eventhub.rahulshettyacademy.com/events");

    }

    public Locator waitForEventToLoad() {
        Locator eventCards =  page.getByTestId("event-card");
        assertThat(eventCards.first()).isVisible();
        return eventCards;
    }

    public Locator findEventCard(String titleCard) {
        Locator eventCards = waitForEventToLoad();
        Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText(titleCard));
        assertThat(targetCard).isVisible();
        return targetCard;
    }

    public int getSeatsCount(Locator targetCard) {

        String seats = targetCard.getByText("seats").innerText();
        System.out.println(seats);
        return Integer.parseInt(seats.split(" ")[0]);
    }

    public BookingFormPage clickOnEventCard(Locator targetCard) {

        targetCard.locator("#book-now-btn").click();
        return new BookingFormPage(page);
    }

}
