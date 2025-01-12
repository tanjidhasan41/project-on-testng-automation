package setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Setup {

    public WebDriver driver;
    Properties properties;
    FileInputStream fileInputStream;

    @BeforeTest
    public void setup() throws IOException {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://dailyfinance.roadtocareer.net/");

        properties = new Properties();
        fileInputStream = new FileInputStream("./src/test/resources/config.properties");
        properties.load(fileInputStream);

    }

    @AfterTest
    public void quitBrowser() {

        driver.quit();

    }

}
