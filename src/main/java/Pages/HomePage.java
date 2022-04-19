package Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HomePage extends BasePage {
    private static Logger log = LoggerFactory.getLogger("Pages.HomePage.class");

    @FindBy(id = "btnMenuToggle")
    private WebElement menuButton;
    @FindBy(id = "msearch")
    private WebElement searchInput;
    @FindBy(css = ".awe-row")
    private List<WebElement> menuOptions;
    @FindBy(css = "#createDinner-awein+button")
    private WebElement createButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToForm() {
        sendKeysToElement(searchInput, "pop");
        goToElement(menuOptions);
        clickOnElement(createButton);
        return this;
    }

    public void goToElement(List<WebElement> elements) {
        for (WebElement element : elements) {
            if (element.getText().equals("PopupForm")) {
                log.info("Found: " + element.getText());
                element.click();
                break;
            } else
                actions.sendKeys(Keys.TAB);
        }

    }


}



