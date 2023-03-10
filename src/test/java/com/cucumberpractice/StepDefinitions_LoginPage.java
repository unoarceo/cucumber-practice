package com.cucumberpractice;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class StepDefinitions_LoginPage {

    WebDriver driver;

    @Before
    public void beforeScenario() {
        System.setProperty("webdriver.chrome.com", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void afterScenario() {
        //driver.close();
        driver.quit();
    }

    @Given("^login page \"(.*?)\" is open$")
    public void loginPage(String strURL) {
        driver.get(strURL);
    }

    @When("^user enters \"(.*?)\" as the Username$")
    public void userEntersAsTheUsername(String strUsername) {
        driver.findElement(By.id("username")).sendKeys(strUsername.trim());
    }

    @And("^user enters \"(.*?)\" as the Password$")
    public void userEntersAsThePassword(String strPassword) {
        driver.findElement(By.id("password")).sendKeys(strPassword.trim());
    }

    @And("clicks on the login button")
    public void clicksOnTheLoginButton() throws InterruptedException {
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(2000);
    }

    @Then("^page is redirected to \"(.*?)\"$")
    public void pageIsRedirectedTo(String strExpectedURL) {
        VerifyURL(driver.getCurrentUrl().trim(), strExpectedURL);
    }

    @And("^page remains at \"(.*?)\"$")
    public void pageRemainsAt(String strExpectedURL) {
        VerifyURL(driver.getCurrentUrl().trim(), strExpectedURL);
    }

    @And("^message \"(.*?)\" is displayed$")
    public void messageIsDisplayed(String strExpectedMesg) {
        String[] strActualMesg = driver.findElement(By.id("flash")).getText().trim().split("\n");

        if (strActualMesg[0].compareTo(strExpectedMesg.trim()) != 0) {
            Assert.fail(
                    "Expected: " + strExpectedMesg
                            + "\n Actual: " + strActualMesg[0]
            );
        }
    }

    @Then("^element xpath \"(.*?)\" is displayed$")
    public void elementXpathIsDisplayed(String strxpath) {
        WebElement we = driver.findElement(By.xpath(strxpath));

        if (we != null) {
            if (!we.isDisplayed())
                Assert.fail(strxpath + " is not found.");
        } else {
            Assert.fail(strxpath + " is not found.");
        }
    }

    public void VerifyURL(String prevURL, String nextURL) {
        if (prevURL.compareToIgnoreCase(nextURL.trim()) != 0) {
            Assert.fail(
                    "Expected: " + nextURL
                            + "\n Actual: " + prevURL
            );
        }
    }


}
