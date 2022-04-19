package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BasePage {
    private static Logger log = LoggerFactory.getLogger("BasePage.class");
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;


    public BasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public void clickOnElement(WebElement element) {
        waitToBeVisible(element);
        element.click();
    }

    public void sendKeysToElement(WebElement element, String text) {
        waitToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    public void chooseRandomListElement(List<WebElement> elements) {
        waitToListVisible(elements);
        WebElement element = elements.get(new Random().nextInt(elements.size()));
        log.info("List size: " + elements.size());
        if (!element.getText().equalsIgnoreCase("more")) {
            actions.moveToElement(element);
            actions.perform();
            waitToBeVisible(element);
            clickOnElement(element);
        } else {
            element.click();
            chooseRandomListElement(elements);
        }

    }


    public void waitToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitToListVisible(List<WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void waitForAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public String getValidationMessage() {
        waitForAlert();
        String alertMessage = driver.switchTo().alert().getText();
        log.info("Validation message: " + alertMessage);
        return alertMessage;
    }

    public void acceptAlert() {
        waitForAlert();
        driver.switchTo().alert().accept();

    }
}
