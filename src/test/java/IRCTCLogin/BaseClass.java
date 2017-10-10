package IRCTCLogin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;

import com.finacle.util.Xls_Reader;

public class BaseClass {
	WebDriver d;
	Xls_Reader xls=new Xls_Reader("C:\\Users\\logic\\Desktop\\IRCTC\\IRCTC_Data.xlsx");

}
