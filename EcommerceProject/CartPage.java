package EcommerceProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
	WebDriver driver;
	By firstproduct = By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[1]/td[2]/p/a");
	By secondproduct = By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[2]");
	By firstdeletebutton = By.id("1_1_0_16728");
	By heading = By.id("cart_title");
	By checkoutbutton = By.xpath("/html/body/div/div[2]/div/div[3]/div/p[2]/a[1]");
	By addressCheckoutButton = By.xpath("/html/body/div/div[2]/div/div[3]/div/form/p/button");
	By shippingCheckoutButton = By.xpath("/html/body/div/div[2]/div/div[3]/div/div/form/p/button");
	By checkoutpageheading = By.className("page-heading");
	By checkbox = By.id("cgv");
	By wirepayment = By.className("bankwire");
	By confirmorderbutton = By.xpath("/html/body/div/div[2]/div/div[3]/div/form/p/button");
	String firstproductname;

	public CartPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean checkTwoProducts() {
		firstproductname = driver.findElement(firstproduct).getText();
		return driver.findElement(firstproduct) != null && driver.findElement(secondproduct) != null;
	}

	public void deleteProduct() {
		driver.findElement(firstdeletebutton).click();
	}

	public boolean checkFirstProductDeleted() {
		return !driver.findElement(firstproduct).getText().equals(firstproductname);
	}

	public boolean checkCartPageLoaded() {
		System.out.println(driver.findElement(heading).getText());
		return driver.findElement(heading).getText().contains("SHOPPING-CART SUMMARY");
	}

	public void clickCheckoutButton() {
		driver.findElement(checkoutbutton).click();
	}

	public void confirmAddress() {
		driver.findElement(addressCheckoutButton).click();

	}

	public boolean checkAddress() {
		return driver.findElement(addressCheckoutButton) != null;
	}

	public void confirmShipping() {
		driver.findElement(shippingCheckoutButton).click();
	}

	public boolean checkCheckoutPageHeading(String heading) {
		return driver.findElement(checkoutpageheading).getText().equals(heading);
	}

	public void agreeTOS() {
		driver.findElement(checkbox).click();
	}

	public void payByBankWire() {
		driver.findElement(wirepayment).click();
	}

	public void clickConfirmOrderButton() {
		driver.findElement(confirmorderbutton).click();
	}
}
