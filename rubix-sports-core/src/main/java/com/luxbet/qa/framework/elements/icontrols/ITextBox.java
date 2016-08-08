package com.luxbet.qa.framework.elements.icontrols;

public interface ITextBox extends IControlBase{
	/**
	 * Inputs value into the text field
	 * @param text > Value to enter
	 * @author nakuladevas
	 * @since 23 July 2014 
	 */
	void Set(String text);
    
	/**
	 * Clears any value already entered in the text box
	 * @author nakuladevas
	 * @since 23 July 2014 
	 */
	void Clear();
	    
	/**
	 * @return Returns the text value displayed in the text box
	 * @author nakuladevas
	 * @since 23 July 2014 
	 */
	String Get();
	
	
    /**
     * Verifies the text displayed in the text box with the expected value
     * @param expected
     * @author nakuladevas
	 * @since 23 July 2014 
     */
    void VerifyText(String expected);
    
    /**
     * Verifies the text box is empty
     * @author nakuladevas
	 * @since 23 July 2014 
     */
    void VerifyEmpty();
    
    /**
     * Verifies the text box is NOT empty
     * @author nakuladevas
	 * @since 23 July 2014 
     */
    void VerifyNotEmpty();
    
    
    /**
     * Call this method to verify field max length. Number of characters in textToEnter must be greater than the given expectedLength
     * @param textToEnter > Text to input > must be greater than the field length
     * @param expectedLength > Expected field length
     * @author nakuladevas
	 * @since 23 July 2014 
     */
    void VerifyFieldLength(String textToEnter, int expectedLength);
}
