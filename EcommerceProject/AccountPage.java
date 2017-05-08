package EcommerceProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage {
	WebDriver driver;
	By accountheading = By.className("page-heading");
	By signin = By.linkText("Sign in");
	By logoutbutton = By.className("logout");
	By orderbutton = By.xpath("/html/body/div/div[2]/div/div[3]/div/div/div[1]/ul/li[1]/a/span");

	public AccountPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getHeading() {
		return driver.findElement(accountheading).getText();
	}

	public void logoutaccount() {
		driver.findElement(logoutbutton).click();
	}

	public boolean checkloggedout() {
		return driver.findElement(signin) != null;
	}

	public boolean checkisAccountPage() {
		return driver.findElement(orderbutton).getText().equals("ORDER HISTORY AND DETAILS");
	}
}
