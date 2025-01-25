package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage extends BasePage {

    public String baseUrl = "https://www.saucedemo.com/v1/";

    public String passwordValue = "secret_sauce";
    public LogInPage(WebDriver driver) {super(driver);}

    @FindBy(id = "user-name")
    public WebElement userNameInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(id = "login-button")
    public WebElement logInButton;

    @FindBy(xpath = "//*[@data-test = 'error']")
    public WebElement logInErrorMessage;

    public String expectedLockedUserError = "Epic sadface: Sorry, this user has been locked out.";

    public void LogIn(String username) {
        driver.get(baseUrl);
        fillText(userNameInput, username);
        fillText(passwordInput, passwordValue);
        click(logInButton);
    }

    public String NegativeLogIn(String username) {
        driver.get(baseUrl);
        fillText(userNameInput, username);
        fillText(passwordInput, passwordValue);
        click(logInButton);
        return logInErrorMessage.getText();
    }
}
