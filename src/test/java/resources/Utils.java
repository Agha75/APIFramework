package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {
    //This object is made static because it will return the last detail s in multiple data set and that will be shown in log file
    public static RequestSpecification req;

    public RequestSpecification requestSpecification() throws IOException {

        //This condition is done because log of all data sets will be maintained in logging file
        if (req == null) {
            //this is used for logging request and response
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalVariable("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public static String getGlobalVariable(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("src/test/java/resources/global.properties");
        prop.load(file);
        return prop.getProperty(key);

    }

    public String getJsonPath(Response response, String key) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();

    }
}
