package RestAssured;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import PojoClasses.Serialization.Location;
import PojoClasses.Serialization.PlaceAPI;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class RestAPICalls {
	
	private String BaseURI;
	RestAPICalls(String BaseURI){
		this.BaseURI=BaseURI;
	}
	public HashMap<Integer,String> getResponseCodeAndResponseforQueryParam(Map<String,String> map,String URI){
		
		Response response=given().header("Cotent-Type", "application/json").queryParams(map).baseUri(BaseURI).when().get(URI);
		HashMap<Integer,String> maps= new HashMap<Integer,String>();
		maps.put(response.getStatusCode(), response.getBody().asString());
		return maps;
	}
	
public HashMap<Integer,String> getResponseCodeAndResponseforPathParam(Map<String,String> map,String URI){
		
		Response response=given().header("Cotent-Type", "application/json").pathParams(map).baseUri(BaseURI).when().get(URI);
		HashMap<Integer,String> maps= new HashMap<Integer,String>();
		maps.put(response.getStatusCode(), response.getBody().asString());
		return maps;
	}
public Object PostRequestwithQueryParam(Map<String,String> map,String URI,String Json){
	
	Response response=given().header("Cotent-Type", "application/json").queryParams(map).baseUri(BaseURI).body(Json).log().all().when().post(URI);
	return !response.getBody().asString().isEmpty()?response.getBody().asString():response.statusCode();
}

public Object PostRequestwithQueryParamwithPOJO(Map<String,String> map,String URI,Object obj){
	
	Response response=given().header("Cotent-Type", "application/json").queryParams(map).baseUri(BaseURI).body(obj).log().all().when().post(URI);
	return !response.getBody().asString().isEmpty()?response.getBody().asString():response.statusCode();
}

public Object PostRequestwithQueryParam(Map<String,String> map,String URI,Object obj){
	
	Response response=given().header("Cotent-Type", "application/json").queryParams(map).baseUri(BaseURI).body(obj).log().all().when().post(URI);
	return response;
}

public Object PostRequestwithPathParam(Map<String,String> map,String URI,String Json){
	
	Response response=given().header("Cotent-Type", "application/json").pathParams(map).baseUri(BaseURI).body(Json).when().post(URI);
	return response.getBody().asString().isBlank()==false?response.getBody().asString():response.statusCode();
}

public void JsonSchemaValidator(Response response,String PayLoad) {
	
	response.then().assertThat().body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema(PayLoad));
	
}

public Object PostRequestwithRequestSpecificBuilder(Map<String,String> map,String URI,String Json){
	
	Response response=given().spec(requestSpecification).body(Json).when().post(URI);
	return response.getBody().asString().isBlank()==false?response.getBody().asString():response.statusCode();
}

public String getRandomAlphanumeric(int lengthOfString) {
	
	Random ran=new Random();
	ran.nextInt();
	String Alphabets="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ0123456789";
	String random="";
	for(int i=0;i<lengthOfString;i++)
		random+=Alphabets.charAt(ran.nextInt(Alphabets.length()-1));
		
	return random;
	
	
}

public RequestSpecification getRequestSpecBuilder() {
	
	return new RequestSpecBuilder().setContentType(ContentType.JSON)
			                                       .setBaseUri(BaseURI)
			                                       .addQueryParam("key", "qaclick123")
			                                       .build();
}

public ResponseSpecification getRequestSpecBuilder(int code) {
	
	return new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(code).build();
}



public static void main(String[] args) throws IOException {
	
	RestAPICalls rest=new RestAPICalls("https://rahulshettyacademy.com");
	Map<String,String> map=new HashMap<>();
	map.put("key", "qaclick123");
	String Payload=new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\main\\resources\\PayLoad\\place.json")));
	Payload=Payload.replaceFirst("<[A-z a-z]+>", rest.getRandomAlphanumeric(15));
	Object code=rest.PostRequestwithQueryParam(map,"maps/api/place/add/json", Payload);
	System.out.println(code);
	
	
	
	//same request using POJO class Serialization 
	Random ran=new Random();
	PlaceAPI place=new PlaceAPI();
	Location location=new Location();
	location.setLat(33.2564);
	location.setLng(34.256);
	place.setAccuracy(ran.nextInt(10,99));
	place.setAddress(rest.getRandomAlphanumeric(15));
	place.setLanguage("EN");
	place.setName(rest.getRandomAlphanumeric(15));
	place.setPhone_number(ran.nextInt(78000000,789999999));
	List<String> list=new ArrayList<>();
	list.addAll(Arrays.asList(new String[] {"shoe park","shop"}));
	place.setTypes(list);
	place.setWebsite("http://rahulshettyacademy.com");
	place.setLocation(location);
	code=rest.PostRequestwithQueryParamwithPOJO(map,"maps/api/place/add/json", place);
	System.out.println(code);
	
	//Json Schema Validator
	
	code=rest.PostRequestwithQueryParam(map,"maps/api/place/add/json", place);
	rest.JsonSchemaValidator((Response)code, new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\main\\resources\\PayLoad\\placeSchema.json"))));
	
	
	
}




}
