package pageObjects;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {super(driver);}

    @FindBy(id = "first-name")
    public WebElement firstNameInput;

    @FindBy(id = "last-name")
    public WebElement lastNameInput;

    @FindBy(id = "postal-code")
    public WebElement postalCode;

    @FindBy(xpath = "//*[@class = 'btn_primary cart_button']")
    public WebElement continueButton;

    @FindBy(xpath = "//*[@class = 'btn_action cart_button' and text() = 'FINISH']")
    public WebElement finishButton;

    @FindBy(xpath = "//*[@class = 'complete-header']")
    public WebElement completeHeader;
    public String expectedCompleteHeader = "THANK YOU FOR YOUR ORDER";

    @FindBy(xpath = "//div[@class = 'complete-text']")
    public WebElement completeText;
    public String expectedCompleteText = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";


    public JSONObject PerformCheckoutToAddedItem(JSONObject checkoutData) {
        fillText(firstNameInput, checkoutData.getString("firstName"));
        fillText(lastNameInput, checkoutData.getString("lastName"));
        fillText(postalCode, checkoutData.getString("postalCode"));
        click(continueButton);
        click(finishButton);
        return new JSONObject("{" +
                "'completeHeader': '" + completeHeader.getText() + "'," +
                "'completeText': '" + completeText.getText() + "'" +
                "}");
    }
}
