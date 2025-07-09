package util;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.config.HttpClientConfig;

import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;

import files.getPayLoad;




public class commonMethods{

    /**
     * Get base URL from YAML configuration for the current environment
     */
    public static String getBaseUrl() {
        String env = System.getProperty("env", "test");
        Map<String, Object> envConfig = ConfigManager.getEnvironmentConfig(env);
        return envConfig != null ? envConfig.get("baseUrl").toString() : null;
    }
    
    /**
     * Get API key from YAML configuration for the current environment
     */
    public static String getApiKey() {
        String env = System.getProperty("env", "test");
        Map<String, Object> envConfig = ConfigManager.getEnvironmentConfig(env);
        return envConfig != null ? envConfig.get("apiKey").toString() : null;
    }
    
    /**
     * Get timeout from YAML configuration for the current environment
     */
    public static int getTimeout() {
        String env = System.getProperty("env", "test");
        Map<String, Object> envConfig = ConfigManager.getEnvironmentConfig(env);
        return envConfig != null ? Integer.parseInt(envConfig.get("timeout").toString()) : 30;
    }
    
    /**
     * Configure RestAssured with settings from YAML configuration
     */
    public static void configureRestAssured() {
        String baseUrl = getBaseUrl();
        int timeout = getTimeout();
        
        if (baseUrl != null) {
            RestAssured.baseURI = baseUrl;
        }
        
        // Configure timeout
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", timeout * 1000)
                        .setParam("http.socket.timeout", timeout * 1000));
    }

	public static FileInputStream getPropertyFileInput(String envType) throws FileNotFoundException {
		// TODO Auto-generated
		FileInputStream input = null;


		if(envType.equals("PreProd"))
		{
			input = new FileInputStream("src/main/java/properties/preProd.properties");
		}
		else if(envType.equals("Prod"))
		{
			input = new FileInputStream("src/main/java/properties/preProd.properties"); // Need to change Prod Property file
		}
		else
		{
			input = new FileInputStream("src/main/java/properties/preProd.properties");
		}

		return input;

	}

	public static Response postRequestAuthenticationExternalAPI(Properties prop, String userName, String password)
	{
		
		String baseUrl = prop.getProperty("baseURI");
		String authenticationExternalURL = baseUrl + prop.getProperty("AuthenticationExternal");
		
		String scarrierCode=prop.getProperty("carrierCode");
		String securityConsumerKey=prop.getProperty("securityConsumerKey");
		String XREQUESTOR = prop.getProperty("XREQUESTOR");


		RestAssured.baseURI = baseUrl;

		Response response = 
				given().log().all()
				.header("carrierCode", scarrierCode)
				.header("X-Requestor", XREQUESTOR)
				.header("Consumer-Key", securityConsumerKey)
				.header("Content-Type", "application/json")

				.body(getPayLoad.userDetails(userName, password))

				.when().post(authenticationExternalURL)

				.then().extract().response();

		return response;


	}

	public static Response postRequestRefreshTokenAPI(Properties prop, String idToken, String customerCode)
	{
		String scarrierCode=prop.getProperty("carrierCode");
		String baseUrl = prop.getProperty("baseURI");
		String tokenExternalURL = baseUrl + prop.getProperty("TokenExternal");

		String securityConsumerKey=prop.getProperty("securityConsumerKey");
		String XREQUESTOR = prop.getProperty("XREQUESTOR");


		RestAssured.baseURI = baseUrl;


		Response response = 
				given().log().all()
				.header("carrierCode", scarrierCode)
				.header("X-Requestor", XREQUESTOR)
				.header("Consumer-Key", securityConsumerKey)
				.header("idToken", idToken )
				.header("Content-Type", "application/json")

				.body(getPayLoad.customerCode(customerCode))

				.when().post(tokenExternalURL)

				.then().extract().response();

		return response;

	}

	public static String getrefreshTokenId(Properties prop) {

		String userName = prop.getProperty("usernameKey");
		String password = prop.getProperty("passwordKey");
		String customerCode = prop.getProperty("customerCode");
		String idToken = "";
		Response res, res1;
		String authorization="";



		res =  postRequestAuthenticationExternalAPI(prop, userName,  password);
		System.out.println(res.getBody().asPrettyString());
		
		if(res.statusCode() == 200)
		{
			idToken = res.jsonPath().getString("tokenId");
			res1 =  postRequestRefreshTokenAPI(prop, idToken, customerCode);
			System.out.println(res1.getBody().asPrettyString());

			
			if(res1.statusCode()==200)
			{
				authorization = res1.jsonPath().getString("idToken");
				return authorization;
			}
			else
			{
				return authorization;
			}
		}
		else
		{
			return authorization;
		}
	}

	public static Response getRequestPreferencesAPI(Properties prop, String deviceId, String authorization) {
		// TODO Auto-generated method stub

		String baseUrl = prop.getProperty("baseURI");

		String getPreferencelURL = baseUrl + "/notifications/public/mobile/"+deviceId+"/preferences";

		String securityConsumerKey=prop.getProperty("securityConsumerKey");
		RestAssured.baseURI = baseUrl;

		// Send the POST request using the retrieved URL
		Response response = given().log().all()
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer "+authorization)
				.header("Consumer-Key",securityConsumerKey)
				.get(getPreferencelURL);

		System.out.println(response.asPrettyString());
		return response;

	}

	public static Response postRequestDnDFreeTimeAPI(Properties prop, String shipmentnumber, String authorization) {
		
		String baseUrl = prop.getProperty("baseURI");
		String v3ConsumerKey= prop.getProperty("v3ConsumerKey");
		String postDnDFreeTimeURL = baseUrl + "/vas-experience/dnd/freetime/"+shipmentnumber+"";

		// Set base URI for RestAssured
		RestAssured.baseURI = baseUrl;

		// Send the POST request using the retrieved URL
				Response response = given().log().all()
						.header("Content-Type", "application/json")
						.header("Authorization", "Bearer "+authorization)
						.header("Consumer-Key",v3ConsumerKey)
						.header("Request-Source", "Maersk Mobile App")
						.header("Charge-Source", "CXED" )
						.body(getPayLoad.freeTimeDnd(shipmentnumber))
						
						.when().post(postDnDFreeTimeURL)

						.then().extract().response();
						
				System.out.println(response.asPrettyString());
				return response;
		
				
					
			
	
	}

	/* Commented code section starts
	
	public static String getCustomerCode(String idToken)
	{
		String[] envDetails = getEnvDetails();

		String envUrl=envDetails[0];
		String consumerKey = envDetails[3];

		RestAssured.baseURI = envUrl;

		Response response = 
				given().log().all()
				.header("carrierCode", CARRIER_CODE)
				.header("Consumer-Key", consumerKey)
				.header("idToken", idToken )

				.when().post("/shipmentmobile/security/customers")

				.then().extract().response();

		//System.out.println(response.asPrettyString());


		int StatusCode = response.getStatusCode();
		String Res= response.asString();

		JsonPath js = new JsonPath(Res);

		if(StatusCode == 200 )
		{
			String customerCode = js.getString("customers.code[1]");
			return customerCode;
		}
		else
		{
			String message = js.getString("message");
			Assert.assertEquals(message, "FAIL");
			return message;
		}

	}


	public static String getrefreshToken()
	{
		String[] envDetails = getEnvDetails();
		String uname = envDetails[1];
		String pwd = envDetails[2];

		String tokenID = getTokenID(uname,pwd);

		if(tokenID.equals("Validation errors") ||  tokenID.equals("Invalid username") )
		{
			Assert.assertEquals(tokenID, "FAIL");
			return tokenID;
		}
		else if (tokenID.equals("Invalid password") || tokenID.equals("Internal Server Error"))
		{
			Assert.assertEquals(tokenID, "FAIL");
			return tokenID;
		}
		else
		{
			String customerCode = getCustomerCode(tokenID);

			System.out.println("**************************************");
			System.out.println(customerCode);
			System.out.println("**************************************");

			String refreshToken = getRefreshTokenID(tokenID,customerCode);

			System.out.println("**************************************");
			System.out.println(refreshToken);
			System.out.println("**************************************");
			Assert.assertEquals("TRUE", "TRUE");

			return refreshToken;

		}

	}

	public static String[] getLocationDetails(String tokenID, String maerskRkstCode)
	{
		String[] envDetails = getEnvDetails();

		String envUrl=envDetails[0];
		String v3consumerKey = envDetails[4];

		RestAssured.baseURI = envUrl;

		Response response = 
				given().log().all()
				.header("Authorization", "Bearer "+ tokenID)
				.header("Consumer-Key", v3consumerKey)
				.queryParam("brand", "MAEU")
				.queryParam("type", "city")
				.queryParam("pageSize", "20")
				.queryParam("sort", "cityName")
				.queryParam("maerskRkstCode", maerskRkstCode)
				.when().get("/synergy/reference-data/geography/locations")

				.then().extract().response();

		//System.out.println(response.asPrettyString());


		int status = response.statusCode();
		JsonPath js = new JsonPath(response.asPrettyString());

		String[] Location = {"","",""};

		if(status == 200 )
		{

			Location[0] = js.getString("[0].maerskGeoLocationId");
			Location[1] = js.getString("[0].maerskRkstCode");
			Location[2] = js.getString("[0].countryCode");

			return Location;

		}
		else
		{
			Location[0] = js.getString("message");
			Assert.assertEquals(Location[0], "FAIL");
			return Location;
		}


	}


	public static String getcorrelationId(String tokenID, String[] orgLocation, String[] dstLocation, String exportServiceMode, String importServiceMode) 
	{
		String[] envDetails = getEnvDetails();

		String envUrl=envDetails[0];
		String consumerKey = envDetails[3];
		String currentDate = getCurrentDate();

		RestAssured.baseURI = envUrl;

		Response response = 
				given().log().all()
				.header("Authorization", "Bearer "+ tokenID)
				.header("X-Requestor", REQUESTOR)
				.header("Consumer-Key", consumerKey)
				.header("Content-Type", "application/json")
				.body(getPayLoad.gateWayDepartures(orgLocation,dstLocation,exportServiceMode, importServiceMode, currentDate ))

				.when().post("/shipmentmobile/gateway/departures")

				.then().assertThat().statusCode(202).extract().response();
		//System.out.println(response.asPrettyString());


		int status = response.statusCode();
		JsonPath js = new JsonPath(response.asPrettyString());

		if(status == 202 )
		{

			String correlationId = js.getString("correlationId");
			System.out.println("*******************"+ correlationId);
			return correlationId;
		}
		else
		{
			String message = js.getString("message");
			Assert.assertEquals(message, "FAIL");
			return message;
		}

	}

	private static String getCurrentDate() {
		// TODO Auto-generated method stub
		Date currentDate = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String currentDateF = dateFormat.format(currentDate);

		return currentDateF;
	}

	public static ArrayList<Object> getrouteScheduleIdStatusCode(String tokenID, String correlationId, int count) {
		String[] envDetails = getEnvDetails();

		String envUrl=envDetails[0];
		String consumerKey = envDetails[3];

		RestAssured.baseURI = envUrl;

		Response response = 
				given().log().all()
				.header("Authorization", "Bearer "+ tokenID)
				.header("Consumer-Key", consumerKey)
				.header("X-Requestor", "K6 Shipment Mobile; 9feca62a49337fa9bfb9")

				.queryParam("batch", count)
				.when().get("/shipmentmobile/gateway/departures/"+correlationId)

				.then().extract().response();

		System.out.println(response.asPrettyString());

		int status = response.statusCode();
		JsonPath js = new JsonPath(response.asPrettyString());

		String moreRoutesAvailable = "No Data";
		ArrayList<Object> resData = new ArrayList<>();


		if(status == 200)
		{
			moreRoutesAvailable = js.getString("moreRoutesAvailable");
			System.out.println("********************"+ status);
			System.out.println("********************"+ moreRoutesAvailable);
			resData.add(status);
			resData.add(moreRoutesAvailable);
		}
		else
		{
			System.out.println("********************"+ status);
			System.out.println("********************"+ moreRoutesAvailable);
			resData.add(status);
			resData.add(moreRoutesAvailable);
		}

		return resData;

	} 

	public static String getrouteScheduleId(String tokenID, String correlationId) {


		String message = "";
		ArrayList<Object> resData2 = new ArrayList<>();
		int statusCode = 0;
		String moreRoutesAvailable = "";
		int i=0;
		int j = 5;

		while(j != 0)
		{
			j--;
			i++;

			resData2 = tobatchRuntoGetRrouteScheduleIdStatusCode(tokenID, correlationId, i);		
			statusCode = (int) resData2.get(0);
			moreRoutesAvailable = (String) resData2.get(1);

			if(statusCode == 200 && moreRoutesAvailable == "true")
			{
				break;
			}
			else if(statusCode == 200 && moreRoutesAvailable == "false")
			{
				break;
			}
			else
			{
				continue;
			}


		}


		message = moreRoutesAvailable ;

		return message;
	}


	public static ArrayList<Object> tobatchRuntoGetRrouteScheduleIdStatusCode(String tokenID, String correlationId, int batchCount)
	{

		ArrayList<Object> resData1 = new ArrayList<>();
		int statusCode = 0;
		String moreRoutesAvailable = "";

		for(int i=1;i<=5;i++)
		{
			resData1 = getrouteScheduleIdStatusCode(tokenID, correlationId, batchCount);
			statusCode = (int) resData1.get(0);
			moreRoutesAvailable = (String) resData1.get(1);

			if(statusCode == 200 && moreRoutesAvailable == "true")
			{
				break;
			}
			else if(statusCode == 200 && moreRoutesAvailable == "false")
			{
				break;
			}
			else
			{
				continue;
			}

		}

		return resData1;

	}

	 */ 
	 
}