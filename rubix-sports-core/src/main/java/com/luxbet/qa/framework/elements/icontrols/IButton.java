package com.luxbet.qa.framework.elements.icontrols;

public interface IButton extends IControlBase {
	/**
	 * Performs a click action on the button element
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
	public void Click();
	
	/**
	 * @author nakuladevas
	 * @since 22 July 2014 
	 * @return Returns the name of the button 
	 */
	public String GetName();
	
	/**
	 * Compares the name of the button with the expected value and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 * @param expected -> Expected button name to verify
	 */
	public void VerifyName(String expected);
	
}
