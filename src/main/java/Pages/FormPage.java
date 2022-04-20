package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class FormPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger("FormPage.class");
    private final List<String> allMonths = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    @FindBy(id = "createDinnerName-awed")
    private WebElement nameInput;
    //calendar
    @FindBy(css = ".awe-icon-datepicker")
    private WebElement calendarIcon;
    @FindBy(css = "#createDinnerDatecm-awed .o-cptn")
    private WebElement actualMonth;
    @FindBy(css = "#createDinnerDatecy-awed .o-cptn")
    private WebElement actualYear;
    @FindBy(css = ".left")
    private WebElement arrowLeft;
    @FindBy(css = "button .right")
    private WebElement arrowRight;
    @FindBy(css = "td:not(.o-outm) div")
    private List<WebElement> allDays;

    @FindBy(css = ".o-mhd")
    private WebElement monthButton;
    @FindBy(css = "o-yhd")
    private WebElement yearButton;
    @FindBy(css = "#createDinnerDatecm-dropmenu .o-ditm")
    private List<WebElement> monthList;
    @FindBy(css = "#createDinnerDatecy-dropmenu .o-ditm")
    private List<WebElement> yearList;

    @FindBy(css = ".awe-lookup-field .awe-lkpbtn")
    private WebElement chefButton;
    @FindBy(css = "#createDinnerChef-awepw .awe-li")
    private List<WebElement> chefList;
    @FindBy(css = "[data-i=\"createDinnerChef-awepw\"] .awe-okbtn")
    private WebElement okChefButton;
    @FindBy(css = ".awe-caption .awe-empty")
    private WebElement emptyChefInput;
    @FindBy(css = ".awe-caption")
    private WebElement chefInput;
    @FindBy(css = ".awe-multilookup-field .awe-lkpbtn")
    private WebElement mealsButton;
    @FindBy(css = "#createDinnerMeals-awepw .awe-srlcont .awe-li")//values
    private List<WebElement> mealListValues;
    @FindBy(css = ".awe-srlcont .awe-li button")
    private List<WebElement> mealsList;
    @FindBy(css = ".awe-sel li")
    private List<WebElement> selectedMeals;
    @FindBy(css = "[data-i=\"createDinnerMeals-awepw\"] .awe-okbtn")
    private WebElement okMealButton;
    @FindBy(id = "createDinnerBonusMealId-awed")
    private WebElement bonusMealButton;
    @FindBy(css = "#createDinnerBonusMealId-dropmenu li")
    private List<WebElement> bonusMealList;
    @FindBy(css = ".awe-okbtn")
    private WebElement confirmFormButton;

    public FormPage(WebDriver driver) {
        super(driver);
    }

    public String getActualMonth() {
        return actualMonth.getText();
    }

    public int getActualYear() {
        return Integer.parseInt(actualYear.getText());
    }

    public FormPage fillForm() {
        sendKeysToElement(nameInput, System.getProperty("name"));
        selectdate(System.getProperty("day"), System.getProperty("month"), Integer.parseInt(System.getProperty("year")));
        choseRandomChefPlus();
        choseThreeRandomMeals();
        choseRandomBonusMealPlus();
        clickOnElement(confirmFormButton);
        log.info("Form send");
        return this;
    }


    private void selectdate(String day, String month, int year) {
        clickOnElement(calendarIcon);
        if (year < getActualYear()) {
            goPrev(month, year);
        } else if (year > getActualYear()) {
            goNext(month, year);
        } else if (getNumberOfMonth(month) < getNumberOfMonth(getActualMonth())) {
            goPrev(month, year);
        } else if (getNumberOfMonth(month) > getNumberOfMonth(getActualMonth())) {
            goNext(month, year);
        }
        selectDay(day);
    }

    private void goNext(String expectedMonth, int expectedYear) {
        while (!expectedMonth.equals(getActualMonth()) || expectedYear != getActualYear()) {
            clickOnElement(arrowRight);
        }
    }

    private void goPrev(String month, int year) {
        while (!month.equals(getActualMonth()) || year != getActualYear()) {
            clickOnElement(arrowLeft);
        }
    }

    private void selectDay(String dayToSelect) {
        for (WebElement day : allDays) {
            if (day.getText().equals(dayToSelect)) {
                day.click();
                break;
            }
        }
    }

    private int getNumberOfMonth(String monthName) {
        return allMonths.indexOf(monthName);
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

    private void choseRandomChefPlus() {
        clickOnElement(chefButton);
        WebElement element = getRandomListEl(chefList);
        if (!element.getText().equalsIgnoreCase("more")) {
            scrollToElementWithClick(element);

        } else {
            scrollToElementWithClick(element);
            choseRandomMealPlus();
        }
        clickOnElement(okChefButton);
        waitToBeNotVisible(emptyChefInput);
        log.info("Random chef is: " + chefInput.getText());
    }

    private void choseThreeRandomMeals() {
        clickOnElement(mealsButton);
        choseRandomMealPlus();
        choseRandomMealPlus();
        choseRandomMealPlus();
        for (WebElement selectedMeal : selectedMeals) {
            log.info("Random meal is: " + selectedMeal.getText());
        }
        clickOnElement(okMealButton);
    }

    private void choseRandomMealPlus() {
        WebElement element = getRandomListEl(mealListValues);
        if (!element.getText().equalsIgnoreCase("more")) {
            scrollToElement(element);
            highLightenerMethod(element);
            clickOnElement(element.findElement(By.cssSelector("button")));
        } else {
            scrollToElementWithClick(element);
            choseRandomMealPlus();
        }

    }

    private void choseRandomBonusMealPlus() {
        clickOnElement(bonusMealButton);
        WebElement element = getRandomListEl(bonusMealList);
        scrollToElementWithClick(element);
        log.info("Random bonus meal is: " + bonusMealButton.getText());

    }

}







