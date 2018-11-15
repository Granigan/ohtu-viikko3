package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.Random;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {
    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";
    
    @Given("^login is selected$")
    public void login_selected() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("login"));       
        element.click();          
    } 
    
    @Given("^command new user is selected$")
    public void new_user_selected() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();
    }

    @When("^correct username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_correct_and_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^correct username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
    public void username_and_incorrect_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }
    
    @When("^non-existing username \"([^\"]*)\" and irrelevant password \"([^\"]*)\" are given$")
    public void nonexisting_username_and_irrelevant_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }
    
    @When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered")
    public void valid_username_and_password_and_confirmation_are_entered(String un, String pwd) throws Throwable {
        Random r = new Random();
        registerWith(un+r.nextInt(10000), pwd, pwd);
    }
    
    @When("^too short username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered")
    public void too_short_username_and_valid_password_and_confirmation_are_entered(String un, String pwd) throws Throwable {
        Random r = new Random();
        registerWith(un+r.nextInt(10), pwd, pwd);
    }
    
    @When("^a valid username \"([^\"]*)\" and too short password \"([^\"]*)\" and matching password confirmation are entered")
    public void valid_username_and_too_short_password_and_confirmation_are_entered(String un, String pwd) throws Throwable {
        Random r = new Random();
        registerWith(un+r.nextInt(10000), pwd, pwd);
    }
    
    @When("^a valid username \"([^\"]*)\" and valid password \"([^\"]*)\" and not matching password confirmation \"([^\"]*)\" are entered")
    public void valid_username_and_valid_password_and_not_matching_confirmation_are_entered(String un, String pwd, String pwdc) throws Throwable {
        Random r = new Random();
        registerWith(un+r.nextInt(10000), pwd, pwdc);
    }
    
    @Then("^user is logged in$")
    public void user_is_logged_in() throws Throwable {
        pageHasContent("Ohtu Application main page");
    }
    
    @Then("^user is not logged in and error message is given$")
    public void user_is_not_logged_in_and_error_message_is_given() throws Throwable {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }     
    
    @Then("^a new user is created")
    public void new_user_is_created() throws Throwable {
        pageHasContent("Welcome to Ohtu Application!");
    }
    
    @Then("^user is not created and error \"([^\"]*)\" is reported$")
    public void user_is_not_created_and_error_message_is_given(String error) throws Throwable {
        pageHasContent(error);
        pageHasContent("Create username and give password");
    }     
    
    @After
    public void tearDown(){
        driver.quit();
    }
        
    /* helper methods */
 
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
        
    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();  
    } 
    
    private void registerWith(String un, String pwd, String pwdc) {
        pageHasContent("Create username and give password");
        WebElement el = driver.findElement(By.name("username"));
        el.sendKeys(un);
        el = driver.findElement(By.name("password"));
        el.sendKeys(pwd);
        el = driver.findElement(By.name("passwordConfirmation"));
        el.sendKeys(pwdc);
        el = driver.findElement(By.name("signup"));
        el.submit();
    }
}
