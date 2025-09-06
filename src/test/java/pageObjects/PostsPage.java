package pageObjects;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostsPage extends BasePage {
    public PostsPage(WebDriver driver) {super(driver);}

    @FindBy(xpath = "//a[contains(@href , '/resources/Post')]")
    public WebElement postsButton;

    @FindBy(xpath = "//a[@data-testid='actions-dropdown']")
    public WebElement actionsButton;

    @FindBy(xpath = "//a[@data-testid='action-edit']")
    public WebElement editButton;

    @FindBy(xpath = "//a[contains(@href , '/resources/Post/actions/new')]")
    public WebElement newPostButton;

    public String tableName = "Post";


    public void OpenPostsPage() {
        actions.WaitForTheElementToBeEnabled(happyFolderButton, defaultTimeout);
        for (int i=0; i<3; i++){
            try {
                actions.click(postsButton, 3);
                break;
            }
            catch (Exception ex){
                actions.click(happyFolderButton, defaultTimeout);
            }
        }
        actions.WaitForTheElementToBeEnabled(newPostButton, defaultTimeout);
    }

    public void OpenPostEditPage() {
        actions.WaitForTheElementToBeEnabled(actionsButton, defaultTimeout);
        actions.click(actionsButton, defaultTimeout);
        actions.click(editButton, defaultTimeout);
    }

    public void OpenNewPostPage() {
        actions.click(newPostButton, defaultTimeout);
    }

    public void ValidateTableData(JSONObject expectedData){
        JSONObject tableData = FilterByIdAndRetrieveTableData(expectedData.get("id").toString(), tableName);
        ValidateAndExpectedData(expectedData, tableData);
    }
}
