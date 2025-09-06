package utilities;

import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v115.network.Network;
import org.openqa.selenium.devtools.v115.network.model.RequestId;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Optional;

public class TestActions {
    WebDriver driver;
    protected WebDriverWait wait;

    public TestActions(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement click(WebElement el, int timeout) {
        WaitForTheElementToBeEnabled(el, timeout);
        el.click();
        return el;
    }

    public void fillText(WebElement el, String text, int timeout) {
        for(int i=0; i<4; i++)
        {
            try {
                WaitForTheElementToBeEnabled(el, timeout);
                el.clear();
                el.sendKeys(text);
                return;
            }catch (StaleElementReferenceException e) {sleep(2000);}
        }

        WaitForTheElementToBeEnabled(el, timeout);
        el.clear();
        el.sendKeys(text);
    }

    public void checkCheckbox(WebElement el) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();" , el);
    }


    public void WaitForTheElementToBeEnabled(WebElement el, int timeout) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public void WaitForTheElementToBeEnabledByLocator(By el, int timeout) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public void sleep(long mills) {
        try {
            Thread.sleep(mills);
        }catch (Exception ex) {}
    }

    public String ClickAndRetrieveResponse(WebElement element, String expectedUrl, String includeInBody) {
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        try {
            click(element, 120);
        }catch (Exception ex) {}

        String requestId = "";
        for(int i=0; i<60; i++){
            requestId = RetrieveRequestUrl(expectedUrl);
            if(!requestId.isEmpty()) {
                break;
            }
            sleep(500);
        }

        Assert.assertFalse(requestId.isEmpty(), "No request with URL " + expectedUrl + " was found");
        String responseBody = RetrieveResponseBody(devTools, requestId, includeInBody);
        return responseBody;
    }

    public String RetrieveRequestUrl(String expectedUrl) {
        LogEntries logs = driver.manage().logs().get(LogType.PERFORMANCE);
        for (LogEntry log : logs) {
            String message = log.getMessage();
            JSONObject json = new JSONObject(message);
            String requestUrl = "";
            String requestId = "";
            try {
                JSONObject reqMessage = ((JSONObject) json.get("message"));
                if (!reqMessage.getString("method").equals("Network.requestWillBeSent") &&
                        !reqMessage.getString("method").equals("Network.responseReceived")) {

                    continue;
                }
                JSONObject reqParams = ((JSONObject) reqMessage.get("params"));
                JSONObject request = ((JSONObject) reqParams.get("request"));

                requestUrl = request.getString("url");
                requestId = reqParams.getString("requestId");
                if(requestUrl.endsWith(expectedUrl)) {
                    System.out.println("Found URL - " + requestUrl);
                    return requestId;
                }
            }catch (Exception ex) {}
        }
        return "";
    }

    public String RetrieveResponseBody(DevTools devTools, String requestId, String includeInBody) {
        Network.GetResponseBodyResponse response = null;
        for(int i=0; i<30; i++) {
            try{
                response = devTools.send(Network.getResponseBody(new RequestId(requestId)));
                String responseString = response.getBody() == null ? "" : response.getBody();
                if (!responseString.isEmpty()){
                    Assert.assertTrue(responseString.contains(includeInBody),
                            "Response body is " + responseString);

                    return responseString;
                }
            } catch (Exception ex) {
                System.out.println("Not found. We'll try again");
                sleep(1000);
            }
        }
        return "";
    }
}
