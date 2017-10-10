package IRCTCLogin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.finacle.util.Xls_Reader;

public class IRCTCLoginTest{
	
	WebDriver d;
	Xls_Reader xls=new Xls_Reader("C:\\Users\\logic\\Desktop\\IRCTC\\IRCTC_Data.xlsx");
  @Test(priority=1)
  public void Login() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\logic\\MySelenium\\IRCTC\\Driver\\geckodriver.exe");
		 d=new FirefoxDriver();
		// d.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	   d.get("http://www.irctc.co.in");
	 // d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  String title=d.getTitle();
	  Assert.assertEquals(title,"IRCTC Next Generation eTicketing System");
	  d.findElement(By.xpath("//input[@id='usernameId']")).sendKeys(xls.getCellData("IRCTC Cred","Username",2));
	  d.findElement(By.xpath("//input[@name='j_password']")).sendKeys(xls.getCellData("IRCTC Cred","Password",2));
	  Scanner scan=new Scanner(System.in);
	  System.out.println("Enter the Captcha");
	  String captcha=scan.nextLine();
	  scan.close();
	  d.findElement(By.xpath("//input[@id='nlpAnswer']	")).sendKeys(captcha);
	  d.findElement(By.xpath("//input[@id='loginbutton']")).click();
  }
  
  @Test(priority=2)
  public void doTicketBooking() throws InterruptedException{
	  WebDriverWait wait=new WebDriverWait(d,50);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='jpform:fromStation']")));
	  d.findElement(By.xpath("//input[@id='jpform:fromStation']")).sendKeys(xls.getCellData("IRCTC Cred","FromStation",2));
	  d.findElement(By.xpath("//input[@id='jpform:toStation']")).sendKeys(xls.getCellData("IRCTC Cred","Destination",2));
	  d.findElement(By.xpath("//input[@id='jpform:journeyDateInputDate']")).sendKeys(xls.getCellData("IRCTC Cred","DOJ(DD-MM-YYYY)",2));
	  d.findElement(By.xpath("//input[@id='jpform:jpsubmit']")).click();
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='jpform:fromStation']")));
	  //List<WebElement> radio_button=d.findElements(By.xpath("//input[@name='quota']"));
	  //d.findElement(By.xpath("//input[@value='GN']")).click();
	  WebElement radioTatkal= d.findElement(By.xpath("html/body/div[3]/div[2]/form[1]/div[2]/div/table[1]/tbody/tr/td[1]/div/table/tbody/tr/td[5]/input"));
	 ( (JavascriptExecutor)d).executeScript("arguments[0].click();",radioTatkal);
	  //d.findElement(By.xpath("//input[@value='TQ']")).click();
	  String train_No_Excel=xls.getCellData("IRCTC Cred","Train Number",2);
	  String train_class=xls.getCellData("IRCTC Cred","Class(2A/3A/SL)",2);	
	  int train_row=-10;
	  for(int i=1;i<=30;i++)
	  {
		  String train_No=d.findElement(By.xpath("html/body/div[3]/div[2]/form[1]/div[2]/div/table[2]/tbody[1]/tr["+i+"]/td[1]")).getText();
		  System.out.println(train_No);
		  if(train_No.equalsIgnoreCase(train_No_Excel))
		  {
			  train_row=i;
			  break;
		  }
		  
	  }
	  if(train_row==-10)
		  System.out.println("Train Number is invalid/Train is not present");
	  
	 if(train_class.equalsIgnoreCase("2A"))
		 d.findElement(By.xpath("html/body/div[3]/div[2]/form[1]/div[2]/div/table[2]/tbody[1]/tr["+train_row+"]/td[16]/a[1]")).click();
	 else if(train_class.equalsIgnoreCase("3A"))
		 d.findElement(By.xpath("html/body/div[3]/div[2]/form[1]/div[2]/div/table[2]/tbody[1]/tr["+train_row+"]/td[16]/a[2]")).click();
	 else
		 d.findElement(By.xpath("html/body/div[3]/div[2]/form[1]/div[2]/div/table[2]/tbody[1]/tr["+train_row+"]/td[16]/a[3]")).click();
	    
	  
  }
  @Test(priority=3)
  public void doVerifyTitle() throws IOException{
	  String pageTitle=d.getTitle();
	 System.out.println(pageTitle);
	 Assert.assertEquals(pageTitle, "E-Ticketing");
	 System.out.println("print this");
	 File scrFile=((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
	 FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//src//test//java//Screenshot//screenshot_"+Math.random()*100+".jpg"));
  }
}
