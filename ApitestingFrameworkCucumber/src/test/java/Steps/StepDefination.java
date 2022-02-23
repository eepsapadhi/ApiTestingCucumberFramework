package Steps;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.constant.Endpoints;
import com.test.helpers.ReusableMethods;
import com.test.helpers.UserServiceHelper;
import com.test.models.AddUserPOJO;
import com.test.models.DeleteUserPOJO;
import com.test.models.LoginObjectPOJO;
import com.test.models.UpdateUserPOJO;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class StepDefination extends UserServiceHelper {

	Response res;
	String token;

	@Given("user is on login page and log in")
	public void user_is_on_login_page_and_log_in() {
		LoginObjectPOJO ob = new LoginObjectPOJO(username, password);
		res = RestAssured.given().contentType("application/json").body(ob).when().post(getbaseURI() + Endpoints.LOGIN);

	}

	@Then("verify the Statuscode")
	public void verify_the_statuscode() {
		// System.out.println(res.statusCode());
		Assert.assertEquals(201, res.getStatusCode());
	}

	@Then("user get the token")
	public void user_get_the_token() {
		token = res.jsonPath().get("[0].token");
		// System.out.println(token);
		HashMap<String, String> hm = ReusableMethods.getHeaderForAuth(token);

	}

	@Then("user get the data")
	public void user_get_the_data() {

		res = RestAssured.given().header("token", token).when().contentType("application/json")
				.get(getbaseURI() + Endpoints.GET_DATA);

	}

	@Then("verify the bodycontains")
	public void verify_the_bodycontains() {
		ResponseBody body = res.getBody();
		String bodyAsString = body.asString();
		System.out.println(" BODY=" + bodyAsString);
		Assert.assertEquals(bodyAsString.contains("TA-9222221"), true, "Response body does not contains 9222221");

	}

	@Then("user add the data")
	public void user_add_the_data() throws StreamReadException, DatabindException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String fName = System.getProperty("user.dir") + "/src/test/resources/jsonFiles/addUser";
		AddUserPOJO ob = objectMapper.readValue(new File(fName), AddUserPOJO.class);

		HashMap<String, String> hm = ReusableMethods.getHeaderForAuth(token);
		res = RestAssured.given().headers(hm).body(ob).when().contentType("application/json")
				.post(getbaseURI() + Endpoints.ADD_DATA);
	}

	@Then("user update the data")
	public void user_update_the_data() throws StreamReadException, DatabindException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String fName = System.getProperty("user.dir") + "/src/test/resources/jsonFiles/updateuser";
		UpdateUserPOJO ob = objectMapper.readValue(new File(fName), UpdateUserPOJO.class);

		HashMap<String, String> hm = ReusableMethods.getHeaderForAuth(token);

		res = RestAssured.given().headers(hm).body(ob).when().contentType("application/json")
				.put(getbaseURI() + Endpoints.UPDATE_DATA);
	}

	@Then("verify the  updated Statuscode")
	public void verify_the_updated_statuscode() {

		Assert.assertEquals(200, res.getStatusCode());
	}

	@Then("user delete the data")
	public void user_delete_the_data() throws StreamReadException, DatabindException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String fName = System.getProperty("user.dir") + "/src/test/resources/jsonFiles/deleteuser";
		DeleteUserPOJO ob = objectMapper.readValue(new File(fName), DeleteUserPOJO.class);

		HashMap<String, String> hm = ReusableMethods.getHeaderForAuth(token);

		res = RestAssured.given().headers(hm).body(ob).when().contentType("application/json")
				.delete(getbaseURI() + Endpoints.DELETE_DATA);
	}

	@Then("verify the  deleted Statuscode")
	public void verify_the_deleted_statuscode() {
		Assert.assertEquals(401, res.getStatusCode());
	}

}
