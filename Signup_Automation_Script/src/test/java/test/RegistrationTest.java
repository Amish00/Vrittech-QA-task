package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class RegistrationTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void testFullRegistrationFlow(){
        driver.get("https://authorized-partner.vercel.app/");
        driver.findElement(By.linkText("Join Us Now")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("remember"))).click();
        driver.findElement(By.linkText("Continue")).click();

        //Set up your account
        driver.findElement(By.name("firstName")).sendKeys("Ram");
        driver.findElement(By.name("lastName")).sendKeys("Shrestha");
        driver.findElement(By.name("email")).sendKeys("hukovozo@denipl.net");
        driver.findElement(By.name("phoneNumber")).sendKeys("98521625762");
        driver.findElement(By.name("password")).sendKeys("Password@123");
        driver.findElement(By.name("confirmPassword")).sendKeys("Password@123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        List<WebElement> errorList = driver.findElements(By.cssSelector("li[role='status'].bg-destructiveAccent"));
        if (!errorList.isEmpty()) {
            System.err.println("Registration Failed: " + errorList.get(0).getText());
            return;
        }

        //OTP Code
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        String otp = JOptionPane.showInputDialog(
                frame,
                "Enter the OTP code:",
                "OTP Verification",
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (otp == null || otp.trim().isEmpty()) {
            System.err.println("OTP cancelled or empty.");
            return;
        }

        WebElement otpInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[data-input-otp='true']")));
        otpInput.clear();
        otpInput.sendKeys(otp);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        verifySuccess("Registration Success");

        // Agency Details
        driver.findElement(By.name("agency_name")).sendKeys("ABC digital");
        driver.findElement(By.name("role_in_agency")).sendKeys("Producer");
        driver.findElement(By.name("agency_email")).sendKeys("f5x1w8mqji@illubd.com");
        driver.findElement(By.name("agency_website")).sendKeys("google.com");
        driver.findElement(By.name("agency_address")).sendKeys("Lagankhel, Lalitpur");
        driver.findElement(By.cssSelector("button[role='combobox']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='dialog']//span[text()='Canada']"))).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        verifySuccess("Registration Success");

        //Professional Experience
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Years of Experience']/following-sibling::button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='option']//span[contains(text(), '3 years')]"))).click();
        driver.findElement(By.name("number_of_students_recruited_annually")).sendKeys(String.valueOf(50));
        driver.findElement(By.name("focus_area")).sendKeys("Undergraduate admissions to Canada");
        driver.findElement(By.name("success_metrics")).sendKeys(String.valueOf(90));
        driver.findElement(By.xpath("//label[text()='Career Counseling']")).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        verifySuccess("Registration Success");

        // Verification and Preferences
        driver.findElement(By.name("business_registration_number")).sendKeys(String.valueOf(123456));
        driver.findElement(By.cssSelector("button[role='combobox']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='dialog']//span[text()='Canada']"))).click();
        driver.findElement(By.xpath("//label[text()='Colleges']")).click();
        driver.findElement(By.name("certification_details")).sendKeys("ICEF Certified Education Agent");

        String file1 = "C:\\Users\\AmishJoshi\\Downloads\\image.jpg";
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(file1);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        verifySuccess("Registration Success");
    }

    private void verifySuccess(String message){
        String msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.bg-successAccent"))).getText();
        System.out.println(message + ": " + msg);
    }

    @AfterTest
    public void tearDown(){
        if  (driver != null) {
            driver.quit();
        }
    }
}