package controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PotentialHomes3 {

	public static void main(String[] args)throws InterruptedException, IOException {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--start-maximized");
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Jonathan\\"
				+ "Desktop\\School\\16-17\\Spring 2017\\4630\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver(capabilities);
		
		driver.get("https://www.zillow.com/homes/for_sale/Warner-Robins-GA-31088/71434_rid/0-100000_price/0-375_mp/32.65"
				+ "484,-83.517209,32.506142,-83.736935_rect/11_zm/1_fr/");
		JavascriptExecutor jsx = (JavascriptExecutor)driver;
		jsx.executeScript("scroll(0,400)");
		Scanner scan = new Scanner(System.in);
		//works
		//driver.findElement(By.cssSelector("a[href*='/homedetails']")).click();
		String mainP = driver.getWindowHandle();
		System.out.println(mainP);
		System.out.println(driver.getCurrentUrl());
		List<WebElement> li = driver.findElements(By.xpath("//a[contains(@href,'/homedetails')]"));
		li.get(5).click();
		jsx.executeScript("scroll(0,400)");
		 driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	

		// driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		Thread.sleep(10000);
	  	//String dog = driver.findElement(By.xpath("//div[contains(@class,'zest-va')]")).getText();
		//System.out.println(dog);
		 //driver.findElement(By.name("Zestimate Details")).click();
		 driver.findElement(By.xpath("//h2[starts-with(@id")).click();
		 //driver.switchTo()Frame.("popupFrame");
		 driver.findElement(By.xpath("//h2[contains(@data-module, 'zestimate')]")).click();
		 driver.findElement(By.xpath("//div[starts-with(@id,'search-detail')]")).click();
		// driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		 String dog3 = "";
		 try{dog3 = driver.findElement(By.xpath("//div[contains(@class, 'zest-value')]")).getText();
		 System.out.println(dog3);} catch (NoSuchElementException e)
		 {
			 System.out.println("Couldn't find the class name");
		 }
		 try{dog3 = driver.findElement(By.className("zest-value")).getText();
		 System.out.println(dog3);} catch (NoSuchElementException e)
		 {
			 System.out.println("Couldn't find the class name");
		 }
		 
		 try{dog3 = driver.findElement(By.className("zestimate-value")).getText();
		 System.out.println(dog3);}catch (NoSuchElementException e){
			 System.out.println("Couldn't find the class name");
		 }
		List<WebElement> zest = driver.findElements(By.className("zest-value"));
		for(WebElement e: zest){
			System.out.println(e.getText());
		}
		System.out.println(zest.get(0));
		
		//List<WebElement> zest = driver.findElements(By.xpath("//div[contains(@class,'zest-details')]"));
		//String dog1 = zest.get(0).getText();
		//System.out.println(dog3);
		//System.out.println(zest);
		//driver.findElement(By.xpath("//a[contains(@href,'/homedetails')]")).click();
		//driver.findElement(By.xpath("//@href[starts-with(text(), '/homedetails'.)]")).click();
	}

}
