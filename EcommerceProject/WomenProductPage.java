package EcommerceProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WomenProductPage {
	WebDriver driver;
	By comparebutton = By.className("compare-form");
	By product1 = By.xpath("/html/body/div/div[2]/div/div[3]/div[2]/ul/li[1]/div/div[1]/div/a[1]/img");
	By product2 = By.xpath("/html/body/div/div[2]/div/div[3]/div[2]/ul/li[2]/div/div[1]/div/a[1]/img");
	By pricedisplay = By.id("our_price_display");
	By addtocartbutton = By
			.xpath("/html/body/div/div[2]/div/div[3]/div/div/div/div[4]/form/div/div[3]/div[1]/p/button");
	By continuebutton = By.className("continue btn btn-default button exclusive-medium");
	By successwindow = By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[4]/div[1]/div[2]/div[4]");
	By reviewbutton = By.className("open-comment-form");
	By commenttitle = By.id("comment_title");
	By commentcontent = By.id("content");
	By commentok = By.xpath("/html/body/div[2]/div/div/div/p[2]/button");
	By submitbutton = By.id("submitNewMessage");

	public WomenProductPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean checkPageLoaded() {
		return driver.findElement(product2) != null;
	}

	public void goToFirstProduct() {
		driver.findElement(product1).click();
	}

	public boolean checkProductPage() {
		return driver.findElement(addtocartbutton) != null;
	}

	public void addProductToCart() {
		driver.findElement(addtocartbutton).click();
	}

	public boolean checkSuccessfulPurchase() {
		return driver.findElement(successwindow) != null;
	}

	public void goToSecondProduct() {
		driver.findElement(product2).click();
	}

	public void clickReviewButton() {
		driver.findElement(reviewbutton).click();
	}

	public boolean checkreviewpage() {
		return driver.findElement(commenttitle) != null;
	}

	public void addreviewTitle(String string) {
		driver.findElement(commenttitle).sendKeys(string);
	}

	public void addreviewContent(String string) {
		driver.findElement(commentcontent).sendKeys(string);
	}

	public boolean checkCommentOK() {
		return driver.findElement(commentok) != null;
	}

	public void submitreview() {
		driver.findElement(submitbutton).click();
	}
}
