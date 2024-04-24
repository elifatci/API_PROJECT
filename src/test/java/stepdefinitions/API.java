package stepdefinitions;

import com.github.javafaker.Faker;
import config_Requirements.ConfigReader;
import hooks.HooksAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import utilities.API_Utilities.API_Methods;

import java.util.Arrays;
import java.util.HashMap;

import static hooks.HooksAPI.spec;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class API {
    public static int id;
    public static String fullPath;
    JSONObject requestBody;
    JsonPath jsonPath;
    HashMap<String, Object> reqBody;
    Faker faker = new Faker();
    @Given("The api user sets {string} path parameters")
    public void the_api_user_sets_path_parameters(String rawPaths) {
        String[] paths = rawPaths.split("/"); // [api, refundReasonList]

        System.out.println(Arrays.toString(paths));

        StringBuilder tempPath = new StringBuilder("/{");


        for (int i = 0; i < paths.length; i++) {

            String key = "pp" + i;
            String value = paths[i].trim();

            spec.pathParam(key, value);

            tempPath.append(key + "}/{");

            if (value.matches("\\d+")) {  // value.matches("\\d+") burada value rakam iceriyorsa dedik
                id = Integer.parseInt(value);
            }
        }
        tempPath.deleteCharAt(tempPath.lastIndexOf("/"));
        tempPath.deleteCharAt(tempPath.lastIndexOf("{"));

        fullPath = tempPath.toString();
        System.out.println("fullPath = " + fullPath); // /{pp0}/{pp1}
        System.out.println("id : " + id);
    }
    @Given("The api user constructs the base url with the {string} token.")
    public void the_api_user_constructs_the_base_url_with_the_token(String token) {
        HooksAPI.setUpApi(token);
    }
    @Given("The API user sends a GET request and records the response without body from the api endpoint.")
    public void the_apı_user_sends_a_get_request_and_records_the_response_without_body_from_the_api_endpoint() {
        API_Methods.getResponse();
    }
    @Given("The api user verifies that the status code is {int}")
    public void the_api_user_verifies_that_the_status_code_is(int code) {
        API_Methods.statusCodeAssert(code);
    }
    @Given("The api user verifies that the message information in the response body is {string}")
    public void the_api_user_verifies_that_the_message_information_in_the_response_body_is(String message) {
        API_Methods.messageAssert(message);
    }
    @Then("The api user verifies the content of the data {int}, {int}, {int}, {int}, {int} in the response body.")
    public void the_api_user_verifies_the_content_of_the_data_in_the_response_body(int wallet_running_balance, int wallet_pending_balance, int total_coupon, int total_wishlist, int total_cancel_order) {
        jsonPath = API_Methods.response.jsonPath();

        assertEquals(wallet_running_balance, jsonPath.getInt("wallet_running_balance"));
        assertEquals(wallet_pending_balance, jsonPath.getInt("wallet_pending_balance"));
        assertEquals(total_coupon, jsonPath.getInt("total_coupon"));
        assertEquals(total_wishlist, jsonPath.getInt("total_wishlist."));
        assertEquals(total_cancel_order, jsonPath.getInt("total_cancel_order"));
    }
    @Given("The API user records the response without body from the api endpoint, confirming that the status code is '401' and the reason phrase is Unauthorized.")
    public void the_apı_user_records_the_response_without_body_from_the_api_list_endpoint_confirming_that_the_status_code_is_and_the_reason_phrase_is_unauthorized() {

        assertTrue(API_Methods.tryCatchGet().equals(ConfigReader.getProperty("unauthorizedExceptionMessage", "api")));

    }
}
