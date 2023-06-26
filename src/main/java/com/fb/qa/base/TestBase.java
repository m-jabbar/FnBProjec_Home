package com.fb.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fb.qa.pages.HomePage;
import com.fb.qa.pages.LoginPage;
import com.fb.qa.util.TestUtil;

public class TestBase {

	public WebDriver driver;
	public Properties prop;
	public WebDriverWait wait;
	String xpath;
	LoginPage loginPage;
	HomePage homepage;

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ipf = new FileInputStream(
					"C:\\Users\\Jabbar's\\eclipse-workspace\\FnBProject\\src\\main\\java\\com\\fb\\qa\\config\\config.properties");
			prop.load(ipf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public WebDriver initialization() {
		String browserName = prop.getProperty("browser");
		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Jabbar's\\eclipse-workspace\\FnBProject\\chromedriver.exe");
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			driver = new ChromeDriver();
		}
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtil.EXPLICIT_WAIT));
		driver.get(prop.getProperty("url"));
		// Perform login
		login(prop.getProperty("username"), prop.getProperty("password"));
		return driver;
	}

	private void login(String username, String password) {
		loginPage = new LoginPage(driver);
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();
	}

	public void failTestCases(String testMethodName) {
		if (driver != null) {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile,
						new File("C:\\Users\\Jabbar's\\eclipse-workspace\\FnBProject\\screenshots"
								+ testMethodName + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void scrollDown(WebDriver driver, int x, int y) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(" + x + "," + y + ")");
	}

	public void selectDateFromCalendar(String calendarXPath, String formattedDate) {
		xpath = String.format(calendarXPath, formattedDate);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public String getFormattedCurrentDate() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
		return currentDate.format(formatter);
	}

	public void waitForElementToBeClickable(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void tearDown() {
		driver.quit();
	}
}
