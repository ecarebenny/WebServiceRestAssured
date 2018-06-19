package WebServiceUtilities;

import org.json.JSONException;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.AuthenticationScheme;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class Webservices {
	public static String authToken;
	
	public static Response Post(String uRI,String stringJSON){
		try
		{
		RequestSpecification requestSpecification = RestAssured.given().body(stringJSON);
		requestSpecification.contentType(ContentType.XML);
		Response response = requestSpecification.post(uRI);
		return response;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
	
	public static Response Get(String uRI){
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.get(uRI);
		return response;
	}
	
	public static Response Put(String uRI,String stringJSON){
		RequestSpecification requestSpecification = RestAssured.given().body(stringJSON);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.put(uRI);
		return response;
	}
	
	public static Response Delete(String uRI){
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.delete(uRI);
		return response;
	}

	public static Response PostCallWithHeader(String uRI,String stringJSON){
		try
		{
		RequestSpecification requestSpecification = RestAssured.given().body(stringJSON);
		requestSpecification.contentType(ContentType.JSON);
		requestSpecification.headers("Authorization", "Basic c3U6Z3c=");
		Response response = requestSpecification.post(uRI);
		return response;
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return null;
	}
	
	public static Response GetCallwithheader(String uRI){
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.headers("Authorization", authToken);
		requestSpecification.headers("companyid", 101);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.get(uRI);
		return response;
	}
	
	public static Response PutCallWithHeader(String uRI,String stringJSON){
		RequestSpecification requestSpecification = RestAssured.given().body(stringJSON);
		requestSpecification.headers("Authorization", authToken);
		requestSpecification.headers("companyid", 101);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.put(uRI);
		return response;
	}
	
	public static Response DeleteCallWitHeader(String uRI){
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.headers("Authorization", authToken);
		requestSpecification.headers("companyid", 101);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.delete(uRI);
		return response;
	}
	
	public static void loginToApplication(String uRI, String userName, String password) throws JSONException{
		RequestSpecification requestSpecification = RestAssured.given().auth().form(userName, password);
		Response response = requestSpecification.get(uRI);
		org.json.JSONObject jsonObject = new org.json.JSONObject(response);
		String auth = jsonObject.getString("authToken");
		authToken = auth;
	}
}
