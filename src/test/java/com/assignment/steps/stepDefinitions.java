package com.assignment.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import pageObjects.*;
import utilities.Base;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class stepDefinitions {

    public WebDriver driver;
    public PublishersPage publishersPage;
    public PostsPage postsPage;
    public NewPostPage newPostPage;

    JSONObject publisherData = null;
    JSONObject postData = null;

    @Given("I navigate to {string} and log in with username {string} and password {string}")
    public void navigateToUrlAndLogIn(String url, String username, String password) {
        driver = Base.driver;
        LogInPage logInPage = new LogInPage(driver);
        logInPage.LogIn(url, username, password);
    }

    @When("I enter Publishers Page")
    public void moveToPublishersPage() {
        publishersPage = new PublishersPage(driver);
        publishersPage.OpenPublishersPage();
    }

    @When("I enter Posts Page")
    public void moveToPostsPage() {
        postsPage = new PostsPage(driver);
        postsPage.OpenPostsPage();
    }

    @When("I create a new Publisher with name={string} and email={string}")
    public void CreateNewPublisher(String name, String email){
        publishersPage.OpenNewPublisherPage();
        NewPublisherPage newPublisherPage = new NewPublisherPage(driver);

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String timeString = now.format(formatter);
        name = name + timeString;
        email = email.replace("{replace_with_time}", timeString);
        publisherData =  newPublisherPage.CreateNewPublisher(name, email);
    }

    @When("I validate Publishers Table Data")
    public void ValidatePublishersTableData(){
        publishersPage.ValidateTableData(publisherData);
    }


    @When("I create a new Post with title={string} content={string} status={string}")
    public void CreateNewPost(String title, String content, String status){
        postsPage.OpenNewPostPage();
        newPostPage = new NewPostPage(driver);

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String timeString = now.format(formatter);
        title = title + timeString;
        content = content + timeString;
        postData =  newPostPage.CreateNewPost(title, content, status, publisherData);
    }

    @When("I validate Posts Table Data")
    public void ValidatePostsTableData(){
        JSONObject expectedTableData = new JSONObject("{\"title\":\"" + postData.getString("title") + "\"," +
                "\"id\":\"" + postData.get("id").toString() + "\"," +
                "\"content\":\"" + postData.getString("content") + "\"," +
                "\"published\":\"Yes\"," +
                "\"status\":\"" + postData.getString("status") + "\"," +
                "}");
        postsPage.ValidateTableData(expectedTableData);
    }

    @When("I open Post Edit Page")
    public void OpenEditPage(){
        postsPage.OpenPostEditPage();
    }

    @When("I Edit Status to {string}")
    public void EditStatus(String status){
        postData.put("status", status);
        newPostPage.SetPostStatus(status);
    }

    @When("I Save Edited Changes")
    public void SaveEditedChanges(){
        newPostPage.SaveNewPost(true);
    }
}
