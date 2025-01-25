package pageObjects;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.*;

public class ProductsPage extends BasePage {

    public String baseUrl = "https://www.saucedemo.com/v1/inventory.html";
    public ProductsPage(WebDriver driver) {super(driver);}

    public String inventoryItemDiv = "//div[@class = 'inventory_item']";
    public String inventoryItemName = ".//div[@class = 'inventory_item_name']";
    public String inventoryItemPrice = ".//div[@class = 'inventory_item_price']";
    public String inventoryItemDescription = ".//div[@class = 'inventory_item_desc']";
    public String addToCartButton = ".//button[@class = 'btn_primary btn_inventory']";

    @FindBy(xpath = "//span[@class = 'fa-layers-counter shopping_cart_badge']")
    public WebElement addedProductsCounter;

    @FindBy(xpath = "//*[@data-icon = 'shopping-cart']")
    public WebElement shoppingCart;


    public List<JSONObject> AddProductsToCart(Integer amountToAdd) {
        driver.get(baseUrl);
        List<JSONObject> allInventoryItemsData = new ArrayList<>();
        List<WebElement> inventoryItems = driver.findElements(By.xpath(inventoryItemDiv));
        if(inventoryItems.size() <=0) {
            Assert.fail("No Items were found within the page");
        }
        for(int i=0 ; i < amountToAdd; i++) {
            String inventoryItemNameValue = inventoryItems.get(i).findElement(By.xpath(inventoryItemName)).getText();
            WebElement addToCartElement = inventoryItems.get(i).findElement(By.xpath(addToCartButton));
            String addToCartButtonText = addToCartElement.getText();
            Assert.assertTrue(addToCartButtonText.equalsIgnoreCase("add to cart"),
                    inventoryItemNameValue + " Button text is " + addToCartButtonText +
                    " instead of Add To Cart");

            String inventoryItemDescriptionValue = inventoryItems.get(i).findElement(By.xpath(inventoryItemDescription)).getText();
            String inventoryItemPriceValue = inventoryItems.get(i).findElement(By.xpath(inventoryItemPrice)).getText();
            JSONObject inventoryItemData = new JSONObject("{" +
                    "'name': '" + inventoryItemNameValue + "'," +
                    "'price': '" + inventoryItemPriceValue.replace("$", "").trim() + "'," +
                    "'description': '" + inventoryItemDescriptionValue.replace("'", "").trim() + "'," +
                    "}");

            click(addToCartElement);
            allInventoryItemsData.add(inventoryItemData);
        }
        ValidateItemsCounterIsCorrect(String.valueOf(amountToAdd));
        return allInventoryItemsData;
    }

    public void ValidateItemsCounterIsCorrect(String expectedAmount) {
        String counterValue = addedProductsCounter.getText();
        Assert.assertEquals(counterValue, expectedAmount, "Counter value is " + counterValue + " instead of " + expectedAmount);
        System.out.println("Counter value is " + counterValue + " as expected");
    }

    public void ClickOnShoppingCart() {
        click(shoppingCart);
    }
}
