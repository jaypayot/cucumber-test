package com.luxbet.qa.framework.elements.icontrols;

/**
 * Include any Check Box specific interfaces here
 * @author nakuladevas
 * @since 22 Jul 2014
 */
public interface ICheckBox extends IControlBase{
	/**
	 * Call it to change check box status to 'Checked' status
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
	void Check();
    
	/**
	 * Call it to change check box status to 'Unchecked' status
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
	void Uncheck();
    
    /**
	 * Verifies whether the check box is already checked, otherwise logs a verification failure in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
    void VerifyChecked();
    
    /**
	 * Verifies whether the check box is already unchecked, otherwise logs a verification failure in the test log.
	 * @author nakuladevas
	 * @since 22 July 2014 
	 */
    void VerifyUnchecked();
    
    /**
     * @author nakuladevas
	 * @since 22 July 2014 
     * @return Returns true if checked else false
     */
    boolean IsChecked();
}
