package controller;

import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class PotentialHomes {

	public static void main(String[] args)throws InterruptedException, IOException, AWTException {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("-incognito");
		options.addArguments("--start-maximized");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Jonathan\\"
				+ "Desktop\\School\\16-17\\Spring 2017\\4630\\geckodriver.exe");
		WebDriver driver = new ChromeDriver(capabilities);

	HomeSettings homeOptions = new HomeSettings(driver);
		homeOptions.userInput();
		homeOptions.initializeSettings();
		//Finds and disables all the non-relevant real estate options


		//Instantiates empty variables to be used in for loop
		String fullText = "";
		String numToString = "";
		int rowCount = 2;

		StaleReferenceConditions g = new StaleReferenceConditions();
		//Creates a list of all the houses on the current page that will eventually be clicked 
		List<WebElement> li = driver.findElements(By.xpath("//a[contains(@href,'/homedetails')]"));
		System.out.println(li.size()); //Troubleshooting

		//Creates the excel sheet used for exporting
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Number Values");

		Map<String, Object[]> data = new HashMap<String, Object[]>();
		//Column names for excel sheet
		data.put("1", new Object[] {"Address", "Sale Price", "Zillow Sale Price", "Rent Estimate"});
		OutputBuilder oBuild = new OutputBuilder();
		//Runs until the next option no longer shows
		while(driver.findElements(By.className("zsg-pagination-next")).size() > 0)
		{
			li = driver.findElements(By.xpath("//a[contains(@href,'/homedetails')]"));
			//Used later to return to page showing all houses
			String defUrl = driver.getCurrentUrl(); 
			System.out.println(li.size());
			for(int x = 0; x < li.size(); x++ )
			{
				numToString = Integer.toString(rowCount);
				rowCount++;
				li = driver.findElements(By.xpath("//a[contains(@href,'/homedetails')]"));

				try{
					li.get(x).click();
					}
				catch (WebDriverException e){
					continue;
					}
				
				Thread.sleep(5000);
				try
					{
					driver.switchTo().defaultContent();
					}
				catch (TimeoutException e)
				{
					driver.get(driver.getCurrentUrl());
					Thread.sleep(5000);
					driver.switchTo().defaultContent();
				}
				//driver.switchTo().parentFrame();
				fullText = driver.findElement(By.tagName("body")).getText();
				System.out.println(fullText);
				oBuild.setFullText(fullText);

				if(!(fullText.contains("and has been priced for")))
				{
					driver.switchTo().defaultContent();
				}
				fullText = driver.findElement(By.tagName("body")).getText();
				if(!(fullText.contains("and has been priced for")))
				{
					continue;
				}
				fullText = oBuild.cleanUpFullText();
				if(fullText.contains("Zestimate"))
				{
					fullText = fullText.substring(fullText.indexOf("Zestimate"),fullText.length());
					oBuild.setFullText(fullText);
				}
				else
				{
					driver.get(defUrl);
					continue;
				}

				oBuild.createOutputs();
				System.out.println(oBuild.getSalePrice() + " "  + oBuild.getAddress() + " " + oBuild.getzSalePrice() + " " +  oBuild.getzRentEstimate());
				data.put(numToString, new Object[] {oBuild.getAddress(), oBuild.getSalePrice(), oBuild.getzSalePrice(), oBuild.getzRentEstimate()});
				driver.get(defUrl);
			}
			try{
				Thread.sleep(5000);
				g.retryingFindClick(driver.findElement(By.className("zsg-pagination-next")));}
			catch (WebDriverException e)
			{
				driver.get(defUrl);
				Thread.sleep(5000);
				g.retryingFindClick(driver.findElement(By.className("zsg-pagination-next")));
			}
			Thread.sleep(5000);
		}
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
					new FileOutputStream(new File("C:/Users/Jonathan/Desktop/" + homeOptions.getZipCode() + ".xls"));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		workbook.close();
		driver.quit();



	}

}
