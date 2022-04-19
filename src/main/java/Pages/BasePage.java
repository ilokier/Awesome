package Pages;

import org.openqa.selenium.JavascriptExecutor;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);
    }

    public void clickOnElement(WebElement element) {
        waitToBeVisible(element);
        highLightenerMethod(element);
        element.click();
    }

    public void sendKeysToElement(WebElement element, String text) {
        waitToBeVisible(element);
        highLightenerMethod(element);
        element.clear();
        element.sendKeys(text);
    }

    public void chooseRandomListElement(List<WebElement> elements) {
        waitToListVisible(elements);
        WebElement element = elements.get(new Random().nextInt(elements.size()));
        log.info("List size: " + elements.size());
        if (!element.getText().equalsIgnoreCase("more")) {
            scrollToElement(element);
        } else {
            element.click();
            chooseRandomListElement(elements);
        }

    }

    public void scrollToElement(WebElement element) {
        actions.moveToElement(element);
        actions.perform();
        waitToBeVisible(element);
        clickOnElement(element);
    }

    public void waitToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitToBeNotVisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitToListVisible(List<WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void waitForAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public void highLightenerMethod(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: lightgreen; border: 5px solid green;')", element);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
