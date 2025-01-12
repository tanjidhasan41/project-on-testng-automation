package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setup.UserModel;

import java.util.List;

public class RegistrationPage {

    @FindBy(id = "firstName")
    WebElement txtFirstName;

    @FindBy(id = "lastName")
    WebElement txtLastName;

    @FindBy(id = "email")
    WebElement txtEmail;

    @FindBy(id = "password")
    WebElement txtPassword;

    @FindBy(id = "phoneNumber")
    WebElement txtPhoneNumber;

    @FindBy(id = "address")
    WebElement txtAddress;

    @FindBy(css = "[type=radio]")
    List<WebElement> btnRadio;

    @FindBy(css = "[type=checkbox]")
    WebElement checkTerms;

    @FindBy(id = "register")
    WebElement btnRegister;

    public RegistrationPage(WebDriver driver) {

        PageFactory.initElements(driver, this);

    }

    public void doRegister(UserModel userModel) {

        txtFirstName.sendKeys(userModel.getFirstname());
        txtLastName.sendKeys(userModel.getLastname());
        txtEmail.sendKeys(userModel.getEmail());
        txtPassword.sendKeys(userModel.getPassword());
        txtPhoneNumber.sendKeys(userModel.getPhonenumber());
        txtAddress.sendKeys(userModel.getAddress());
        btnRadio.get(0).click();
        checkTerms.click();
        btnRegister.click();

    }

}
