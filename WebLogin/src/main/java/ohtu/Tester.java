package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {

    private static WebDriver driver;
    private static WebElement element;

    public static void main(String[] args) {
        driver = new ChromeDriver();

        //open site
        driver.get("http://localhost:4567");

        sleep(2);

        // go to login
        clickLogin();

        // successful login
        doLogin("pekka", "akkep");

        // back to main
        logout();

        // go to login
        clickLogin();

        // incorrect pwd
        doLogin("pekka", "incorrect");

        // incorrect un
        doLogin("non-existing", "irrelevant");

        // go back to main
        leaveLogin();

        // go to new user creation
        clickRegister();

        // register a new user (may crash inadvertedly if random(10000) matches existing user)
        doRegistering("newuser", "passw0rd");

        // go to main from welcome
        goMainFromWelcome();
        
        // back to main
        logout();

        driver.quit();
    }

    private static void clickLogin() {
        element = driver.findElement(By.linkText("login"));
        element.click();
        
        sleep(2);
    }

    private static void clickRegister() {
        element = driver.findElement(By.linkText("register new user"));
        element.click();
        
        sleep(2);
    }

    private static void leaveLogin() {
        element = driver.findElement(By.linkText("back to home"));
        element.click();
        
        sleep(2);
    }

    private static void goMainFromWelcome() {
        element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();
        
        sleep(2);
    }

    private static void doLogin(String un, String pwd) {
        element = driver.findElement(By.name("username"));
        element.sendKeys(un);
        element = driver.findElement(By.name("password"));
        element.sendKeys(pwd);
        element = driver.findElement(By.name("login"));

        sleep(2);
        element.submit();

        sleep(3);
    }

    private static void doRegistering(String un, String pwd) {
        Random r = new Random();
        element = driver.findElement(By.name("username"));
        element.sendKeys(un + r.nextInt(10000));
        element = driver.findElement(By.name("password"));
        element.sendKeys(pwd);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(pwd);
        element = driver.findElement(By.name("signup"));

        sleep(2);
        element.submit();

        sleep(3);
    }

    private static void logout() {
        element = driver.findElement(By.linkText("logout"));
        element.click();

        sleep(2);
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }
}
