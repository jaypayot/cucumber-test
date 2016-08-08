package com.luxbet.qa.framework.elements.icontrols;

/**
 * Include all Image button specific interfaces here
 * @author nakuladevas
 * @since 23 July 2014
 */
public interface IImageButton extends IControlBase{
	/**
	 * Performs a click action on the image button element
	 * @author nakuladevas
	 * @since 23 July 2014 
	 */
	public void Click();
	
	/**
	 * @author nakuladevas
	 * @since 23 July 2014 
	 * @return Returns the name of the image button 
	 */
    public String GetName();
    
    /**
	 * Compares the name of the button with the expected value and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 23 July 2014 
	 * @param expected -> Expected image button name to verify
	 */
    public void VerifyName(String expected);	
}
