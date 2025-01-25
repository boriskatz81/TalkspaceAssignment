package pageObjects;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPage extends BasePage {

    public ShoppingCartPage(WebDriver driver) {super(driver);}

    public String cartItemLabel = "//div[@class = 'cart_item_label']";
    public String inventoryItemName = ".//div[@class = 'inventory_item_name']";
    public String inventoryItemPrice = ".//div[@class = 'inventory_item_price']";
    public String inventoryItemDescription = ".//div[@class = 'inventory_item_desc']";

    public String removeButton = ".//button[@class = 'btn_secondary cart_button']";

    @FindBy(xpath = "//a[@class = 'btn_action checkout_button']")
    public WebElement checkoutButton;

    @FindBy(xpath = "//a[@class = 'btn_secondary' and text() = 'Continue Shopping']")
    public WebElement continueShoppingButton;


    public List<JSONObject> RetrieveAllItemsWithinShoppingCart() {
        List<JSONObject> allCartItemLabels = new ArrayList<>();
        WaitForTheElementToBeEnabled(checkoutButton);
        List<WebElement> inventoryItems = driver.findElements(By.xpath(cartItemLabel));
        if(inventoryItems.size() <=0) {
            Assert.fail("No Item Labels were found within the page");
        }
        for(WebElement inventoryItem: inventoryItems) {
            String inventoryItemNameValue = inventoryItem.findElement(By.xpath(inventoryItemName)).getText();
            WebElement removeButtonElement = inventoryItem.findElement(By.xpath(removeButton));
            String removeButtonText = removeButtonElement.getText();
            Assert.assertTrue(removeButtonText.equalsIgnoreCase("remove"),
                    inventoryItemNameValue + " Button text is " + removeButtonText +
                    " instead of Remove");

            String inventoryItemDescriptionValue = inventoryItem.findElement(By.xpath(inventoryItemDescription)).getText();
            String inventoryItemPriceValue = inventoryItem.findElement(By.xpath(inventoryItemPrice)).getText();
            JSONObject inventoryItemData = new JSONObject("{" +
                    "'name': '" + inventoryItemNameValue + "'," +
                    "'price': '" + inventoryItemPriceValue + "'," +
                    "'description': '" + inventoryItemDescriptionValue.replace("'", "").trim() + "'," +
                    "}");

            allCartItemLabels.add(inventoryItemData);
        }
        return allCartItemLabels;
    }

    public void RemoveAllItemsFromShoppingCart() {
        WaitForTheElementToBeEnabled(checkoutButton);
        List<WebElement> inventoryItems = driver.findElements(By.xpath(cartItemLabel));
        for(int i=0; i< inventoryItems.size(); i++) {
            WebElement inventoryItem = driver.findElement(By.xpath(cartItemLabel));
            WebElement removeButtonElement = inventoryItem.findElement(By.xpath(removeButton));
            click(removeButtonElement);
            sleep(2000);
        }
        EnsureNoElementExistOnPage(By.xpath(cartItemLabel));
    }

    public void ClickOnCheckout() {
        click(checkoutButton);
    }
}
