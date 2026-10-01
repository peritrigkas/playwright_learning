package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookingFormPage {

    Page page;

    private final String CONFIRMATION_MESSAGE = "Your tickets are reserved.";
    private final String FULL_NAME = "Full Name";
    private final String EMAIL = "Email";
    private final String PHONE_NUMBER = "Phone Number";

    public BookingFormPage(Page page) {

        this.page = page;
    }

    public void clickAndConfirm(String fullName, String email, String phoneNumber) {

        page.getByLabel(FULL_NAME).fill(fullName);
        page.getByLabel(EMAIL).fill(email);
        page.getByLabel(PHONE_NUMBER).fill(phoneNumber);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();
        assertThat(page.getByText(CONFIRMATION_MESSAGE)).isVisible();
    }
}
