import Pages.FormPage;
import Pages.HomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FormTest extends BaseTest {
    HomePage homePage;
    FormPage formPage;


    @BeforeEach
    public void initPages() {
        homePage = new HomePage(driver);
        formPage = new FormPage(driver);
    }

    @Test
    public void shouldFillFormWithSucces() {
        homePage.goToForm();
        formPage.fillForm();
        String alertMessage = formPage.getValidationMessage();
        formPage.acceptAlert();
        assertThat(alertMessage, equalTo(System.getProperty("expectedMsg")));
    }
}
