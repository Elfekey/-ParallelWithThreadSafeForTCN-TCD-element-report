package Base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.screenShots;

public class GM2 extends BaseTest{

	screenShots screenShots = new Utilities.screenShots();

		public synchronized void NavigateTo(String url) {//,String tcN,String TcDesc
	
			getDriver().navigate().to(url);
			screenShots.takeFullScreenshot(getTCName(),getTCD(),getDriver());
			System.out.println("TCName And TCD USed  from Nav Are "+getTCName()+"    "+getTCD());
		}


	public  void sendText(WebElement e,String text) {
		e.clear();
		e.sendKeys(text);
		screenShots.takeFullScreenshot(getTCName(),getTCD(),getDriver());

	}





}
