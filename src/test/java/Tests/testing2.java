package Tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import Base.GM2;
import Base.GeneralMethods;
import Pages.GooglePage;
import Utilities.*;
public class testing2 extends GM2 {

	//it'll be in the parent to use it inside all the class
	screenShots screenShots = new screenShots();
	GM2 gm = new GM2();

	//we must provide tcname and description in the testcase cause it's unique 
	@Test(description = "This is test case one now")
	public void test1() throws Exception {
		//then we can initaite it in before methods 
		//we must create object for the page we need to use in each test if we'll use it many times in many test cases
		GooglePage googlePage = new GooglePage(getDriver());
		
		//if we'll navigate manytimes we can get the tcname and description in varible and then use them
		
		//		NavigateTo("https://www.lambdatest.com/blog/extent-reports-in-selenium/",tc1nString,tc1DString);
		//		NavigateTo("https://www.google.com/",tc1nString,tc1DString);
		NavigateTo("https://www.lambdatest.com/blog/extent-reports-in-selenium/" );
		//		System.out.println("test1 tCName  "+getTCName() );
		//		System.out.println("test1 tCDescription  "+getTCD() );
		//		System.out.println("test1 getDriver  "+getDriver());
		//		
		NavigateTo("https://www.google.com/" );
//		//		System.out.println("test1 tCName  "+getTCName() );
//		//		System.out.println("test1 tCDescription  "+getTCD() );
//		//		System.out.println("test1 getDriver  "+getDriver());
//		//		NavigateTo("https://www.google.com/",tc1nString,tc1DString,,getDriver());//the above will work fine too
//		System.out.println("TC1 element search   "+googlePage.googleSearchElement);
//		sendText(googlePage.googleSearchElement, "test1");
	}

	//we must provide tcname and description in the testcase cause it's unique 
	@Test(description = "This isTC4")
	public void tc4() {
		//we must create object for the page we need to use in each test if we'll use it many times in many test cases
		GooglePage	googlePage = new GooglePage(getDriver());
		
		NavigateTo("https://translate.google.com/?sl=auto&tl=ar&op=translate" );
		//		System.out.println("tc4 tCName  "+getTCName());
		//		System.out.println("tc4 tCDescription  "+getTCD());
//		//		System.out.println("tc4 getDriver  "+getDriver());
		NavigateTo("https://www.google.com/" );
//		System.out.println("TC4 element search   "+googlePage.googleSearchElement);
//
//		sendText(googlePage.googleSearchElement, "tc4");
		assertTrue(false);

	}
	//we must provide tcname and description in the testcase cause it's unique 

//	@Test(description = "This isTC5",retryAnalyzer =retryFailedTCs.class)
	public void tc5() {
		//we must create object for the page we need to use in each test if we'll use it many times in many test cases
		GooglePage	googlePage = new GooglePage(getDriver());

		NavigateTo("https://www.youtube.com/" );
		//		System.out.println("tc5 tCName  "+getTCName());
		//		System.out.println("tc5 tCDescription  "+getTCD());
		//		System.out.println("tc5 getDriver  "+getDriver());
		//		assertTrue(false);
		NavigateTo("https://translate.google.com/?sl=auto&tl=ar&op=translate");
		//		System.out.println("tc5 tCName  "+getTCName());
		//		System.out.println("tc5 tCDescription  "+getTCD());
		//		System.out.println("tc5 getDriver  "+getDriver());
		NavigateTo("https://www.google.com/" );
		System.out.println("TC5 element search   "+googlePage.googleSearchElement);

		sendText(googlePage.googleSearchElement, "tc5");


	}





}