package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import resources.ApiResources;
import resources.PayloadClass;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class AddPaceStepDefinition extends Utils {
    ResponseSpecification resSpec;
    RequestSpecification res;
    Response response;
    PayloadClass pc = new PayloadClass();
    static String place_Id;


    @Given("Add place payload {string} {string} {string}")
    public void add_place_payload(String name, String language, String address) throws IOException {
        res = given().spec(requestSpecification()).body(pc.addPlace(name, language, address));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        ApiResources apiResources=ApiResources.valueOf(resource);
        System.out.println(apiResources.getResource());
        resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if(method.equalsIgnoreCase("POST")) {
            response = res.when().post(apiResources.getResource());
        }else if(method.equalsIgnoreCase("GET")){
            response = res.when().get(apiResources.getResource());
        }
    }

    @Then("the API call is success with status code is {int}")
    public void the_api_call_is_success_with_status_code_is(Integer int1) {
        response.getStatusCode();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body should be {string}")
    public void in_response_body_should_be(String keyValue, String expectedValue) {


        Assert.assertEquals(getJsonPath(response,keyValue), expectedValue);
    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {

        place_Id=getJsonPath(response,"place_id");
        res = given().spec(requestSpecification()).queryParam("place_id",place_Id);
        user_calls_with_http_request(resource,"GET");
        String actualName=getJsonPath(response,"name");
        Assert.assertEquals(actualName,expectedName);

    }

    @Given("Delete place payload")
    public void delete_place_payload() throws IOException {
        res = given().spec(requestSpecification()).body(pc.deletePlace(place_Id));
    }

}
