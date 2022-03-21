package IBM_Haldia.Rest_Assured;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.pojoclass;

public class localhostApi {

	

	@Test
	public void creatingUser(ITestContext val) throws JsonProcessingException
	{
		try
		{
			io.restassured.RestAssured.baseURI = "http://localhost:3000";		
			
			pojoclass data = new pojoclass();
			
			data.setEmail("api@test.com");
			data.setFirstName("APIP");
			data.setLastName("Testering");
			data.setPassword("iamtestin234");
			data.setPhone("9525486010");
			data.setUsername("apiter");
			data.setUserStatus("0");
			
			ObjectMapper objmapper = new ObjectMapper();
			
			
//			System.out.println(objmapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
			
			
		Response resp =						given()
										.contentType(ContentType.JSON)
										.body(objmapper.writeValueAsString(data)).
								when()
										.post("/userdetails").
								then()
										.log().all()
										.statusCode(201).extract().response();
		
			String id = resp.jsonPath().getString("id");
			val.setAttribute("id", id);
			
//			System.out.println(resp.asString()); 
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

			
			io.restassured.RestAssured.baseURI = "http://localhost:3000";		
			
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
										.put("/userdetails/"+val.getAttribute("id")).
								then()
										.statusCode(200);
									
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		
	}
	
	@Test (dependsOnMethods = "modifyingUser" )
	public void getafteredit(ITestContext val)
	{
		
		try
		{
		
			io.restassured.RestAssured.baseURI = "http://localhost:3000";		
			
			given()
//					.queryParam("username", val.getAttribute("username"))
					.get("/userdetails/"+val.getAttribute("id")).
			then()
					.statusCode(200)
					.log().all();			


			
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}
	

	
	@Test (dependsOnMethods = "getafteredit" )
	public void delete(ITestContext val)
	{
		try
		{
			
		io.restassured.RestAssured.baseURI = "http://localhost:3000";		
//		String id = val.getAttribute("id").toString();
		given()
//				.delete("/userdetails" + id).
				.delete("/userdetails/" + val.getAttribute("id").toString()).
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
