package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {

    private final Page page;
    private final String baseUrl;
    private static final String EMAIL_LABEL = "Email";
    private static final String PASSWORD_LABEL = "Password";

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public DashboardPage loginToApplication(){

        page.navigate(baseUrl);
        System.out.println(page.title());
        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        page.getByLabel(EMAIL_LABEL)
                .fill("student@example.com");
        page.getByLabel(PASSWORD_LABEL)
                .fill("secret123");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        return new DashboardPage(page);
    }
}
