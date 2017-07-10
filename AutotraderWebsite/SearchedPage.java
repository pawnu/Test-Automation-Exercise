package Autotrader.AutotraderWebsite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchedPage {
	WebDriver driver;
	By searchsection = By.xpath("/html/body/main/section[1]/div[1]");
	By displayedmake = By.xpath("/html/body/main/section[1]/div[1]/form/ul/li[3]/div/button/span/span[2]");
	By displayedmodel = By.xpath("/html/body/main/section[1]/div[1]/form/ul/li[4]/div/button/span/span[2]");
	public SearchedPage (WebDriver driver){
		this.driver = driver;
	}
	public boolean verifyLoaded(){
		return driver.findElement(searchsection) != null;
	}
	
	public boolean checkMake(String make){
		return driver.findElement(displayedmake).getText().toUpperCase().contains(make.toUpperCase());
	}
	
	public boolean checkModel(String model){
		//return driver.findElement(displayedmodel).getText().toUpperCase().contains(model.toUpperCase());
		return false;
	}
}
