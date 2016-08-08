package com.luxbet.qa.framework.elements.icontrols;

public interface IDropDownList extends IControlBase {
	
	/**
	 * Selects the specified value on the drop down list (non-editable).
	 * @param valueToSelect > Name of the drop down list item to be selected.
	 * @author nakuladevas
     * @since 22 Jul 2014
	 */
	void Select(String valueToSelect);
    
	/**
     * @return Returns the currently selected item
     * @author nakuladevas
     * @since 22 Jul 2014
     */
	String GetSelectedItem();
    
	/**
     * Verifies that currently selected item matches with the expected value, otherwise logs a verification failure in the test log.
     * @param expected > The value to verify as selected item
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifySelectedItem(String expected);    
    
    /**
     * Verifies that the drop down list contains given items in the list, otherwise logs a verification failure in the test log.
     * @param csvOneOrMoreListItems > Comma separated one or more items (no space in between) to verify whether they're present in the drop down list
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifyItemsPresent(String csvOneOrMoreListItems);
    
    /**
     * Verifies that the drop down list ONLY shows the given items in the list, otherwise logs a verification failure in the test log.
     * @param csvAllListItems > All items expected to be in the list, separated by a comma.
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifyList(String csvAllListItems);
    
    /**
     * Verifies that the given item is present in the list, otherwise logs a verification failure in the test log.
     * @param item > An item name that is expected to be in the drop down list
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifyItemPresent(String item);
    
    /**
     * Verifies that the given item is NOT present in the list, otherwise logs a verification failure in the test log.
     * @param item > An item name that is NOT expected to be in the drop down list
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    void VerifyItemNotPresent(String item);
    
    /**
     * Verifies that the drop down list is empty, otherwise logs a verification failure in the test log.
     * @author nakuladevas
     * @since 22 Jul 2014
     */
    public void VerifyListIsEmpty();

    /**
     * Made public for the Redbook project
     */
    public boolean isItemInTheList(String item);

}
