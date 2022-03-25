package quickstart.tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;

import functionLibrary.ReportLog;
import functionLibrary.TestData;
import io.github.bonigarcia.wdm.WebDriverManager;
import quickstart.Pages.GoogleSearchPage;
import quickstart.Pages.GoogleSearchResultPage;

public class BaseTest {
	WebDriver driver;
	GoogleSearchPage googleSearch;
	GoogleSearchResultPage googlesearchresults;
	ReportLog logger;

	TestData testdata;

	@BeforeSuite
	public void startTestSuite() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		logger = new ReportLog(driver);
		String currDir = System.getProperty("user.dir");
		try {
			testdata = new TestData(currDir + "\\TestData\\searchData.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Issue in Test data file opening; you can do some operation here, may be exit test");
		}
	}

}
