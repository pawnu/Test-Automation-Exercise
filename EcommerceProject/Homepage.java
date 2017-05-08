package EcommerceProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Homepage {
	WebDriver driver;
	By signin = By.linkText("Sign in");
	By pageLogo = By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[1]/a/img");
	By womenbutton = By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[6]/ul/li[1]/a");
	By cartbutton = By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[3]/div/a");

	public Homepage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickSignin() {
		driver.findElement(signin).click();
	}

	public boolean checkLogoPresent() {
		return driver.findElement(pageLogo) != null;
	}

	public void goToWomenProductPage() {
		driver.findElement(womenbutton).click();
	}

	public void clickOnCart() {
		driver.findElement(cartbutton).click();
	}
}
