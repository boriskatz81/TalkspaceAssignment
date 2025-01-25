package saucedemoTests;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CheckoutPage;
import pageObjects.LogInPage;
import pageObjects.ProductsPage;
import pageObjects.ShoppingCartPage;
import utilities.Base;

import java.util.ArrayList;
import java.util.List;

public class TestingSaucedemo extends Base {
    ProductsPage productsPage;
    ShoppingCartPage shoppingCartPage;


    @Test
    public void Test01_AddProductToChat() {
        initChromeDriver();
        List<JSONObject> addedCarts = AddProductsToCart( 1, "standard_user");

        List<JSONObject> shoppingListCarts = RetrieveAllCartsFromShoppingCart();
        List<String> errList = FindExpectedCartAndValidateItsData(addedCarts, shoppingListCarts);

        shoppingCartPage.RemoveAllItemsFromShoppingCart();
        Assert.assertTrue(errList.size() <=0, String.valueOf(errList));
        PrintToConfirmTestEnding();
    }

    @Test
    public void Test02_AddSeveralProductsToChat() {
        initChromeDriver();
        List<JSONObject> addedCarts = AddProductsToCart( 5, "standard_user");

        List<JSONObject> shoppingListCarts = RetrieveAllCartsFromShoppingCart();
        List<String> errList = FindExpectedCartAndValidateItsData(addedCarts, shoppingListCarts);

        shoppingCartPage.RemoveAllItemsFromShoppingCart();
        Assert.assertTrue(errList.size() <=0, String.valueOf(errList));
        PrintToConfirmTestEnding();
    }

    @Test
    public void Test03_CheckoutOnAddedProduct() {
        initChromeDriver();
        AddProductsToCart( 1, "standard_user");
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
        String errorMessage = logInPage.NegativeLogIn("locked_out_user");
        Assert.assertTrue(errorMessage.equalsIgnoreCase(logInPage.expectedLockedUserError) ,
                "Error is " + errorMessage + " instead of " + logInPage.expectedLockedUserError);

        PrintToConfirmTestEnding();
    }

    public List<JSONObject> AddProductsToCart(Integer productsToAdd, String username) {
        productsPage = new ProductsPage(driver);
        LogInPage logInPage = new LogInPage(driver);
        logInPage.LogIn(username);

        List<JSONObject> addedCarts = productsPage.AddProductsToCart(productsToAdd);
        productsPage.ClickOnShoppingCart();
        return addedCarts;
    }

    public List<JSONObject> RetrieveAllCartsFromShoppingCart() {
        productsPage.ClickOnShoppingCart();
        shoppingCartPage = new ShoppingCartPage(driver);
        return shoppingCartPage.RetrieveAllItemsWithinShoppingCart();
    }

    public List<String> FindExpectedCartAndValidateItsData(List<JSONObject> expectedValues, List<JSONObject> actualValues) {
        List<String> errList = new ArrayList<>();
        if(expectedValues.size() != actualValues.size()) {
            errList.add("Invalid amount of carts within Shopping List. Expected - " + expectedValues.size() +
                    " Actual is - " + actualValues.size());
        }
        for(JSONObject expectedData: expectedValues) {
            boolean expectedCartWasFound = false;
            for(JSONObject actualData: actualValues) {
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
