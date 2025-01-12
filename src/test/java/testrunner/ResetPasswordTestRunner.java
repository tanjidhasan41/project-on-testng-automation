package testrunner;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import page.ResetPasswordPage;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

import java.io.IOException;

public class ResetPasswordTestRunner extends Setup {

    @Test(description = "Check if user can reset password")
    public void resetPassword() throws IOException, ParseException, InterruptedException {

        Utils utils = new Utils();
        String dyna = utils.getResetLink();
        driver.get(dyna);

        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
        UserModel userModel = new UserModel();
        userModel.setPassword("usEr!5%");

        resetPasswordPage.resetPassword(userModel);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("new_password", userModel.getPassword());
        Utils.saveData(jsonObject);

        Thread.sleep(2000);

    }

}
