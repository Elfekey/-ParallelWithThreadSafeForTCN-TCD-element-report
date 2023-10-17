package Base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;

import Utilities.WordDocumentEvidence;
import Utilities.extentReport;
//import Utilities.extentReport;
import Utilities.screenShots;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest   {
	//	public   WebDriver driver;
	//for driver threadsafe for parallel execution we do the below 
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	//end of webdriver threadlocal

	public  WebDriverWait wait;
	//for tcname and description threadsafe for parallel execution we do the below 
	private static final ThreadLocal<String> tCName = new ThreadLocal<>();
	private static final ThreadLocal<String> tCDescription = new ThreadLocal<>();
	private static final ThreadLocal<String> tcStatus = new ThreadLocal<>();

	//end of webdriver threadlocal
	 WordDocumentEvidence wordDocumentEvidenceObject;
	//	protected screenShots screenShotsOb;

	//exxtent report class object to use it in all childs
	protected extentReport extentreportObject  = new extentReport();
	//screenshots  class object to use it in all childs
	protected screenShots screenShotsOb = new screenShots();

	//#######below we are using thread safe to keep every instance of the test and not override them!!!
	//	public static ThreadLocal<ExtentTest> extentTesThreadLocal = new  ThreadLocal<ExtentTest>();
	//#######End of thread local for thread safe 
	//#######below headless option use!!!
	ChromeOptions options = new ChromeOptions();
	/*
	 * FirefoxOptions options = new FirefoxOptions();
	options.setHeadless(true);
	WebDriver driver = new FirefoxDriver(options);
	driver.get("https://demoqa.com/");
	System.out.println("Title of the page is -> " + driver.getTitle());
	driver.close();
	} 
	 */
	//#######End of headless option use!!!
	//get driver method 
	public static WebDriver getDriver() {
		return driver.get();//to return the value of this thread safe 
	}
	
	//Getting value of tcname and tcDescription for thread local
	public static String getTCName() {
		return tCName.get();//to return the value of this thread safe 
	}
	
	public static String getTCD() {
		return tCDescription.get();//to return the value of this thread safe 
	}
	public static String getTCStatus() {
		return tcStatus.get();
	}

	
	//the before suite
	@BeforeSuite
	public void setupSuite() {
		WebDriverManager.chromedriver().setup();
		//open report page in the beginning 
		extentReport.setUpExtent();
		extentreportObject.open_reportPage(); 
	}

	@BeforeMethod
	public synchronized void beforeMethod(ITestResult result) {
		//		driver=new ChromeDriver();
		//#######Start of headless option use!!!
		options.setHeadless(true);
		//		driver=new ChromeDriver(options);

		//instead of the abouve line we do the belwo for threadloacal set driver
		//threadguard is for making sure the driver is only called by the thread that created it
		driver.set(ThreadGuard.protect(new ChromeDriver(options)));
		//#######End of headless option use!!!
		//		driver.manage().window().maximize();
		//the below is instead of the above line for thread safe set value
		getDriver().manage().window().maximize();
		//setting up webdriver listener for actions
		getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(getDriver(),30);

		//	//getting tc name and description
		//		tCName =result.getMethod().getMethodName();
		//		tCDescription=result.getMethod().getDescription();
		//setting value of tcname and tcDescription for thread local
		tCName.set(result.getMethod().getMethodName());
		tCDescription.set(result.getMethod().getDescription());


		//##Creating tests and set the test as thread safe
		//		reportOb.test = extentReport.extent.createTest(tCName + "_" +tCDescription);
//		extentreportObject.extentTestThreadLocal = extentReport.extent.createTest(getTCName() + "_" +getTCD());
//		extentreportObject.extentTestThreadLocal.set(extentReport.extent.createTest(getTCName() + "_" +getTCD())) ;
		//I removed the object because the varible is already static so i can access it directly
		extentReport.extentTestThreadLocal.set(extentReport.extent.createTest(getTCName() + "_" +getTCD())) ;
//				extentTesThreadLocal.set(reportOb.test);
		//##End of Creating tests and set the test as thread safe

	}
	//after method anotaion we'll be performed after test case is done
		@AfterMethod
		public synchronized void afterMethod(ITestResult result) {
			try {
				wordDocumentEvidenceObject = new WordDocumentEvidence();
				if (result.getStatus() == ITestResult.SUCCESS) {
					//setting up name of status
					tcStatus.set("Passed");

					//setting up the status of the test case
					//  log the status to the html report 
//					extentreportObject.extentTestThreadLocal.log(Status.PASS, getTCName()+"  :  "+getTCStatus());
					extentReport.getextentTestThreadLocal().log(Status.PASS, getTCName()+"  :  "+getTCStatus());
					//calling a helper method
					insideStatusOfTestCase(getTCStatus());
				}
				else if (result.getStatus() == ITestResult.FAILURE) {
					//setting up name of status
					//				String status = "Failed";
					tcStatus.set("Failed");
					//  log the status to the html report 
//					extentreportObject.extentTestThreadLocal.log(Status.FAIL, getTCName()+"  :  "+getTCStatus());
//					extentreportObject.extentTestThreadLocal.fail(result.getThrowable());
					extentReport.getextentTestThreadLocal().log(Status.FAIL, getTCName()+"  :  "+getTCStatus());
					extentReport.getextentTestThreadLocal().fail(result.getThrowable());
					//calling a helper method
					insideStatusOfTestCase(getTCStatus());
				} 
				else {
					//setting up name of status
					tcStatus.set("Skiped");
					//  log the status to the html report 
//					extentreportObject.extentTestThreadLocal.log(Status.SKIP, getTCName()+"  :  "+getTCStatus());
					extentReport.getextentTestThreadLocal().log(Status.SKIP, getTCName()+"  :  "+getTCStatus());
					
					//calling a helper method
					insideStatusOfTestCase(getTCStatus());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	@AfterSuite
	public void afterSuite() {

				extentreportObject.refreshReport();

	}

	//>>>Below the section of Helper methods to reduce the code redundancy<<< 

	//the below will help to have the screenshots inserted inside word document and report 
	//It'll be used inside each status of each test case 
	public void insideStatusOfTestCase(String status) {

		//rename the folder to new name with status
		screenShotsOb.renameScreenShotsFolder(getTCName(), getTCD(),status);
		//save screenshots to word evidence file
		wordDocumentEvidenceObject.saveAllScreenShotsIntoWordDocument(getTCName(), getTCD(),status);
		//		//log the status to the html report and insert screenshot to it
		extentreportObject.InsertAllImagesToTheReport(getTCName(), getTCD(),status);
		//updating and refreshing the report
		extentReport.extent.flush();
		extentreportObject.refreshReport();
		//closing after the method
		//		TearDown();
	}
	//after  test method  
		public synchronized void TearDown() {
			//the below is instead of the above lines for threadlocal
			//		/driver.remove is to remove the threadlocal value 
			if (getDriver() != null) {
				getDriver().quit();
				//		/driver.remove is to remove the threadlocal value 
				//here we are acting with the thread so that we don't need to use getDriver() method
				driver.remove();

			}
		}




}