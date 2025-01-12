package utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    public static void handleAlert(WebDriver driver) throws InterruptedException {

        Thread.sleep(1000);
        driver.switchTo().alert().accept();
        Thread.sleep(1000);

    }

    public static int generateRandomID(int min, int max) {

        double randomID = Math.random() * (max - min) + min;
        return (int) randomID;

    }

    public static void saveData(JSONObject jsonObject) throws IOException, ParseException {

        String filePath = "./src/test/resources/users.json";

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(filePath));

        jsonArray.add(jsonObject);

        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonArray.toJSONString());
        writer.flush();
        writer.close();

    }

    public static void setEnvironmentVariable(String key, String value) throws ConfigurationException {

        PropertiesConfiguration configuration = new PropertiesConfiguration("./src/test/resources/config.properties");
        configuration.setProperty(key, value);
        configuration.save();

    }

    public String getResetLink() {
        Properties properties = new Properties();
        String link = "";

        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("./src/test/resources/config.properties");
            properties.load(fileInputStream);

            String resetLink = properties.getProperty("reset_link");

            link = extractLink(resetLink);

        } catch (IOException e) {
            //e.printStackTrace();
        }

        return link;
    }

    public static String extractLink(String text) {
        String link = "";
        int startIndex = text.indexOf("http");
        if (startIndex != -1) {
            int endIndex = text.indexOf(" ", startIndex);
            if (endIndex == -1) {
                endIndex = text.length();
            }
            link = text.substring(startIndex, endIndex);
        }
        return link;
    }

}
