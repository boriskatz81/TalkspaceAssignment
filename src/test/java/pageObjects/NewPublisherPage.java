package pageObjects;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewPublisherPage extends BasePage {
    public NewPublisherPage(WebDriver driver) {super(driver);}

    @FindBy(id = "name")
    public WebElement newPublisherNameInput;

    @FindBy(id = "email")
    public WebElement newPublisherEmailInput;

    @FindBy(xpath = "//button[@data-css='Publisher-new-drawer-submit']")
    public WebElement saveButton;

    public void SetPublisherName(String name) {
        actions.fillText(newPublisherNameInput, name, defaultTimeout);
    }

    public void SetPublisherEmail(String email) {
        actions.fillText(newPublisherEmailInput, email, defaultTimeout);
    }

    public JSONObject SaveNewPublisher(){
        String new_publisher = actions.ClickAndRetrieveResponse(saveButton, "/resources/Publisher/actions/new",
                "successfullyCreated");

        return new JSONObject(new_publisher).getJSONObject("record").getJSONObject("params");
    }

    public JSONObject CreateNewPublisher(String name, String email) {
        SetPublisherName(name);
        SetPublisherEmail(email);
        JSONObject newPublisher = SaveNewPublisher();
        JSONObject expectedData = new JSONObject("{\"name\":\"" + name + "\"," +
                "\"email\":\"" + email + "\"" +
                "}");

        ValidateAndExpectedData(expectedData, newPublisher);
        return newPublisher;
    }
}
