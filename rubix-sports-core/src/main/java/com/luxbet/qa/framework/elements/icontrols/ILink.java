package com.luxbet.qa.framework.elements.icontrols;

public interface ILink extends IControlBase {
	
	/**
	 * Performs a click action on the link element
	 * @author nakuladevas
	 * @since 23 July 2014 
	 */
	public void Click();

	/**
	 * Performs a get link text action on the link element
	 * @author bhattacharyad
	 * @since 04 April 2016
	 */
	public String  GetLinkText();

	/**
	 * Compares the name of the link with the expected value and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 23 July 2014 
	 * @param expected -> Expected link name to verify
	 */
	public void VerifyName(String expected);
	
	/**
	 * Verifies the link contains the expected portion, otherwise logs a verification failure in the test log.
	 * @author nakuladevas
	 * @since 23 July 2014 
	 * @param partialLink -> part of the link name to verify
	 */
    public void VerifyLinkContains(String partialLink);

}
