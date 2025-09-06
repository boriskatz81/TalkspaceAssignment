package utilities;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import pageObjects.BasePage;

import java.util.ArrayList;
import java.util.List;

public class ApiRequests {


    public static RequestSpecification prepareApiDefault(String requestUrl, String cookie) {
        Header h1 = new Header("Content-Type", "application/json; charset=utf-8");
        Header h2 = new Header("Cookie", "adminjs=" + cookie);
        List<Header> list = new ArrayList<>();
        list.add(h1);
        list.add(h2);
        Headers headers = new Headers(list);

        RestAssured.baseURI = requestUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.headers(headers);
        return httpRequest;
    }

    public static void ValidateRetrievedStatus(int status) {
        Assert.assertEquals(status, 200, "Expected status is 200 but we got " + status);
    }

    public static JSONObject createNewPublisher(String body, String cookie) {
        String requestUrl = "http://localhost:3000/admin/api/resources/Publisher/actions/new";
        RequestSpecification httpRequest = prepareApiDefault(requestUrl, cookie);
        httpRequest.body(body);

        Response response = httpRequest.post(requestUrl);
        ValidateRetrievedStatus(response.statusCode());
        return new JSONObject(response.getBody().asString()).getJSONObject("record").getJSONObject("params");
    }

    public static void validatePublisherWasSuccessfullyCreated(JSONObject publisherData, String cookie){
        String requestUrl = "http://localhost:3000/admin/api/resources/Publisher/actions/list?filters.id=" + publisherData.getString("id") + "&page=1";
        RequestSpecification httpRequest = prepareApiDefault(requestUrl, cookie);
        Response response = httpRequest.get(requestUrl);
        ValidateRetrievedStatus(response.statusCode());

        JSONObject getParams =
                new JSONObject(response.getBody().asString()).getJSONArray("records").getJSONObject(0).getJSONObject("params");

        BasePage.ValidateAndExpectedData(publisherData, getParams);
    }


    public static JSONObject createNewPost(String body, String cookie) {
        String requestUrl = "http://localhost:3000/admin/api/resources/Post/actions/new";
        RequestSpecification httpRequest = prepareApiDefault(requestUrl, cookie);
        httpRequest.body(body);

        Response response = httpRequest.post(requestUrl);
        ValidateRetrievedStatus(response.statusCode());
        return new JSONObject(response.getBody().asString()).getJSONObject("record").getJSONObject("params");
    }

    public static void validatePostWasSuccessfullyCreated(JSONObject postData, String cookie){
        String requestUrl = "http://localhost:3000/admin/api/resources/Post/actions/list?filters.id=" + postData.getString("id") +"&page=1";
        RequestSpecification httpRequest = prepareApiDefault(requestUrl, cookie);
        Response response = httpRequest.get(requestUrl);
        ValidateRetrievedStatus(response.statusCode());

        JSONObject getParams =
                new JSONObject(response.getBody().asString()).getJSONArray("records").getJSONObject(0).getJSONObject("params");

        BasePage.ValidateAndExpectedData(postData, getParams);
    }

    public static JSONObject updatePost(String body, String id, String cookie) {
        String requestUrl = "http://localhost:3000/admin/api/resources/Post/records/" + id + "/edit";
        RequestSpecification httpRequest = prepareApiDefault(requestUrl, cookie);
        httpRequest.body(body);

        Response response = httpRequest.post(requestUrl);
        ValidateRetrievedStatus(response.statusCode());
        return new JSONObject(response.getBody().asString()).getJSONObject("record").getJSONObject("params");
    }
}
