package com.luxbet.qa.framework.elements.icontrols;

public interface IRadioButton extends IControlBase{
	
	/**
	 * Selects the radio button.
	 * @author nakuladevas
     * @since 23 Jul 2014
	 */
	public void Select();
	
	/**
	 * @return Returns true if selected, otherwise false.
	 * @author nakuladevas
     * @since 23 Jul 2014
	 */
	public boolean IsSelected();
	
	/**
	 * Verifies whether a radio button is selected, otherwise logs a verification failure in the test log.
	 * @author nakuladevas
     * @since 23 Jul 2014
	 */
	public void VerifySelected();
	
	/**
	 * Verifies whether a radio button is NOT selected, otherwise logs a verification failure in the test log.
	 * @author nakuladevas
     * @since 23 Jul 2014
	 */
	public void VerifyNotSelected();
}
