package pageObjects;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PublishersPage extends BasePage {
    public PublishersPage(WebDriver driver) {super(driver);}

    @FindBy(xpath = "//a[contains(@href , '/resources/Publisher')]")
    public WebElement publishersButton;

    @FindBy(xpath = "//a[contains(@href , '/resources/Publisher/actions/new')]")
    public WebElement newPublisherButton;

    public String tableName = "Publisher";


    public void OpenPublishersPage() {
        actions.WaitForTheElementToBeEnabled(happyFolderButton, defaultTimeout);
        for (int i=0; i<3; i++){
            try {
                actions.click(publishersButton, 3);
                break;
            }
            catch (Exception ex){
                actions.click(happyFolderButton, defaultTimeout);
            }
        }
        actions.WaitForTheElementToBeEnabled(newPublisherButton, defaultTimeout);
    }

    public void OpenNewPublisherPage() {
        actions.click(newPublisherButton, defaultTimeout);
    }

    public void ValidateTableData(JSONObject expectedData){
        JSONObject tableData = FilterByIdAndRetrieveTableData(expectedData.get("id").toString(), tableName);
        ValidateAndExpectedData(expectedData, tableData);
    }
}
