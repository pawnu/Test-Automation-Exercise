package EcommerceProject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Website tested: http://automationpractice.com
 * 
 * Testing the following: 
 * 1. User logging into website and viewing account page(s) 
 * 2. User puts in multiple products to cart and delete some without affecting other 
 * 3. User navigates to review page and successfully leaves a review 
 * 4. User browsing product and purchasing one to completion 
 * 5. User logs out
 */
public class RunTest {
	WebDriver driver;
	Homepage homepage;
	SigninPage signinpage;
	AccountPage accountpage;
	WomenProductPage womenproduct;
	CartPage cartpage;
	Wait<WebDriver> wait;
	ExtentReports report;
	ExtentTest test;
	//need to fill this in
	String username = "";
	String password = "";

	@Test(priority = 1, enabled = true)
	public void testLoginAndView() throws IOException {
		// Create report file on desktop
		report = new ExtentReports("C:\\Users\\felix\\Desktop\\automationreport.html", true);
		// init/start the test
		test = report.startTest("Login and view Account Page");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\felix\\Desktop\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\felix\\Desktop\\Selenium\\geckodriver.exe");
		driver = new ChromeDriver();
		driver.get("http://automationpractice.com/index.php");
		wait = new WebDriverWait(driver, 25);

		// load homepage
		homepage = new Homepage(driver);
		signinpage = new SigninPage(driver);
		assertTrue(homepage.checkLogoPresent());
		test.log(LogStatus.INFO, "Browser started");
		// navigate to signinpage
		homepage.clickSignin();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Searching for sign in page...");
				return signinpage.checkSignPageLoaded();
			}
		});
		assertTrue(signinpage.checkSignPageLoaded());
		// enter login details
		signinpage.login(username, password);
		// check navigated to account page
		accountpage = new AccountPage(driver);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Searching for account page...");
				return accountpage.checkisAccountPage();
			}
		});
		test.log(LogStatus.INFO, "Accessed account");
		if (accountpage.getHeading().equals("MY ACCOUNT")) {
			test.log(LogStatus.PASS, "View user account page");
		} else {
			test.log(LogStatus.FAIL, "View user account page");
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(
					"C:\\Users\\felix\\Desktop\\img.jpg"));
			String image = test.addScreenCapture(
					"C:\\Users\\felix\\Desktop\\img.jpg");
			test.log(LogStatus.FAIL, "View user account page", image);
		}
		report.endTest(test);
		report.flush();
	}

	@Test(priority = 2, enabled = true)
	public void testCartFunction() {
		test = report.startTest("Add and delete product from cart");
		driver.navigate().to("http://automationpractice.com/index.php");
		homepage.goToWomenProductPage();
		// go to product page in women section
		womenproduct = new WomenProductPage(driver);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Waiting for product page to load...");
				return womenproduct.checkPageLoaded();
			}
		});
		assertTrue(womenproduct.checkPageLoaded());
		// go to first product page
		womenproduct.goToFirstProduct();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking first product page is loaded..");
				return womenproduct.checkProductPage();
			}
		});
		// add first product to cart
		womenproduct.addProductToCart();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking first successful purchase page..");
				return womenproduct.checkSuccessfulPurchase();
			}
		});
		assertTrue(womenproduct.checkSuccessfulPurchase());
		test.log(LogStatus.INFO, "Added first product to cart");

		// go back to main page
		driver.navigate().to("http://automationpractice.com/index.php");
		homepage.goToWomenProductPage();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Waiting for product page to load...");
				return womenproduct.checkPageLoaded();
			}
		});
		assertTrue(womenproduct.checkPageLoaded());
		// go to second product page
		womenproduct.goToSecondProduct();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking second product page is loaded..");
				return womenproduct.checkProductPage();
			}
		});
		// add second product to cart
		womenproduct.addProductToCart();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking second successful purchase page..");
				return womenproduct.checkSuccessfulPurchase();
			}
		});
		assertTrue(womenproduct.checkSuccessfulPurchase());
		test.log(LogStatus.INFO, "Added second product to cart");

		driver.navigate().to("http://automationpractice.com/index.php");
		homepage.clickOnCart();
		cartpage = new CartPage(driver);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking if checkout page is loaded..");
				return cartpage.checkCartPageLoaded();
			}
		});
		test.log(LogStatus.INFO, "Browsed to cart page");
		cartpage.checkTwoProducts();
		test.log(LogStatus.INFO, "Confirmed 2 products on cart");
		cartpage.deleteProduct();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				cartpage = new CartPage(webDriver);
				System.out.println("Checking if first product is deleted..");
				return cartpage.checkFirstProductDeleted();
			}
		});
		assertTrue(cartpage.checkFirstProductDeleted());
		test.log(LogStatus.INFO, "Deleted first product");

		if (cartpage.checkFirstProductDeleted()) {
			test.log(LogStatus.PASS, "Add and delete items to cart");
		} else {
			test.log(LogStatus.FAIL, "Add and delete items to cart");
		}
		report.endTest(test);
		report.flush();
	}

	@Test(priority = 3, enabled = true)
	public void testReviewFunction() {
		test = report.startTest("Leave review for a product");
		driver.navigate().to("http://automationpractice.com/index.php");
		homepage.goToWomenProductPage();
		// go to product page in women section
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Waiting for product page to load...");
				return womenproduct.checkPageLoaded();
			}
		});
		assertTrue(womenproduct.checkPageLoaded());
		test.log(LogStatus.INFO, "Navigated to product page");

		// go to first product page
		womenproduct.goToFirstProduct();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking first product page is loaded..");
				return womenproduct.checkProductPage();
			}
		});
		womenproduct.clickReviewButton();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking first product page is loaded..");
				return womenproduct.checkreviewpage();
			}
		});
		test.log(LogStatus.INFO, "Navigated to review page");
		womenproduct.addreviewTitle("Noice");
		womenproduct.addreviewContent("Wow, it's so ugly!");
		womenproduct.submitreview();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking first product page is loaded..");
				return womenproduct.checkCommentOK();
			}
		});
		assertTrue(womenproduct.checkCommentOK());
		test.log(LogStatus.INFO, "Left review for moderater to approve");
		if (womenproduct.checkCommentOK()) {
			test.log(LogStatus.PASS, "Leave a review for product");
		} else {
			test.log(LogStatus.FAIL, "Leave a review for product");
		}
		report.endTest(test);
		report.flush();
	}

	@Test(priority = 4, enabled = true)
	public void testCheckoutFunction() {
		test = report.startTest("Make a purchase");
		// go back to main page
		driver.navigate().to("http://automationpractice.com/index.php");
		homepage.clickOnCart();
		cartpage = new CartPage(driver);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking if checkout page is loaded..");
				return cartpage.checkCartPageLoaded();
			}
		});
		test.log(LogStatus.INFO, "Browse cart page");
		cartpage.clickCheckoutButton();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking if checkout page is loaded..");
				return cartpage.checkCheckoutPageHeading("ADDRESSES") && cartpage.checkAddress();
			}
		});
		test.log(LogStatus.INFO, "Move to confirm address page");
		cartpage.confirmAddress();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking if checkout page is loaded..");
				return cartpage.checkCheckoutPageHeading("SHIPPING");
			}
		});
		test.log(LogStatus.INFO, "Move to shipping page");
		cartpage.agreeTOS();
		cartpage.confirmShipping();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking if checkout page is loaded..");
				return cartpage.checkCheckoutPageHeading("PLEASE CHOOSE YOUR PAYMENT METHOD");
			}
		});
		test.log(LogStatus.INFO, "Move to payment method page");
		cartpage.payByBankWire();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking if checkout page is loaded..");
				return cartpage.checkCheckoutPageHeading("ORDER SUMMARY");
			}
		});
		test.log(LogStatus.INFO, "Move to order summary page");
		cartpage.clickConfirmOrderButton();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Checking if checkout page is loaded..");
				return cartpage.checkCheckoutPageHeading("ORDER CONFIRMATION");
			}
		});
		assertTrue(cartpage.checkCheckoutPageHeading("ORDER CONFIRMATION"));
		if (cartpage.checkCheckoutPageHeading("ORDER CONFIRMATION")) {
			test.log(LogStatus.PASS, "Order confirmed");
		} else {
			test.log(LogStatus.FAIL, "Order confirmed");
		}
		report.endTest(test);
		report.flush();
	}

	@Test(priority = 5, enabled = true)
	public void testLogoutFunction() {
		test = report.startTest("Logout");
		driver.navigate().to("http://automationpractice.com/index.php?controller=my-account");
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Searching for account page...");
				return accountpage.checkisAccountPage();
			}
		});
		assertEquals(accountpage.getHeading(), "MY ACCOUNT");
		test.log(LogStatus.INFO, "Browse account page");
		accountpage.logoutaccount();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Searching for account page...");
				return accountpage.getHeading().equals("AUTHENTICATION");
			}
		});
		assertTrue(accountpage.checkloggedout());
		if (accountpage.checkloggedout()) {
			test.log(LogStatus.PASS, "Logged out");
		} else {
			test.log(LogStatus.FAIL, "Logged out");
		}
		report.endTest(test);
		report.flush();
		driver.quit();
	}
}
