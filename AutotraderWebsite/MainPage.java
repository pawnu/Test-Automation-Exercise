package Autotrader.AutotraderWebsite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class MainPage {
	WebDriver driver;
	By postcode = By.id("postcode");
	By make = By.id("searchVehiclesMake");
	By model = By.id("searchVehiclesModel");
	By searchbutton = By.id("search");
	By headerimage = By.xpath("/html/body/header/nav[2]/a");
	public MainPage(WebDriver driver){
		this.driver =driver;
	}
	public boolean checkPageLoaded(){
		return driver.findElement(headerimage)!=null;
	}
	public void setPostcode(String post){
		driver.findElement(postcode).sendKeys(post.toUpperCase());
	}
	
	public void selectMake(String vechiclename){
		driver.findElement(make).click();
		Select carmake = new Select(driver.findElement(make));
		carmake.selectByValue(vechiclename.toUpperCase());
	}
	
	public void setModel(String vechiclemodel){
		driver.findElement(model).click();		
		Select carmake = new Select(driver.findElement(model));
		carmake.selectByValue(vechiclemodel.toUpperCase());		
	}
	
	public void clickSearchButton(){
		driver.findElement(searchbutton).click();
	}
}
