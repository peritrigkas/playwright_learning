package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {

    private final Page page;
    private final String base_url;
    private static final String email_label = "Email";
    private static final String password_label = "Password";

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.base_url = baseUrl;
    }

    public void loginToApplication(){

        page.navigate(base_url);

        System.out.println(page.title());

        assertThat(page).hasTitle("EventHub — Discover & Book Events");


        page.getByLabel(email_label)
                .fill("student@example.com");

        page.getByLabel(password_label)
                .fill("secret123");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
    }
}
