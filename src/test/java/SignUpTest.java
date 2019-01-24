
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SignUpTest {
    WebDriver driver;
    SignUpPage page;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Andrei.Kaliasinski\\IdeaProjects\\testselenium\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.spotify.com/us/signup/");
    }


    @Test
    public void typeInvalidYear(){
        page = new SignUpPage(driver);
        page.setMonth("December")
                .typeDay("20")
                .typeYear("85")
                .setShare(true);
        assertTrue(page.getErrorVisible("Please enter a valid year."));
        assertFalse(page.getErrorVisible("Please enter your birth month."));

    }

    @Test
    public void typeInvalidEmail(){
        page = new SignUpPage(driver);
        page.typeEmail("test@mail.test")
                .typeConfirmEmail("wrong@mail.test")
                .clickSignUpButton();
        assertTrue(page.getErrorVisible("Email address doesn't match."));
    }

    @Test
    public void signUpWithEmptyPassword(){
        page = new SignUpPage(driver);
        page.typeEmail("test@mail.test")
                .typeConfirmEmail("test@mail.test")
                .typeName("Testname")
                .clickSignUpButton();
        assertTrue(page.getErrorVisible("Please choose a password."));
    }

    @Test
    public void typeInvalidValues(){
        page = new SignUpPage(driver);
        page.typeEmail("testmail")
                .typeConfirmEmail("wrong@test.mail")
                .typePassword("qweqweqwe")
                .typeName("Name")
                .setSex("Male")
                .setShare(true)
                .clickSignUpButton();
        assertEquals(6, page.getErrors().size());
        assertEquals("Please enter your birth month.",page.getErrorByNumber(3));
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }


}

