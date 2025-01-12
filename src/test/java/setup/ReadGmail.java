package setup;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ReadGmail {

    Properties properties;

    public void getEnvironmentVariable() throws IOException {

        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("./src/test/resources/config.properties");
        properties.load(fileInputStream);

    }

    public ReadGmail() throws IOException {
        getEnvironmentVariable();
    }

    public String getGmailList() throws IOException, ConfigurationException {

        RestAssured.baseURI = "https://www.googleapis.com";
        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer "+properties.getProperty("gmail_token"))
                .when().get("/gmail/v1/users/me/messages");
        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String mailID = jsonPath.get("messages[0].id");
        System.out.println(mailID);
        return mailID;

    }

    public String readMailById1() throws IOException, ConfigurationException {

        String mail_ID=getGmailList();
        RestAssured.baseURI = "https://www.googleapis.com";
        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer "+properties.getProperty("gmail_token"))
                .when().get("/gmail/v1/users/me/messages/"+mail_ID); //properties.getProperty("mail_ID"));
        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String mailBody = jsonPath.get("snippet");
        System.out.println(mailBody);
        return jsonPath.get("snippet");

    }

    public String getGmailLink() throws IOException, ConfigurationException {

        RestAssured.baseURI = "https://www.googleapis.com";
        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer "+properties.getProperty("gmail_token"))
                .when().get("/gmail/v1/users/me/messages");
        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String mailID = jsonPath.get("messages[0].id");
        System.out.println(mailID);
        return mailID;

    }

    public void readMailById2() throws IOException, ConfigurationException {

        String mail_ID2 = getGmailLink();
        RestAssured.baseURI = "https://www.googleapis.com";
        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer "+properties.getProperty("gmail_token"))
                .when().get("/gmail/v1/users/me/messages/"+mail_ID2); //properties.getProperty("mail_ID2"));
        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String mailBody = jsonPath.get("snippet");
        System.out.println(mailBody);
        Utils.setEnvironmentVariable("reset_link", mailBody);

    }

}
