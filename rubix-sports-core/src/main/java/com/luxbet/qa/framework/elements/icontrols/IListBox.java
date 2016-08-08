package com.luxbet.qa.framework.elements.icontrols;

public interface IListBox extends IControlBase {

	/**
	 * Selects the first item in the list box
	 * @author nakuladevas
     * @since 23 Jul 2014
	 */
    String SelectFirstItem();
    
    /**
	 * Selects the specified item in the list box.
	 * @param byName > Name of the item to select
	 * @author nakuladevas
     * @since 23 Jul 2014
     */
    void SelectItem(String byName);
    void DeSelectItem(String byName);
    void SelectMultipleItems(String csvItemsList);
    org.openqa.selenium.interactions.Actions SelectAllItems();
    void DeSelectAll();
    public void VerifyItemPresent(String listItem);
    public void VerifyItemsPresent(String csvOneOrMoreListItems);
    public void VerifyItemNotPresent(String item);
    public void VerifyItemsNotPresent(String csvOneOrMoreListItems);
    public void VerifyListIsEmpty();
    void VerifyItemLocked(String listItem);
}
