package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
    WebDriver driver;
    protected WebDriverWait wait;

    BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement el) {
        el.click();
    }

    public void sleep(long mills) {
        try {
            Thread.sleep(mills);
        }catch (Exception ex) {}
    }

    public void fillText(WebElement el, String text) {
        el.clear();
        el.sendKeys(text);
    }

    public void WaitForTheElementToBeEnabled(WebElement el) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        wait.until(ExpectedConditions.elementToBeClickable(el));
        Assert.assertTrue(el.isEnabled());
    }

    public void EnsureNoElementExistOnPage(By elementXPath) {
        for(int i=0; i < 4; i++) {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> possibleElements = driver.findElements(elementXPath);
            if(possibleElements.size() <= 0) {
                driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
                return;
            }
            sleep(5000);
        }
        Assert.fail("Element " + elementXPath + " is still found within the page though it shouldn't");
    }
}
