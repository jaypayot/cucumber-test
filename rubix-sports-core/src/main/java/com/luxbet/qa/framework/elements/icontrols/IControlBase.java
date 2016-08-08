package com.luxbet.qa.framework.elements.icontrols;

/**
 * This is the base interface for all types of elements
 * @author Selva Nakuladeva
 * @since 22 Jul 2014
 */
public interface IControlBase {
	/**
	 * Verifies whether the control is enabled and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
    void VerifyEnabled();
        
	/**
	 * Verifies whether the control is disabled and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
    void VerifyDisabled();
    
	/**
	 * Verifies whether the control is displayed on the screen and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
    void VerifyDisplayed();
    
	/**
	 * Verifies whether the control is NOT displayed on the page and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
    void VerifyNotDisplayed();
    
	/**
	 * Verifies the tool tip of the control and logs any verification failures in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
    void VerifyTooltip(String tooltip);
    
	/**
	 * Verifies whether the control has focus or not. A verification failure will be logged if the focus is not on the control.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
    void VerifyFocused();
}
