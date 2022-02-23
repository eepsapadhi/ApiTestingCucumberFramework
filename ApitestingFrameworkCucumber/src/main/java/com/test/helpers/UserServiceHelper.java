package com.test.helpers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.constant.Endpoints;
import com.test.models.AddUserPOJO;
import com.test.models.DeleteUserPOJO;
import com.test.models.LoginObjectPOJO;
import com.test.models.UpdateUserPOJO;
import com.test.utils.Utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserServiceHelper {

	private static final String BASE_URL = Utils.getInstance().geString("base_url");
	protected static final String username = Utils.getInstance().geString("username");
	protected static final String password = Utils.getInstance().geString("password");

	public static String getbaseURI() {
		return BASE_URL;

	}

	public static Response LogintoApp() {

		LoginObjectPOJO ob = new LoginObjectPOJO(username, password);
		Response response;
		response = RestAssured.given().contentType("application/json").body(ob).when()
				.post(getbaseURI() + Endpoints.LOGIN);
		return response;

	}

	public static String gettoken(Response response) {
		String token = response.jsonPath().get("[0].token");
		//System.out.println(token);
		return token;
	}

	public static Response addUser() throws StreamReadException, DatabindException, IOException {
		Response response = LogintoApp();
		String token = gettoken(response);
		ObjectMapper objectMapper = new ObjectMapper();
		String fName = System.getProperty("user.dir") + "/src/test/resources/jsonFiles/addUser";
		AddUserPOJO ob = objectMapper.readValue(new File(fName), AddUserPOJO.class);

		HashMap<String, String> hm = ReusableMethods.getHeaderForAuth(token);
		
		Response response1;
		response1 = RestAssured.given().headers(hm).body(ob).when().contentType("application/json")
				.post(getbaseURI() + Endpoints.ADD_DATA);
		//System.out.println(response1.getHeaders().toString());
		return response1;

	}
	public static Response getuser() throws StreamReadException, DatabindException, IOException {
		Response response = LogintoApp();
		String token = gettoken(response);
		

		HashMap<String, String> hm = ReusableMethods.getHeaderForAuth(token);
		
		Response response1;
//		response1 = RestAssured.given().headers(hm).when().contentType("application/json")
//				.get(getbaseURI() + Endpoints.GET_DATA);
		response1 = RestAssured.given().header("token",token).when().contentType("application/json")
				.get(getbaseURI() + Endpoints.GET_DATA);
		//System.out.println(response.getHeaders().toString());
		return response1;
	}
	public static Response getupdate() throws StreamReadException, DatabindException, IOException {
		Response response = LogintoApp();
		String token = gettoken(response);
		ObjectMapper objectMapper = new ObjectMapper();
		String fName = System.getProperty("user.dir") + "/src/test/resources/jsonFiles/updateuser";
		UpdateUserPOJO ob = objectMapper.readValue(new File(fName), UpdateUserPOJO.class);

		HashMap<String, String> hm = ReusableMethods.getHeaderForAuth(token);
		
		Response response1;
		response1 = RestAssured.given().headers(hm).body(ob).when().contentType("application/json")
				.put(getbaseURI() + Endpoints.UPDATE_DATA);
		//System.out.println(response1.getHeaders().toString());
		return response1;

	}


	public static Response Deleteuser() throws StreamReadException, DatabindException, IOException {
		Response response = LogintoApp();
		String token = gettoken(response);
		ObjectMapper objectMapper = new ObjectMapper();
		String fName = System.getProperty("user.dir") + "/src/test/resources/jsonFiles/deleteuser";
		DeleteUserPOJO ob = objectMapper.readValue(new File(fName), DeleteUserPOJO.class);

		HashMap<String, String> hm = ReusableMethods.getHeaderForAuth(token);
		
		Response response1;
		response1 = RestAssured.given().headers(hm).body(ob).when().contentType("application/json")
				.delete(getbaseURI() + Endpoints.DELETE_DATA);
		//System.out.println(response1.getHeaders().toString());
		return response1;
	}

}