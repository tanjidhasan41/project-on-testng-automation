package testrunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.AddItemPage;
import page.LoginPage;
import setup.Setup;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;

public class AddItemTestRunner extends Setup {

    @Test(priority = 1, description = "User can login successfully")
    public void userLoginByRegisteredAccount() throws IOException, ParseException {

        String filePath = "./src/test/resources/users.json";

        LoginPage loginPage = new LoginPage(driver);
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        JSONObject userObj1 = (JSONObject) jsonArray.get(jsonArray.size() - 2);
        String email = userObj1.get("email").toString();

        JSONObject userObj2 = (JSONObject) jsonArray.get(jsonArray.size() - 1);
        String password = userObj2.get("new_password").toString();
        loginPage.doLogin(email, password);

        String headerActual = driver.findElement(By.tagName("h2")).getText();
        String headerExpected = "User Daily Costs";
        Assert.assertTrue(headerActual.contains(headerExpected));

    }

    @Test(priority = 2, description = "Check if item 1 is added successfully")
    public void doAddItems1 () throws InterruptedException {

        AddItemPage addItemPage = new AddItemPage(driver);

        driver.findElement(By.className("add-cost-button")).click();
        addItemPage.doAddMobile("Mobile", "10000", "");
        Utils.handleAlert(driver);

    }

    @Test(priority = 3, description = "Check if item 2 is added successfully")
    public void doAddItems2 () throws InterruptedException {

        AddItemPage addItemPage = new AddItemPage(driver);

        driver.findElement(By.className("add-cost-button")).click();
        addItemPage.doAddLaptop("Laptop", "50000", "Student Laptop");
        Utils.handleAlert(driver);

        Thread.sleep(2000);

    }

}