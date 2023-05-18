package com.fb.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fb.qa.base.TestBase;
import com.fb.qa.pages.HomePage;
import com.fb.qa.pages.LoginPage;

public class HomePageTest extends TestBase {
	
	public HomePageTest() {
		super();
	}
	
	LoginPage loginPage;
	HomePage homepage;
	WebDriver driver;
//	MaterialRequisitionPage materialreq;
//	OutwardPage outward;
	
	@BeforeMethod
	public void setup() throws InterruptedException {
		this.driver = initilization();
	
		loginPage = new LoginPage(this.driver);
		homepage = loginPage.login("abdullah.bilal@nxb.com.pk", "Qajob@1234");
//		homepage.clickOnMaterialRequisition();	
//		homepage.clickOnOutward();
	}
	
	@Test(priority=1)
	public void verifyHomePageTitleTest() {
		String homepageTitle = homepage.verifyPageTitle();
		Assert.assertEquals(homepageTitle, "F&B System", "Title is not matched");
	}
	
//	@Test(priority=2)
//	public void clickOnMaterialReq() {
//		homepage.clickOnMaterialRequisition();
//	}
//	@Test(priority=3)
//	public void clickOnOutward() {
//		homepage.clickOnOutward();
//	}
	
	@AfterMethod
	public void teardown() throws InterruptedException {
		Thread.sleep(3000);
		driver.close();
	}
}
