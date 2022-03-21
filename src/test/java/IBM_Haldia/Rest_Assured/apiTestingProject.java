package IBM_Haldia.Rest_Assured;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.pojoclass;

import static io.restassured.RestAssured.given;

public class apiTestingProject {

	@Test
	public void creatingUser(ITestContext val) throws JsonProcessingException
	{
		
		try
		
		{
		
		io.restassured.RestAssured.baseURI = "https://petstore.swagger.io/v2";		
		
		pojoclass data = new pojoclass();
		
		data.setEmail("api@test.com");
		data.setFirstName("APIP");
		data.setLastName("Testering");
		data.setPassword("iamtestin234");
		data.setPhone("9525486010");
		data.setUsername("apiter");
		data.setUserStatus("0");
		
		ObjectMapper objmapper = new ObjectMapper();
		
		
//		System.out.println(objmapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
		
		
							given()
									.contentType(ContentType.JSON)
									.body(objmapper.writeValueAsString(data)).
							when()
									.post("/user").
							then()
									.statusCode(200).log().all();
	
		String usrnm = data.getUsername();
		val.setAttribute("username", usrnm);
		System.out.println(usrnm);
		
		
		String pswrd = data.getPassword();
		val.setAttribute("password", pswrd);
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}
	
	
	
	
	@Test (dependsOnMethods = "creatingUser" )
	public void modifyingUser(ITestContext val)
	{
		try
		
		{
		
		io.restassured.RestAssured.baseURI = "https://petstore.swagger.io/v2";		
		
		JSONObject userdetails = new JSONObject();
		userdetails.put("username", "apitester");
		userdetails.put("firstName", "API");
		userdetails.put("lastName", "Tester");
		userdetails.put("email", "api@testing.com");
		userdetails.put("password", "iamtestingapis1234");
		userdetails.put("phone", "9525486010");
		userdetails.put("userStatus", 1);
		
		
							given()
									.contentType(ContentType.JSON)
									.body(userdetails.toJSONString()).
							when()
									.put("/user/"+val.getAttribute("username")).
							then()
									.statusCode(200)
									.log().all();
		
							
		}
		
		catch(Exception e)
		
		{
			System.out.println(e);
		}
		
		
	}
	
	@Test (dependsOnMethods = "modifyingUser" )
	public void login(ITestContext val)
	{
		try
		
		{
		
		io.restassured.RestAssured.baseURI = "https://petstore.swagger.io/v2";		
		
							given()
									.queryParam("username", val.getAttribute("username"))
									.get("/user/login").
							then()
									.statusCode(200)
									.log().all();			
		}
		
		catch(Exception e)
		
		{
			System.out.println(e);
		}
		
	}
	
	@Test (dependsOnMethods = "login" )
	public void logout()
	{
		
		try
		
		{
		
		io.restassured.RestAssured.baseURI = "https://petstore.swagger.io/v2";		
		
		given()
				.get("/user/logout").
		then()
				.statusCode(200)
				.log().all();
		
		}
		
		catch(Exception e)
		
		{
			System.out.println(e);
		}
		
	}
	
	@Test (dependsOnMethods = "logout" )
	public void delete(ITestContext val)
	{
		
		try
		
		{
		
		io.restassured.RestAssured.baseURI = "https://petstore.swagger.io/v2";		
		String username = val.getAttribute("username").toString();
		given()
				.delete("/user/" + username).
//				.delete("/user/" + val.getAttribute("username").toString()).
		then()
				.statusCode(200)
				.log().all();
		
		}
		
		catch(Exception e)
		
		{
			System.out.println(e);
		}
		
		
	}
	
	
	
}
