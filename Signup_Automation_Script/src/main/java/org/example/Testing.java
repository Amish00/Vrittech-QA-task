package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver =new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://authorized-partner.vercel.app/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.findElement(By.linkText("Join Us Now")).click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("remember"))).click();
        driver.findElement(By.linkText("Continue")).click();

        // Set up your account
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.findElement(By.name("firstName")).sendKeys("Ram");
        driver.findElement(By.name("lastName")).sendKeys("Shrestha");
        driver.findElement(By.name("email")).sendKeys("jignanakno@necub.com");
        driver.findElement(By.name("phoneNumber")).sendKeys("9852520501");
        driver.findElement(By.name("password")).sendKeys("Password@123");
        driver.findElement(By.name("confirmPassword")).sendKeys("Password@123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        try {
            String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[role='status'].bg-destructiveAccent"))).getText();
            System.err.println("\n\nRegistration Failed: " + errorMessage);
            return;
        } catch (TimeoutException e) {
            System.out.println("No error toast detected. Proceeding to OTP...");
        }

        // OTP code
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the OTP code: ");
        int otp = sc.nextInt();
        driver.findElement(By.cssSelector("input[data-input-otp='true']")).sendKeys(String.valueOf(otp));
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[role='status'].bg-successAccent"))).getText();
        System.out.println("\nRegistration Success: " + successMessage);

        // Agency Details
        driver.findElement(By.name("agency_name")).sendKeys("ABC digital");
        driver.findElement(By.name("role_in_agency")).sendKeys("Producer");
        driver.findElement(By.name("agency_email")).sendKeys("pe803jdcf5@illubd.com");
        driver.findElement(By.name("agency_website")).sendKeys("google.com");
        driver.findElement(By.name("agency_address")).sendKeys("Lagankhel, Lalitpur");
        driver.findElement(By.cssSelector("button[role='combobox']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='dialog']//span[text()='Canada']"))).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String successMessage1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[role='status'].bg-successAccent"))).getText();
        System.out.println("\nRegistration Success: " + successMessage1);

        //Professional Experience
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Years of Experience']/following-sibling::button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='option']//span[contains(text(), '3 years')]"))).click();
        driver.findElement(By.name("number_of_students_recruited_annually")).sendKeys(String.valueOf(50));
        driver.findElement(By.name("focus_area")).sendKeys("Undergraduate admissions to Canada");
        driver.findElement(By.name("success_metrics")).sendKeys(String.valueOf(90));
        driver.findElement(By.xpath("//label[text()='Career Counseling']")).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String successMessage2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[role='status'].bg-successAccent"))).getText();
        System.out.println("\nRegistration Success: " + successMessage2);

        // Verification and Preferences
        driver.findElement(By.name("business_registration_number")).sendKeys(String.valueOf(123456));
        driver.findElement(By.cssSelector("button[role='combobox']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='dialog']//span[text()='Canada']"))).click();
        driver.findElement(By.xpath("//label[text()='Colleges']")).click();
        driver.findElement(By.name("certification_details")).sendKeys("ICEF Certified Education Agent");

        String file1 = "C:\\Users\\AmishJoshi\\Downloads\\image.jpg";
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(file1);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String successMessage3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[role='status'].bg-successAccent"))).getText();
        System.out.println("\nRegistration Success: " + successMessage3);

    }
}
