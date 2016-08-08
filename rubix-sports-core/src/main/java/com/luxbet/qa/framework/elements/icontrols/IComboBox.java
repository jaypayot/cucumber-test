package com.luxbet.qa.framework.elements.icontrols;

public interface IComboBox extends IControlBase{
	
	/**
	 * Selects the specified value on the combo box.
	 * @param valueToSelect > A case sensitive value to be selected 
	 */
    public void Select(String valueToSelect);

    /**
     * Inputs the specified combo value directly on to the combo edit box, it will clear out any existing selection first.
     * @param valueToSelect
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void Set(String valueToSelect);
    
    /**
     * Clears out any existing selection.
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void Clear();
    
    /**
     * Verifies that currently selected item matches with the expected value, else a verification failure will be logged in the test log.
     * @param expected > The value to verify as selected item
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifySelectedItem(String expected);

    /**
     * Verifies that the combo list contains given items in the list, otherwise logs a verification failure in the test log.
     * @param csvOneOrMoreListItems > Comma separated one or more items (no space in between) to verify whether they're present in the combo list
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifyItemsPresent(String csvOneOrMoreListItems);
    
    /**
     * Verifies that the combo list ONLY shows the given items in the list, otherwise logs a verification failure in the test log.
     * @param csvAllListItems > All items expected to be in the list, separated by a comma.
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifyList(String csvAllListItems);
    
    /**
     * Verifies that the combo list is empty, otherwise logs a verification failure in the test log.
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    public void VerifyListIsEmpty();

    /**
     * Verifies that the given item is present in the list, otherwise logs a verification failure in the test log.
     * @param item > An item name that is expected to be in the combo list
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifyItemPresent(String item);
    
    /**
     * Verifies that the given item is NOT present in the list, otherwise logs a verification failure in the test log.
     * @param item > An item name that is NOT expected to be in the combo list
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifyItemNotPresent(String item);
    
    /**
     * @return Returns the currently selected combo item
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    String GetSelectedItem();
}
