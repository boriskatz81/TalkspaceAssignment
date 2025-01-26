package CodeImprovementAssignment;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Base64;

public class LoginAndProductPage {

    public LoginAndProductPage(WebDriver driver) {
        this.driver = driver;
    }

    private String username;
    private String password;

    protected WebDriverWait wait;
    private WebDriver driver;



    @FindBy(id = "file-upload")
    public WebElement fileUpload;

    @FindBy(xpath = "//button[contains(text(),'Upload')]")
    public WebElement uploadButton;



    @FindBy(id = "username")
    public WebElement usernameInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(css = ".register-btn")
    public WebElement loginButton;



    @FindBy(id = "reg-username")
    public WebElement regUsername;

    @FindBy(id = "reg-email")
    public WebElement regEmail;

    @FindBy(id = "reg-password")
    public WebElement regPassword;

    @FindBy(xpath = "//a[contains(text(),'Register')]")
    public WebElement registerButton;


    @FindBy(name = "search")
    public WebElement searchInput;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    public WebElement searchButton;



    public String productElementXPath = "//div[@class='product-item'][1]";

    public void uploadProductImage() {
        Path absoluteFilePath = Path.of(System.getProperty("user.home"), "Documents", "product_image.jpg");
        fill(fileUpload , absoluteFilePath.toString());
        System.out.println("Uploaded file from: " + absoluteFilePath);
        click(uploadButton);
    }

    public void logIn(String userNameKey, String passwordKey) {
        SetUsernameAndPasswordFromAws(userNameKey, passwordKey);
        fill(usernameInput, username);
        fill(passwordInput, password);
        click(loginButton);
    }

    public void loginUser() {
        System.out.println("Attempting to login");
        logIn("hardcoded_username", "hardcoded_password");
    }

    public void CreateNewUser() {
        click(registerButton);
        fill(regUsername, "newuser" + System.currentTimeMillis());
        fill(regEmail, "newuser" + System.currentTimeMillis() + "@example.com");
        fill(regPassword, "Temporary123!");
        click(loginButton);
        System.out.println("Attempting to create new user");
    }

    public void doSomethingWithProduct() {
        WebElement productElement = driver.findElement(By.xpath(productElementXPath));
        click(productElement);
        System.out.println("Product selected: " + productElement.getText());
    }

    public void loginOrRegister(boolean createNewUser) {
        if (createNewUser) {
            CreateNewUser();
        } else {
            logIn("defaultuser", "password123");
        }
    }

    public void processProduct(String productName) {
        fill(searchInput, productName);
        click(searchButton);
    }

    public void click(WebElement el) {
        WaitForTheElementToBeEnabled(el);
        el.click();
    }

    public void fill(WebElement el, String text) {
        WaitForTheElementToBeEnabled(el);
        try {el.clear();} catch (Exception ex) {}
        el.sendKeys(text);
    }

    public void WaitForTheElementToBeEnabled(WebElement el) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        wait.until(ExpectedConditions.elementToBeClickable(el));
        Assert.assertTrue(el.isEnabled());
    }

    public void SetUsernameAndPasswordFromAws(String usernameKey, String passwordKey) {
        String region = "my-region";
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId("my-id");

        GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        if (getSecretValueResult.getSecretString() != null) {
            JSONObject jsonObject = new JSONObject(getSecretValueResult.getSecretString());
            username = jsonObject.getString(usernameKey);
            password = jsonObject.getString(passwordKey);
        } else {
            String decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
            JSONObject jsonObject = new JSONObject(decodedBinarySecret);
            username = jsonObject.getString(usernameKey);
            password = jsonObject.getString(passwordKey);
        }
    }
}
