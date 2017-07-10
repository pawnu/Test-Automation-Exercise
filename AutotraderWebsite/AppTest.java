package Autotrader.AutotraderWebsite;
import java.io.IOException;
import java.util.Scanner;
import static org.testng.Assert.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


/**
 * Unit test for Auto Trader.
 * Checks search by make and model is correct or not.
 */
public class AppTest {
	WebDriver driver;
	Wait<WebDriver> wait;
	ExtentReports report;
	ExtentTest test;
	MainPage mainpage;
	SearchedPage searchpage;
	private Scanner sc;
	@Test(priority = 1, enabled = true)
	public void TestSearchFunction() throws IOException {
		// Create report file on desktop
		report = new ExtentReports("C:\\Users\\felix\\Desktop\\ATtestreport.html", true);
		// init/start the test
		test = report.startTest("Load page and search for result.");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\felix\\Desktop\\QAC\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\felix\\Desktop\\QAC\\Selenium\\geckodriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.autotrader.co.uk/");
		wait = new WebDriverWait(driver, 25);
		test.log(LogStatus.INFO, "Browser loaded");
		String inputpostcode, inputmake, inputmodel;
		mainpage = new MainPage(driver);
		assertTrue(mainpage.checkPageLoaded());
		test.log(LogStatus.PASS, "Homepage loaded");

		sc = new Scanner(System.in);
		System.out.println("Input postcode");
		inputpostcode = sc.nextLine();
		mainpage.setPostcode(inputpostcode);
		
		sc = new Scanner(System.in);
		System.out.println("Input car manufacturer");
		inputmake = sc.nextLine();
		mainpage.selectMake(inputmake);
		
		sc = new Scanner(System.in);
		System.out.println("Input car model");
		inputmodel = sc.nextLine();
		mainpage.setModel(inputmodel);
		
		mainpage.clickSearchButton();
		
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Waiting for search page...");
				return mainpage.checkPageLoaded();
			}
		});
		searchpage = new SearchedPage(driver);
		assertTrue(searchpage.verifyLoaded());
		test.log(LogStatus.PASS, "Search page loaded.");
		
		if (searchpage.checkMake(inputmake)) {
			test.log(LogStatus.PASS, "Searched for the make " + inputmake );
		} else {
			test.log(LogStatus.FAIL, "Searched for the make " + inputmake);
		}
		if (searchpage.checkModel(inputmodel)) {
			test.log(LogStatus.PASS, "Searched for the make " + inputmodel );
		} else {
			test.log(LogStatus.FAIL, "Searched for the make " + inputmodel);
		}		
		report.endTest(test);
		report.flush();
		driver.quit();
	}
}