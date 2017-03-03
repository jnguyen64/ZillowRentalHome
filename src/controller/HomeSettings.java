package controller;

import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class HomeSettings {

	WebDriver driver;
	String zipCode = "";
	
	public HomeSettings()
	{
		driver = new ChromeDriver();
	}
		
	public HomeSettings(WebDriver driver1)
	{
		driver = driver1;
	}
		
		public void userInput() throws InterruptedException
		{
			driver.get("https://www.zillow.com/homes/");
			
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter a zip code for homes you'd like to look at.");
			zipCode = scan.nextLine();
			
			driver.findElement(By.id("citystatezip")).sendKeys(zipCode);
			driver.findElement(By.className("zsg-icon-searchglass")).click();
			System.out.println("Zip code successfully entered");
			Thread.sleep(1500);
			driver.findElement(By.id("price-menu-label")).click();
			System.out.println("Please choose a minimum price value (Enter 0 if none)");
			String minPrice = scan.nextLine();
			driver.findElement(By.id("price-min")).sendKeys(minPrice);
			System.out.println("Min price successfully entered.");
			System.out.println("Please enter a max price value (Enter 0 if none)");
			String maxPrice = scan.nextLine();
			scan.close();
			driver.findElement(By.id("price-max")).sendKeys(maxPrice);
			driver.findElement(By.id("price-max")).sendKeys(Keys.ENTER);
			Thread.sleep(2000);
		}
		
		public void initializeSettings() throws InterruptedException
		{
			
		driver.findElement(By.className("hometype-standalone")).click();
		driver.findElement(By.id("hometype-land-top-filters-label")).click();
		driver.findElement(By.id("listings-menu-label")).click();
		driver.findElement(By.id("pm-foreclosed")).click();
		driver.findElement(By.id("pm-pre-foreclosure")).click();
		driver.findElement(By.id("pm-mmm")).click();

		Thread.sleep(2000);
		}

		/**
		 * @param driver the driver to set
		 */
		public void setDriver(WebDriver driver) {
			this.driver = driver;
		}

		/**
		 * @param zipCode the zipCode to set
		 */
		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}

		/**
		 * @return the driver
		 */
		public WebDriver getDriver() {
			return driver;
		}

		/**
		 * @return the zipCode
		 */
		public String getZipCode() {
			return zipCode;
		}
		
	}


