package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage extends BasePage {
    public LogInPage(WebDriver driver) {super(driver);}

    @FindBy(name = "email")
    public WebElement userNameInput;

    @FindBy(name = "password")
    public WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Login']")
    public WebElement logInButton;

    public void LogIn(String url, String username, String password) {
        driver.get(url);
        actions.fillText(userNameInput, username, defaultTimeout);
        actions.fillText(passwordInput, password, defaultTimeout);
        actions.click(logInButton, defaultTimeout);
    }
}
