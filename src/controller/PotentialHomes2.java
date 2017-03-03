package controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PotentialHomes2 {

	public static void main(String[] args)throws InterruptedException, IOException, AWTException {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("-incognito");
		options.addArguments("--start-maximized");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Jonathan\\"
				+ "Desktop\\School\\16-17\\Spring 2017\\4630\\geckodriver.exe");
		WebDriver driver = new ChromeDriver(capabilities);
		
driver.get("https://www.zillow.com/homes/");
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a zip code for homes you'd like to look at.");
		String zipCode = scan.nextLine();
		
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
		driver.findElement(By.id("price-max")).sendKeys(maxPrice);
		driver.findElement(By.id("price-max")).sendKeys(Keys.ENTER);
	
	
		System.out.println(driver.getCurrentUrl());
		List<WebElement> li = driver.findElements(By.xpath("//a[contains(@href,'/homedetails')]"));
		li.get(5).click();
		WebElement test = driver.findElement(By.xpath("//a[contains(@href,'/homedetails')]"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click()", test);
		System.out.println(driver.getCurrentUrl());
		Thread.sleep(5000);
		 driver.switchTo().defaultContent();
		
		String cat = driver.findElement(By.tagName("body")).getText();
		
		 cat = cat.substring(cat.lastIndexOf("and has been priced for sale at"));
		cat = cat.substring(0,cat.lastIndexOf("/mo."));
		String salePrice = cat.substring(cat.indexOf('$') ,cat.indexOf('.'));
		String address = cat.substring(cat.indexOf("for") + 3, cat.indexOf("is"));
		address = address.substring(address.indexOf("for ") + 4, address.length());
		String zSalePrice = cat.substring(cat.indexOf("is ") + 3, cat.indexOf(" and"));
		String zRentEstimate = cat.substring(cat.lastIndexOf("is $")+ 3, cat.length());
		System.out.println(cat);
		System.out.println(salePrice);
		System.out.println(address);
		System.out.println(zSalePrice);
		System.out.println(zRentEstimate);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
		
		Map<String, Object[]> data = new HashMap<String, Object[]>();
		data.put("1", new Object[] {"Address", "Sale Price", "Zillow Sale Price", "Rent Estimate"});
		data.put("2", new Object[] {address, salePrice, zSalePrice, zRentEstimate});
		
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if(obj instanceof Date) 
					cell.setCellValue((Date)obj);
				else if(obj instanceof Boolean)
					cell.setCellValue((Boolean)obj);
				else if(obj instanceof String)
					cell.setCellValue((String)obj);
				else if(obj instanceof Double)
					cell.setCellValue((Double)obj);
			}
		}
	
			
		
		
		try {
			FileOutputStream out = 
					new FileOutputStream(new File("C:/Users/Jonathan/Desktop/new.xls"));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		driver.quit();
		 driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		JavascriptExecutor jsx = (JavascriptExecutor)driver;
		jsx.executeScript("scroll(0,400)");

		// driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		Thread.sleep(10000);
	  	String dog = driver.findElement(By.xpath("//div[contains(@class,'zest-va')]")).getText();
		System.out.println(dog);
		 //driver.findElement(By.name("Zestimate Details")).click();

		// driver.findElement(By.xpath("//h2[contains(@data-module, 'zestimate')]")).click();
		// driver.findElement(By.xpath("//div[starts-with(@id,'search-detail')]")).click();
		// driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		/* String dog3 = "";
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
		*/
		//List<WebElement> zest = driver.findElements(By.xpath("//div[contains(@class,'zest-details')]"));
		//String dog1 = zest.get(0).getText();
		//System.out.println(dog3);
		//System.out.println(zest);
		//driver.findElement(By.xpath("//a[contains(@href,'/homedetails')]")).click();
		//driver.findElement(By.xpath("//@href[starts-with(text(), '/homedetails'.)]")).click();
	}

}
