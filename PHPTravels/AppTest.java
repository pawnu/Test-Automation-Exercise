package ecommerce.travels;

import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;

/**
 * Language and currency option test for phptravel.net
 */
public class AppTest {
	ExtentReports report;
	ExtentTest test;
	WebDriver driver;
	Homepage homepage;
	Wait<WebDriver> wait;
	private static final String TestCurrency = "F:\\Workspace\\travels\\src\\test\\java\\ecommerce\\travels\\testcurrency.xlsx";
	private static final String TestLanguage = "F:\\Workspace\\travels\\src\\test\\java\\ecommerce\\travels\\testlanguage.xlsx";

	@BeforeClass
	public void setup() {
		// Create report file on desktop
		report = new ExtentReports("C:\\Users\\felix\\Desktop\\Phptravelstest.html", true);
		// init/start the test
		test = report.startTest("Test website is up and running.");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\felix\\Desktop\\QAC\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\felix\\Desktop\\QAC\\Selenium\\geckodriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.phptravels.net/");
		homepage = new Homepage(driver);
		wait = new WebDriverWait(driver, 25);
		test.log(LogStatus.INFO, "Browser started");
		waitForHomepage();
		assertTrue(homepage.checkPageLoaded());
		test.log(LogStatus.PASS, "Homepage loaded");
		report.endTest(test);
		report.flush();
	}

	@Test(priority = 1, enabled = true)
	public void testCurrency() {
		test = report.startTest("Test each currency is working as intended.");
		try {

			FileInputStream excelFile = new FileInputStream(new File(TestCurrency));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				// row number 0 is label i.e. input data, expected data
				if (currentRow.getRowNum() == 0) {
					continue;
				}
				String input = "";
				String expected = "";

				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getColumnIndex() == 0) {
						input = currentCell.getStringCellValue();
					}
					if (currentCell.getColumnIndex() == 1) {
						expected = currentCell.getStringCellValue();
					}
				}
				// start tests
				waitForHomepage();
				homepage.selectCurrency(input);
				driver.navigate().to("http://www.phptravels.net/");
				waitForHomepage();
				if (homepage.checkCurrencySet(expected)) {
					test.log(LogStatus.PASS, "Currency check testing -- " + input);

				} else {
					test.log(LogStatus.FAIL, "Currency check testing -- " + input);

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException nsee) {
			nsee.printStackTrace();
		}
		report.endTest(test);
		report.flush();
	}

	@Test(priority = 2, enabled = true)
	public void testLanguage() {
		test = report.startTest("Test each language pages are right.");
		driver.navigate().to("http://www.phptravels.net/");
		try {

			FileInputStream excelFile = new FileInputStream(new File(TestLanguage));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				// row number 0 is label i.e. input data, expected data
				if (currentRow.getRowNum() == 0) {
					continue;
				}
				String input = "";
				String expected = "";

				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getColumnIndex() == 0) {
						input = currentCell.getStringCellValue();
					}
					if (currentCell.getColumnIndex() == 1) {
						expected = currentCell.getStringCellValue();
					}
				}
				// start tests
				waitForHomepage();
				homepage.selectLanguage(input);
				if (homepage.checkLanguageSet(expected)) {
					test.log(LogStatus.PASS, "Language check for code -- " + input);
				} else {
					test.log(LogStatus.FAIL, "Language check for code -- " + input);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException nsee) {
			nsee.printStackTrace();
		}
		end();
	}

	private void end() {
		report.endTest(test);
		report.flush();
		driver.quit();
	}

	private void waitForHomepage() {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Waiting for page to load...");
				return homepage.checkPageLoaded();
			}
		});
	}
}
