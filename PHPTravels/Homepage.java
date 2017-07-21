package ecommerce.travels;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Homepage {
	WebDriver driver;
	By pagelogo = By.xpath("/html/body/div[2]/div/div/div[1]/a/img");
	By currencyoption = By.id("currency");
	By languageoption = By.xpath("/html/body/div[2]/div/div/div[2]/ul/li[1]/a/img");
	By languagedisplaycheck = By.xpath("/html/body/div[9]/div/div[2]/div[1]/div/h2");
	By displayedcurrency = By.xpath("/html/body/div[9]/div/div[2]/div[2]/a/div/div[3]/div");
	By displayedlanguage = By.xpath("/html/body/div[9]/div/div[2]/div[1]/div/h2");
	By languagelist = By.xpath("/html/body/div[2]/div/div/div[2]/ul/li[1]/ul");
	Wait<WebDriver> wait;

	public Homepage(WebDriver driver){
		this.driver=driver;
	}
	
	public boolean checkPageLoaded(){
		return driver.findElement(pagelogo) != null;
	}
	
	public void selectCurrency(String currency){
		Select currencyselector = new Select(driver.findElement(currencyoption));		
		driver.findElement(currencyoption).click();
		currencyselector.selectByVisibleText(currency);
		//wait for page to send reload request
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);		
	}
	//the language input is the id used for selecting language web element
	public void selectLanguage(String language){
		driver.findElement(languageoption).click();

		wait = new WebDriverWait(driver, 25);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Waiting for list to show...");
				return driver.findElement(languagelist)!=null;
			}
		});
		driver.findElement(By.id(language)).click();
	}
	
	public boolean checkLanguageSet(String language){
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Waiting for language text to be displayed..");
				return driver.findElement(languagedisplaycheck)!=null;
			}
		});
		return driver.findElement(displayedlanguage).getText().equals(language);
	}
	//the text to test against may contain numbers e.g. £50.55
	//we only need to check that the currency sign exists
	public boolean checkCurrencySet(String currency){
		return driver.findElement(displayedcurrency).getText().contains(currency);
	}	
}
