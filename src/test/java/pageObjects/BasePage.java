package pageObjects;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.TestActions;

import java.util.ArrayList;
import java.util.List;


public class BasePage {
    WebDriver driver;
    protected TestActions actions;
    public int defaultTimeout = 120;

    @FindBy(xpath = "//div[text()='Happy Folder']")
    public WebElement happyFolderButton;

    @FindBy(name = "filter-id")
    public WebElement idToFilterInput;
    public String filterButton = "//a[@data-css='{data_css}-filter-button']";
    public String filterSubmitButton = "//button[@data-css='{data_css}-filter-drawer-button-apply']";


    public String tableHeaders = "//table[@data-css='{data_css}-table']//thead//td";
    public String tableCells = "//table[@data-css='{data_css}-table']//tbody//tr";


    public BasePage(WebDriver driver) {
        this.driver = driver;
        actions = new TestActions(this.driver);
        PageFactory.initElements(driver, this);
    }


    public JSONObject FilterByIdAndRetrieveTableData(String idToFilterBy, String filterCss) {
        String filterXpath = filterButton.replace("{data_css}", filterCss);
        actions.WaitForTheElementToBeEnabledByLocator(By.xpath(filterXpath), 120);
        actions.click(driver.findElement(By.xpath(filterXpath)), 120);
        actions.fillText(idToFilterInput, idToFilterBy, 120);

        String filterSubmitXpath = filterSubmitButton.replace("{data_css}", filterCss);
        actions.WaitForTheElementToBeEnabledByLocator(By.xpath(filterSubmitXpath), 120);
        actions.click(driver.findElement(By.xpath(filterSubmitXpath)),120);
        for (int i=0; i<5; i++) {
            JSONObject tableData = RetrieveTableData(filterCss);
            if(tableData.get("id").toString().equals(idToFilterBy)) {
                return tableData;
            }
        }
        Assert.fail("Failed to detect tables row with id=" + idToFilterBy);
        return null;
    }

    public JSONObject RetrieveTableData(String tableCss) {
        String headersXpath = tableHeaders.replace("{data_css}", tableCss);
        actions.WaitForTheElementToBeEnabledByLocator(By.xpath(headersXpath), 120);

        List<WebElement> headers = driver.findElements(By.xpath(headersXpath));
        ArrayList<String> firsRowCells = ValidateRowsAppear(tableCss);
        JSONObject tableFirstRowData = new JSONObject(){};
        for (int i=0; i<headers.size(); i++){
            tableFirstRowData.put(headers.get(i).getText().toLowerCase().replace("#", "id"), firsRowCells.get(i));
        }
        return tableFirstRowData;
    }

    public ArrayList<String> ValidateRowsAppear(String tableCss) {
        String cellsXpath = tableCells.replace("{data_css}", tableCss);
        actions.WaitForTheElementToBeEnabledByLocator(By.xpath(cellsXpath), 120);
        for (int i=0; i<5; i++){
            try {
                List<WebElement> rows = driver.findElements(By.xpath(cellsXpath));
                ArrayList<String> cell_values = new ArrayList<>();
                List<WebElement> cells = rows.get(0).findElements(By.xpath(".//td"));
                for (WebElement cell : cells) {
                    cell_values.add(cell.getText());
                }
                return cell_values;
            }catch (Exception ex) {
                actions.sleep(2000);
            }
        }
        Assert.fail("Failed to detect tables rows");
        return null;
    }


    public static void ValidateAndExpectedData(JSONObject expectedData, JSONObject actualData) {
        ArrayList<String> err_list = new ArrayList<>();
        for (String key : expectedData.keySet()){
            if(!actualData.has(key.toLowerCase())) {
                err_list.add("Key " + key + " was not found in actual data");
            }
            else if(!actualData.get(key.toLowerCase()).toString().equals(expectedData.get(key).toString())) {
                err_list.add("Key " + key + " Expected " + expectedData.get(key).toString() + " but got " + actualData.get(key.toLowerCase()).toString());
            }
            else {
                System.out.println("Key " + key + " : " + expectedData.get(key).toString() + "=" + actualData.get(key.toLowerCase()).toString());
            }
        }
        Assert.assertTrue(err_list.size() == 0, err_list.toString());
    }
}
