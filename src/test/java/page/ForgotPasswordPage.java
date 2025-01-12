package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setup.UserModel;

public class ForgotPasswordPage {

    @FindBy(css = "[type=email]")
    WebElement txtForgotEmail;

    @FindBy(css = "[type=submit]")
    WebElement btnReset;

    public ForgotPasswordPage(WebDriver driver) {

        PageFactory.initElements(driver, this);

    }

    public void doResetPassword(String email) {

        txtForgotEmail.sendKeys(email);
        btnReset.click();

    }

}
