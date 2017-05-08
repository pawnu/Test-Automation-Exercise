package EcommerceProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SigninPage {
	WebDriver driver;
	By account = By.id("email");
	By password = By.id("passwd");
	By loginbutton = By.id("SubmitLogin");
	By myaccounttext = By.xpath("/html/body/div/div[2]/div/div[3]/div/h1");

	public SigninPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean checkSignPageLoaded() {
		return driver.findElement(loginbutton) != null;
	}

	public void login(String acc, String pass) {
		driver.findElement(account).sendKeys(acc);
		driver.findElement(password).sendKeys(pass);
		driver.findElement(loginbutton).click();
	}

	public Boolean checkLoggedIn() {
		return driver.findElement(myaccounttext).getText().equals("MY ACCOUNT");
	}

}
