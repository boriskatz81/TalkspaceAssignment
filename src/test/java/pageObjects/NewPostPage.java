package pageObjects;

import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewPostPage extends BasePage {
    public NewPostPage(WebDriver driver) {super(driver);}

    @FindBy(id = "title")
    public WebElement newPostTitleInput;

    @FindBy(id = "content")
    public WebElement newPostContentInput;

    @FindBy(id = "published")
    public WebElement newPostPublishedCheckbox;

    @FindBy(xpath = "//section[@data-testid = 'property-edit-status']//div[contains(@class , 'adminjs_Select ')]")
    public WebElement newPostStatusDropDown;

    @FindBy(xpath = "//section[@data-testid = 'property-edit-publisher']//div[contains(@class , 'adminjs_Select ')]")
    public WebElement newPostPublisherDropDown;

    @FindBy(xpath = "//button[@data-css='Post-new-drawer-submit']")
    public WebElement saveButton;

    @FindBy(xpath = "//button[@data-css='Post-edit-drawer-submit']")
    public WebElement editButton;

    @FindBy(xpath = "//button[@data-testid = 'someJson-add']")
    public WebElement newPostNewJsonButton;

    @FindBy(xpath = "//input[contains(@name , 'someJson.') and contains(@name , '.number')]")
    public WebElement newPostNewSomeJsonNumberInput;

    @FindBy(xpath = "//input[contains(@name , 'someJson.') and contains(@name , '.string')]")
    public WebElement newPostNewSomeJsonTextInput;





    public void SetPostTitle(String title) {
        actions.fillText(newPostTitleInput, title, defaultTimeout);
    }

    public void SetPostContent(String content) {
        actions.fillText(newPostContentInput, content, defaultTimeout);
    }

    public void SetRandomJsonData(){
        actions.click(newPostNewJsonButton, defaultTimeout);
        actions.fillText(newPostNewSomeJsonNumberInput, "1234", defaultTimeout);
        actions.fillText(newPostNewSomeJsonTextInput, "abc", defaultTimeout);
    }

    public void SetPostToBePublished() {actions.checkCheckbox(newPostPublishedCheckbox);}

    public void SetPostStatus(String status) {
        DropDownSelection(newPostStatusDropDown, status);
    }

    public void SetPostPublisher(String publisher) {
        DropDownSelection(newPostPublisherDropDown, publisher);
    }

    public void DropDownSelection(WebElement el, String valueToSelect) {
        WebElement dropDown = actions.click(el, defaultTimeout);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropDownInput = wait.until(ExpectedConditions.visibilityOf(dropDown.findElement(By.xpath(".//input"))));

        actions.fillText(dropDownInput , valueToSelect, defaultTimeout);
        actions.sleep(2000);
        dropDownInput.sendKeys(Keys.ENTER);
    }


    public JSONObject SaveNewPost(boolean toEdit){
        String requestUrl = "/resources/Post/actions/new";
        String includeInBody = "successfullyCreated";

        if(toEdit){
            actions.click(editButton, defaultTimeout);
            return new JSONObject();
        }
        String newPublisher = actions.ClickAndRetrieveResponse(saveButton, requestUrl,includeInBody);
        return new JSONObject(newPublisher).getJSONObject("record").getJSONObject("params");
    }

    public JSONObject CreateNewPost(String title, String content, String status, JSONObject publisher) {
        SetPostTitle(title);
        SetPostContent(content);
        SetRandomJsonData();
        SetPostToBePublished();
        SetPostStatus(status);
        SetPostPublisher(publisher.getString("email"));
        JSONObject newPost = SaveNewPost(false);
        JSONObject expectedData = new JSONObject("{\"title\":\"" + title + "\"," +
                "\"content\":\"" + content + "\"," +
                "\"published\":\"true\"," +
                "\"status\":\"" + status + "\"," +
                "\"publisher\":\"" + publisher.get("id").toString() + "\"," +
                "}");

        ValidateAndExpectedData(expectedData, newPost);
        return newPost;
    }
}
