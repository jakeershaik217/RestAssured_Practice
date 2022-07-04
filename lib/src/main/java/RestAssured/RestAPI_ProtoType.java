package RestAssured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAPI_ProtoType {
	
	public RestAPI_ProtoType() {
		RestAssured.baseURI="https://mail.google.com/";
	}
	
	public RequestSpecBuilder getReuqestBuilder() {
		RequestSpecBuilder res=new RequestSpecBuilder();
		res.setAccept(ContentType.JSON).addQueryParam("q", "Jakeer");
		return res;

		
	}
	
public static void main(String[] args) {
	RestAPI_ProtoType rn=new RestAPI_ProtoType();
	rn.getReuqestBuilder();
	
}	
	

}
