package rest.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static io.restassured.config.SSLConfig.sslConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class MyStepdefs {
    private RequestSpecification request = RestAssured.with();
    private Response response;
    Properties properties;

    Utils util = new Utils();
    HashMap inputData = new HashMap();
    static String respValue;
    String storedValue;
    HashMap<String, Object> map = new HashMap<>();

    String baseUrl;
    JSONObject base = null;
    Object randomValue = null;
    JSONObject obj;
    Scenario scenario;

    final static Logger logger = Logger.getLogger(MyStepdefs.class);


    @Before(order=1)
    public void before(Scenario scenario) {
        System.out.println("Test case started");
        logger.info("Test case started");
        this.scenario = scenario;
        logger.info("Scenario::"+scenario.getName()+ " started");
        System.out.println("Scenario::"+scenario.getName()+" started");
    }
    @Before(order = 2)
    public void setProperties() {
        properties = util.fetchFromPropertiesFile();
        baseUrl=properties.getProperty("baseUrl");
    }


    @Before
    public void request_with_baseurl() {
        System.out.println("baseUrl:" + baseUrl);
        request.given().baseUri(baseUrl);
    }



    public Response apiOperation(String method,String apiUrl){
        System.out.println("API URL IS:"+apiUrl);
        RestAssuredConfig rac = RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation("TLSv1.1"));


        if ((method.equalsIgnoreCase("get")))
            response = request.when().get(apiUrl);
        else if ((method.equalsIgnoreCase("delete")))
            response = request.when().delete(apiUrl);
        else if ((method.equalsIgnoreCase("post")))
            response = request.config(rac).when().post(apiUrl);
        else if ((method.equalsIgnoreCase("put")))
            response = request.when().put(apiUrl);
        else if (method.equalsIgnoreCase("patch"))
            response = request.when().patch(apiUrl);

        return response;

    }

    @And("^Request params$")
    public void request_params_are(DataTable queryParams) {
//        String brandId;
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> data = queryParams.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            String keyMain = data.get(i).get("key");
            Object keyValue = data.get(i).get("value");
            String tempValue = keyValue.toString();

            if (tempValue.contains("response")) {
                if (tempValue.contains(",")) {
                    String ss[] = tempValue.split(",");
                    StringBuffer sb = new StringBuffer();
                    for (int j = 0; j < ss.length; j++) {
                        if (ss[j].contains(":")) {
                            String jsonKey = ss[j].split(":")[1];
                            Object tempValue2 = get_the_value_from_response(jsonKey);
                            keyValue = sb.append(tempValue2.toString() + ",");
                            String s1 = String.valueOf(keyValue);
                            s1 = s1.substring(0, s1.length() - 1);
                            keyValue = s1;
                        } else {
                            keyValue = respValue;
                        }
                    }
                }

                else if (tempValue.contains(":")){
                    String jsonKey = tempValue.split(":")[1];
                    keyValue = get_the_value_from_response(jsonKey);
                }

                else {
                    keyValue = respValue;
                }

            }
            else if (keyValue.toString().contains("randomString")) {
                keyValue = "TestAutomation" + util.randomAlphaNumeric(10);
            } else if (keyValue.toString().contains("randomValue")) {
                String[] temp = keyValue.toString().split("randomValue");
                keyValue = temp[0] + randomValue.toString();
            } else if (((String) keyValue).equalsIgnoreCase("randomint")) {
                keyValue = String.valueOf(util.randomInt(5000));
            }else if (keyValue.toString().contains("storedValue")) {
                keyValue = storedValue;
            } else if (keyValue.toString().equalsIgnoreCase("randomemail")) {
                keyValue = generateRandomEmail();
            } else if (((String) keyValue).equalsIgnoreCase("randomint")) {
                keyValue = String.valueOf(util.randomInt(5000));
            }


            map.put(keyMain, keyValue);
        }
        request.given()
                .queryParams(map);
    }


    @Given("^Request headers$")
    public void request_headers_are(DataTable headerParams) {
        try {
            List<Map<String, String>> data = headerParams.asMaps(String.class, String.class);
            for (int i = 0; i < data.size(); i++) {
                String keyMain = data.get(i).get("key");
                String keyValue = data.get(i).get("value");

                 if (keyValue.equalsIgnoreCase("response")) {
                    if (keyValue.contains(":")) {
                        String jsonKey = keyValue.split(":")[1];
                        Object tempValue = get_the_value_from_response(jsonKey);
                        keyValue = tempValue.toString();
                    }
                    else {
                        keyValue = respValue;
                    }
                    map.put(keyMain, keyValue);
                } else {
                    map.put(keyMain, keyValue);
                }
            }
            request.given()
                    .headers(map).log().all();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @When("^user hits \"(.*?)\" (.*?) api$")
    public void api_request_is_made(String uri, String method) {
        try {
            response = apiOperation(method,uri);
            request.log().all();
            logger.info("Response:");
            response.prettyPrint();
        } catch (Exception e) {
            e.printStackTrace();
            fail("unable to hit the request");
        } finally {
            request = RestAssured.with();
            System.out.println("URL is:" + properties.getProperty("baseUrl"));
            request.given().baseUri(properties.getProperty("baseUrl"));
        }
    }



    @Then("^the status code is (\\d+)$")
    public void verify_status_code(int statusCode) {
        response.then().statusCode(statusCode);
    }


    @And("^the response content type is (.*?)$")
    public void verify_response_content_type(String type) {
        response.then().contentType(type);
    }

    @Then("^the status code is (\\d+) else \"(.*?)\"$")
    public void verify_status_code(int statusCode, String message) {
        int expStatus = response.getStatusCode();
        if (expStatus != statusCode) {
            //System.out.println(message);
            logger.info(message);
            fail("Expected code:" + statusCode + " Actual code:" + expStatus + " " + message);
        }
    }


    @And("^Returned json schema is \"(.*?)\"$")
    public void schema_is(String fileName) {
        String basePath = properties.getProperty("schema.path");
        File file = new File(basePath + fileName);
        response.then()
                .body(matchesJsonSchema(file));
    }


    @And("^Response body should contain \"(.*?)\"$")
    public void response_should_contain(String message) {
        response.then().body(containsString(message));
    }


    @And("^Response should contain$")
    public void response_should_contain_data(DataTable responseParams) {
        //System.out.println("Response should contain:");
        logger.info("Response should contain:");
        List<Map<String, String>> data = responseParams.asMaps(String.class, String.class);
        for (int j = 0; j < data.size(); j++) {
            String keyMain = data.get(j).get("key");
            String keyValue = data.get(j).get("value");

            if (keyValue.equalsIgnoreCase("response")) {
                keyValue = respValue;
            }else if(keyValue.equalsIgnoreCase("randomString")){
                keyValue = "TESTAUTOMATION" + randomValue.toString();
            }
            if (!keyValue.equals("null")) {
                assertThat(response.path(keyMain).toString(), containsString(keyValue));
            } else {
                assertNull(response.path(keyMain));
            }

        }
    }

    @And("^get the (.*?) from the response$")
    public String get_the_value_from_response(String key) {

        if (key.equals("response")) {
            respValue = response.body().prettyPrint();

        } else {
            Object valueFromResp = response.then().extract().body().jsonPath().get(key);

            if (valueFromResp instanceof ArrayList) {
                ArrayList arrayId = ((ArrayList) valueFromResp);
                respValue = arrayId.get(0).toString();
            } else if (valueFromResp instanceof Integer) respValue = String.valueOf(valueFromResp);
            else {
                respValue = valueFromResp.toString();
            }
        }

        return respValue;
    }


    @And("(.*?) Response length should be (\\d+)")
    public void response_length_should_be(String key, int length) {
        if (key.equalsIgnoreCase("json"))
            response.then().body("result.size()", is(length));
        else
            response.then().body(key + ".size()", is(length));

    }



    @And("^Request body as list$")
    public void request_body_as_list(DataTable jsonParams) {
        List<String> val = new ArrayList<String>();
        try {
            List<Map<String, String>> data = jsonParams.asMaps(String.class, String.class);

            for (int i = 0; i < data.size(); i++) {
                String keyMain = data.get(i).get("key");
                String keyValue = data.get(i).get("value");

                    val.add(keyValue);

            }
            request.given().contentType(ContentType.JSON).body(val);
        } catch (Exception e) {
            System.out.println(e);

        }

    }



    @And("^Request body as json$")
    public void request_body_as_json(DataTable jsonParams) {
        String temp;
        String jsonFileName;
        String jsonData;
        base = null;

        try {
            List<Map<String, String>> data = jsonParams.asMaps(String.class, String.class);
            jsonFileName = data.get(0).get("value");
            logger.info("Json file:" + jsonFileName);
            if (jsonFileName.equalsIgnoreCase("response")) {
                jsonData = response.body().prettyPrint();
            } else if (jsonFileName.contains("formResponse")) {
                jsonData = obj.toString();
            } else {
                jsonData = util.readJsonDataFromFile(data.get(0).get("value"));
            }
            base = new JSONObject(jsonData);
            for (int i = 1; i < data.size(); i++) {
                String keyMain = data.get(i).get("key");
                String keyValue = data.get(i).get("value");
                Object value;
                if (keyValue.contains("randomInt")) {
                    randomValue = util.randomInt(8000);
                    value = randomValue;
                } else if (keyValue.contains("randomString")) {
                    if (keyValue.contains("-")) {
                        String[] valueArray = keyValue.split("-");
                        temp = util.randomAlphaNumeric(10);
                        randomValue = temp.toUpperCase();
                        value = valueArray[0] + temp;
                    } else {
                        temp = util.randomAlphaNumeric(10);
                        value = "TestAutomation" + temp;
                        value = value.toString().toUpperCase();
                    }
                    randomValue = temp.toUpperCase();
                } else if (keyValue.contains("randomValue")) {
                    String[] temp1 = keyValue.split("randomValue");
                    if (temp1.length >= 1)
                        value = temp1[0] + randomValue.toString();
                    else
                        value = randomValue.toString();
                } else if (keyValue.equalsIgnoreCase("randomphoneNumber")) {
                    Random rand = new Random();

                    int num1 = (rand.nextInt((9 - 7) + 1) + 7) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);

                    int num2 = rand.nextInt(743);
                    int num3 = rand.nextInt(10000);

                    DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
                    DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

                    value = df3.format(num1) + df3.format(num2) + df4.format(num3);

                } else if (keyValue.equals("randomurl")) {
                    char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                    StringBuilder sb = new StringBuilder(20);
                    Random random = new Random();
                    for (int j = 0; j < 20; j++) {
                        char c = chars[random.nextInt(chars.length)];
                        sb.append(c);
                    }
                    value = "www." + sb.toString() + ".com";

                } else if (keyValue.contains("randomEmail")) {
                    value = generateRandomEmail();
                }  else if (keyValue.contains("response")) {
                    if (keyValue.contains(":")) {
                        String jsonKey = keyValue.split(":")[1];
                        Object tempValue = get_the_value_from_response(jsonKey);
                        value = tempValue.toString();
                    } else {
                        value = respValue;
                    }
                } else {
                    value = keyValue;
                }
                base = getupdatedJsonData(base, keyMain, value);
                inputData.put(keyMain, value);
            }

            request.given().contentType(ContentType.JSON)
                    .body(base.toString());
            logger.info("inputData Hashmap:" + inputData);

        } catch (Exception e) {
            e.printStackTrace();
            fail("unable to update the json file");
        } catch (Throwable t) {
            t.getStackTrace();
            fail("unable to update the json file");
        }
    }




    public String generateRandomEmail() {
        String value ;
        value = "username"+(long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L+"@gmail.com";
        return value;
    }


    @And("^Request body as form params")
    public void request_body_as_form_params(DataTable formParams) {
        List<Map<String, String>> data = formParams.asMaps(String.class, String.class);
        for (int i = 0; i < data.size(); i++) {
            String keyMain = data.get(i).get("key");
            String keyValue = data.get(i).get("value");
            String tempValue = keyValue;
            if (keyValue.contains("randomString")) {
                keyValue = "TestAutomation" + util.randomAlphaNumeric(10);
            } else if (keyValue.contains("response")) {

                keyValue = respValue;
            }else if (keyValue.contains("storedValue")) {

                keyValue = storedValue;
            }
            else {
                keyValue = tempValue;
            }
            request.given()
                    .formParam(keyMain, keyValue);
        }
    }



    @And("^assert (.*?) == (.*?)$")
    public void assert_the_values(String expected, String actual) {

        if (expected.equalsIgnoreCase("response")) {
            expected = respValue;
        } else if (expected.toLowerCase().contains("randomstring")) {
            expected = "TESTAUTOMATION" + randomValue.toString();

        } else if (expected.equalsIgnoreCase("storedValue")) {
            expected=respValue;
        }else if (expected.contains("storedValue")) {
            expected = expected.replace("storedValue",storedValue);
            if (expected.toLowerCase().contains("response")){
                if (expected.contains(":")){
                    String jsonKey = expected.split(":")[1];
                    Object tempValue = get_the_value_from_response(jsonKey);
                    expected = tempValue.toString();
                }
                else {
                    expected = respValue;
                }
            }
        }

        else if (actual.toLowerCase().contains("response")) {
            actual = respValue;
        }  else if (actual.toLowerCase().contains("randomstring")) {
            actual = "TESTAUTOMATION" + randomValue.toString();
        }else if (actual.equalsIgnoreCase("storedValue")) {
            actual = storedValue;
        }

        assertTrue(expected + " is not equal to " + actual, expected.equalsIgnoreCase(actual));
    }




    @And("^Add Delay (.*?)$")
    public void add_delay(String del) {
        long delay = Long.valueOf(del);
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }


    @And("^get the count from response$")
    public void getTheCountFromResponse() throws Throwable {
        int res = response.body().path("size()");
        respValue = String.valueOf(res);

    }


    @And("^store this value$")
    public void storeThisValue() {
        storedValue = respValue;
        logger.info("Stored Value is:" + storedValue);
    }


    public static JSONObject getupdatedJsonData(JSONObject Object, String jsonPath, Object value) throws IOException, JSONException {
        String data = Object.toString();
        final Configuration jacksonJsonNode = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider()).mappingProvider(new JacksonMappingProvider()).build();
        final DocumentContext doc;

        logger.info("Value is:::" + value + " for path " + jsonPath);

        if (value.equals("null")) {
            doc = JsonPath.using(jacksonJsonNode).parse(data).set("$." + jsonPath, null);
        } else {
            doc = JsonPath.using(jacksonJsonNode).parse(data).set("$." + jsonPath, value);
        }


        String json1 = doc.read("$").toString();
        JsonObject obj = new JsonParser().parse(json1).getAsJsonObject();
        return (new JSONObject(obj.toString()));
        //return obj;

    }

    @And("^Pankaj feels boring$")
    public void pankajFeelsBoring() {
        System.out.println("pankaj now feeling excited");
    }

    @And("^asndasjdj (.*?)$")
    public void abc(String abc) {
        System.out.println(">>>>>>"+abc);
    }
}