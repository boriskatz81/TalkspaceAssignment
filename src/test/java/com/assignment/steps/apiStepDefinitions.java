package com.assignment.steps;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import utilities.ApiRequests;
import utilities.Base;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class apiStepDefinitions {

    String cookieValue = "";
    JSONObject publisherApiData = null;
    JSONObject postApiData = null;


    @When("I create a new API Publisher with name={string} and email={string}")
    public void CreateNewAPIPublisher(String name, String email){
        WebDriver driver = Base.driver;
        Cookie sessionCookie = driver.manage().getCookieNamed("adminjs");
        cookieValue = sessionCookie.getValue();

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String timeString = now.format(formatter);
        name = name + timeString;
        email = email.replace("{replace_with_time}", timeString);

        String bodyString = "{\"name\":\"" + name + "\"," +
                "\"email\":\"" + email + "\"" +
                "}";

        publisherApiData = ApiRequests.createNewPublisher(bodyString, cookieValue);

        JSONObject expectedData = new JSONObject("{\"name\":\"" + name + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"id\":\"" + publisherApiData.get("id").toString() + "\"," +
                "}");

        ApiRequests.validatePublisherWasSuccessfullyCreated(expectedData, cookieValue);
    }


    @When("I create a new API Post with title={string} and content={string} and status={string}")
    public void CreateNewAPIPost(String title, String content, String status){
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String timeString = now.format(formatter);
        title = title + timeString;
        content = content + timeString;

        String bodyString = "{\"title\":\"" + title + "\"," +
                "\"content\":\"" + content + "\"," +
                "\"status\":\"" + status + "\"," +

                "\"someJson.0.number\":\"123\"," +
                "\"someJson.0.string\":\"abc\"," +

                "\"publisher\":" + publisherApiData.get("id") + "," +
                "\"published\":true" +
                "}";

        postApiData = ApiRequests.createNewPost(bodyString, cookieValue);

        JSONObject expectedData = new JSONObject("{\"title\":\"" + title + "\"," +
                "\"content\":\"" + content + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"id\":\"" + postApiData.get("id").toString() + "\"," +
                "\"publisher\":\"" + publisherApiData.get("id").toString() + "\"," +
                "\"published\":\"true\"," +
                "}");

        ApiRequests.validatePostWasSuccessfullyCreated(expectedData, cookieValue);
    }

    @When("I update {string} to {string}")
    public void updateDataOfAnExistingPost(String keyToPost, String valueToPost) {
        postApiData.put(keyToPost, valueToPost);
        ApiRequests.updatePost(postApiData.toString(), postApiData.get("id").toString(), cookieValue);

        JSONObject expectedData = new JSONObject(){};
        for(String key : postApiData.keySet()) {
            if(!key.contains("someJson.") && !key.endsWith("At")) {
                expectedData.put(key, postApiData.get(key).toString());
            }
        }
        ApiRequests.validatePostWasSuccessfullyCreated(expectedData, cookieValue);
    }

}
