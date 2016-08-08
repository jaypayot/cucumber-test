package com.luxbet.qa.framework.testng;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomTestListener extends TestListenerAdapter {
	private int m_count = 0;
	 
	@Override
	public void onTestFailure(ITestResult tr) {
		logme("F");
		//TestBase.reportLogScreenshot();
	}
	 
	@Override
	public void onTestSkipped(ITestResult tr) {
		logme("S");
	}
	 
	@Override
	public void onTestSuccess(ITestResult tr) {
		logme(".");
	}
	 
	private void logme(String string) {
		System.out.print(string);
	    if (++m_count % 40 == 0) {
	    	System.out.println("");
	    }
	}
}
