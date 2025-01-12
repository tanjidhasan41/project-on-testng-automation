package testrunner;

import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.ForgotPasswordPage;
import setup.ReadGmail;
import setup.Setup;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ForgotPasswordTestRunner extends Setup {

    @Test(priority = 1, description = "User cannot reset password using wrong email")
    public void resetPasswordUsingWrongEmail() throws InterruptedException {

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

        Faker faker = new Faker();
        List<WebElement> tagNameElement = driver.findElements(By.tagName("a"));
        tagNameElement.get(0).click();
        forgotPasswordPage.doResetPassword(faker.name().firstName().toLowerCase() + "@gmail.com");

        Thread.sleep(1000);

        String warningActual = driver.findElement(By.tagName("p")).getText();
        String warningExpected = "Your email is not registered";
        Assert.assertTrue(warningActual.contains(warningExpected));

        driver.navigate().refresh();
        Thread.sleep(2000);
    }

    @Test(priority = 2, description = "User cannot reset password using invalid email")
    public void resetPasswordUsingInvalidEmail() throws InterruptedException {

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

        Faker faker = new Faker();
        forgotPasswordPage.doResetPassword(faker.name().firstName().toLowerCase() + "@gmail");

        Thread.sleep(2000);

        String warningActual = driver.findElement(By.tagName("p")).getText();
        String warningExpected = "Your email is not registered";
        Assert.assertTrue(warningActual.contains(warningExpected));

        driver.navigate().refresh();
        Thread.sleep(2000);

    }

    @Test(priority = 3, description = "User can reset password using valid email")
    public void resetPasswordUsingValidEmail() throws IOException, ParseException, ConfigurationException, InterruptedException {

        String filePath = "./src/test/resources/users.json";

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size() - 1);
        String email = userObj.get("email").toString();
        forgotPasswordPage.doResetPassword(email);

        String messageActual = driver.findElement(By.tagName("p")).getText();
        String messageExpected = "Password reset link sent to your email";
        Assert.assertTrue(messageActual.contains(messageExpected));
        Thread.sleep(5000);

        ReadGmail readGmail = new ReadGmail();
        readGmail.getGmailLink();

        readGmail.readMailById2();

    }

}
