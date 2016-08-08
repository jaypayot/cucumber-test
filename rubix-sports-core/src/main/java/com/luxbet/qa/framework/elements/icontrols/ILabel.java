package com.luxbet.qa.framework.elements.icontrols;

public interface ILabel extends IControlBase {
	
	/**
	 * @author nakuladevas
	 * @since 22 July 2014 
	 * @return Returns the name of the label 
	 */
	public String Get();
	
	/**
	 * Compares the name of the label with the expected value and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 * @param expected -> Expected label name to verify
	 */
    public void VerifyLabel(String expected);
    
    /**
	 * Verifies the label name contains the expected portion, otherwise logs a verification failure in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 * @param partialLabel -> part of the label name to verify
	 */
    public void VerifyLabelContains(String partialLabel);
}
