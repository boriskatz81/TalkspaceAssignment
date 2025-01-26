package saucedemoCartAssignment;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CheckoutPage;
import pageObjects.LogInPage;
import pageObjects.ProductsPage;
import pageObjects.ShoppingCartPage;
import utilities.Base;
import utilities.ConfigReader;

import java.util.ArrayList;
import java.util.List;

public class TestingSaucedemoCarts extends Base {
    ProductsPage productsPage;
    ShoppingCartPage shoppingCartPage;


    @Test
    public void Test01_AddProductToCart() {
        initChromeDriver();
        List<JSONObject> addedCarts = AddProductsToCartAndRetrieveTheirValues( 1, ConfigReader.get("standardUsername"));

        List<String> errList = FindExpectedCartInShoppingCartAndValidateItsData(addedCarts);

        shoppingCartPage.RemoveAllItemsFromShoppingCart();
        Assert.assertTrue(errList.size() <=0, String.valueOf(errList));
        PrintToConfirmTestEnding();
    }

    @Test
    public void Test02_AddSeveralProductsToCart() {
        initChromeDriver();
        List<JSONObject> addedCarts = AddProductsToCartAndRetrieveTheirValues( 5, ConfigReader.get("standardUsername"));

        List<String> errList = FindExpectedCartInShoppingCartAndValidateItsData(addedCarts);

        shoppingCartPage.RemoveAllItemsFromShoppingCart();
        Assert.assertTrue(errList.size() <=0, String.valueOf(errList));
        PrintToConfirmTestEnding();
    }

    @Test
    public void Test03_CheckoutOnAddedProduct() {
        initChromeDriver();
        AddProductsToCartAndRetrieveTheirValues( 1, ConfigReader.get("standardUsername"));
        RetrieveAllCartsFromShoppingCart();
        shoppingCartPage.ClickOnCheckout();
        JSONObject checkoutData = new JSONObject("{" +
                "'firstName': 'John'," +
                "'lastName': 'Doe'," +
                "'postalCode': '123'" +
                "}");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        JSONObject checkoutPageCompleteData = checkoutPage.PerformCheckoutToAddedItem(checkoutData);

        JSONObject expectedCompleteTexts = new JSONObject("{" +
                "'completeHeader': '" + checkoutPage.expectedCompleteHeader + "'," +
                "'completeText': '" + checkoutPage.expectedCompleteText + "'," +
                "}");
        List<String> errList = ValidateExpectedAndActualData(expectedCompleteTexts, checkoutPageCompleteData, new ArrayList<>());
        Assert.assertTrue(errList.size() <=0, String.valueOf(errList));
        PrintToConfirmTestEnding();
    }

    @Test
    public void Test04_AttemptToLogInWithLockedUser() {
        initChromeDriver();
        LogInPage logInPage = new LogInPage(driver);
        String errorMessage = logInPage.NegativeLogIn(ConfigReader.get("lockedOutUsername"));
        Assert.assertTrue(errorMessage.equalsIgnoreCase(logInPage.expectedLockedUserError) ,
                "Error is " + errorMessage + " instead of " + logInPage.expectedLockedUserError);

        PrintToConfirmTestEnding();
    }

    public List<JSONObject> AddProductsToCartAndRetrieveTheirValues(Integer productsToAdd, String username) {
        LogInPage logInPage = new LogInPage(driver);
        logInPage.LogIn(username);

        productsPage = new ProductsPage(driver);
        List<JSONObject> addedCarts = productsPage.AddProductsToCartAndRetrieveTheirValues(productsToAdd);
        productsPage.ClickOnShoppingCart();
        return addedCarts;
    }

    public List<JSONObject> RetrieveAllCartsFromShoppingCart() {
        productsPage.ClickOnShoppingCart();
        shoppingCartPage = new ShoppingCartPage(driver);
        return shoppingCartPage.RetrieveAllItemsWithinShoppingCart();
    }

    public List<String> FindExpectedCartInShoppingCartAndValidateItsData(List<JSONObject> addedProducts) {
        List<JSONObject> itemsInShoppingCart = RetrieveAllCartsFromShoppingCart();

        List<String> errList = new ArrayList<>();
        if(addedProducts.size() != itemsInShoppingCart.size()) {
            errList.add("Invalid amount of carts within Shopping List. Expected - " + addedProducts.size() +
                    " Actual is - " + itemsInShoppingCart.size());
        }
        for(JSONObject expectedData: addedProducts) {
            boolean expectedCartWasFound = false;
            for(JSONObject actualData: itemsInShoppingCart) {
                if(actualData.getString("name").equalsIgnoreCase(expectedData.getString("name"))){
                    expectedCartWasFound = true;
                    errList = ValidateExpectedAndActualData(expectedData, actualData, errList);
                    break;
                }
            }
            if (!expectedCartWasFound) {
                errList.add(expectedData.getString("name") + " was not found within Cart Page");
            }
        }
        return errList;
    }

    public List<String> ValidateExpectedAndActualData(JSONObject expectedData, JSONObject actualData, List<String> errList) {
        for(String key : expectedData.keySet()) {
            String expectedValue = expectedData.getString(key).replace("'", "").trim();
            String actualValue = actualData.getString(key);
            if(!expectedValue.equalsIgnoreCase(actualValue)){
                errList.add("Error while validating " + key + ". Expected - " + expectedValue +
                        ". actual - " + actualValue);
            }
            else {
                System.out.println(key + ". is " + expectedValue + " as expected");
            }
        }
        System.out.println("----------------------------------------------------------");
        return errList;
    }

    public void PrintToConfirmTestEnding() {
        System.out.println("------------------Test has ended----------------------");
        System.out.println("----------------------------------------------------------");
        System.out.println("----------------------------------------------------------");
    }

}
