package testrunner;

import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.RegistrationPage;
import setup.ReadGmail;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

import java.io.IOException;

public class RegistrationTestRunner extends Setup {

    @Test(description = "Check if registration is successful")
    public void userRegistration() throws InterruptedException, IOException, ParseException, ConfigurationException {

        RegistrationPage registrationPage = new RegistrationPage(driver);

        Faker faker = new Faker();
        UserModel userModel = new UserModel();
        driver.findElement(By.partialLinkText("Register")).click();
        userModel.setFirstname(faker.name().firstName());
        userModel.setLastname(faker.name().lastName());
        userModel.setEmail("thsqa41+" + Utils.generateRandomID(1, 999) + "@gmail.com");
        userModel.setPassword("1234");
        userModel.setPhonenumber("0190" + Utils.generateRandomID(1000000, 9999999));
        userModel.setAddress(faker.address().city());

        registrationPage.doRegister(userModel);
        Thread.sleep(3000);
        String successMessage = driver.findElement(By.className("Toastify__toast")).getText();
        Assert.assertTrue(successMessage.contains("registered successfully"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", userModel.getFirstname());
        jsonObject.put("lastName", userModel.getLastname());
        jsonObject.put("email", userModel.getEmail());
        jsonObject.put("password", userModel.getPassword());
        jsonObject.put("phoneNumber", userModel.getPhonenumber());
        jsonObject.put("address", userModel.getAddress());

        Utils.saveData(jsonObject);
        Thread.sleep(3000);
        ReadGmail readGmail = new ReadGmail();

        Thread.sleep(1000);
        String mailBody = readGmail.readMailById1();

        Assert.assertTrue(mailBody.contains("Welcome to our platform!"));

    }

}
