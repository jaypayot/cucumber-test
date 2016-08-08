package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.core.SeleniumBase;
import com.luxbet.qa.framework.elements.icontrols.IListBox;
import com.luxbet.qa.framework.testng.Verify;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class ListBox extends ControlBase implements IListBox {
	private static Logger logger = Logger.getLogger(ListBox.class);
	private WebElement _listbox;
	@SuppressWarnings("unused")
	private By _by;
	
	public ListBox(WebElement listbox) {
		super(listbox);	
		this._listbox = listbox;
	}
	
	public ListBox(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public ListBox(String id) {
		this(By.id(id));
	}

	/********************************************************************/
	/* Add all private methods here */ 
	/********************************************************************/
    protected List<WebElement> getItems() {
		List<WebElement> items = _listbox.findElements(By.tagName("li"));
		return items;
	}
	
	protected boolean isItemInTheList(String itemName)
    {
        boolean itemFound = false;
        List<WebElement> items = getItems();
        for (WebElement e : items) {
        	if (getText(e).equals(itemName)) {
        		itemFound = true;
        		break;
        	}
        }
        return itemFound;
    }
	
	public WebElement getItem(String itemName)
    {
        WebElement item = null;
        List<WebElement> items = getItems();
        for (WebElement e : items) {
        	if (getText(e).equals(itemName)) {
        		item = e;
        		break;
        	}
        }
        return item;
    }
	
	public Actions selectItems(String csvItemsList, boolean performAction) {
		logger.info(String.format("selectItems(%s)", csvItemsList));
		String[] itemsToSelect = csvItemsList.split(",");
		Actions builder = new Actions(getDriver());
		builder.keyDown(Keys.CONTROL);
		
		for(String itemName : itemsToSelect) {
			builder.click(getItem(itemName));
			sleepms(200);			   
		}
		builder.keyUp(Keys.CONTROL);

		if(performAction) {
			buildAndExecuteActions(builder);
		}
		
		return builder;
	}

    public Actions selectItems(List<WebElement> items, boolean performAction) {
        logger.info("selectItems(elements)");
        Actions builder = new Actions(getDriver());
        builder.keyDown(Keys.CONTROL);

        for(int i=0; i<items.size(); i++) {
			builder.click(items.get(i));
            sleepms(200);
        }
        builder.keyUp(Keys.CONTROL);

        if(performAction) {
            buildAndExecuteActions(builder);
        }

        return builder;
    }

	public Actions selectItemsBottomToTop(List<WebElement> items, boolean performAction) {
		logger.info("selectItemsBottomToTop(elements)");
		Actions builder = new Actions(getDriver());
		builder.keyDown(Keys.CONTROL);

		for(int i=items.size()-1; i>=0; i--) {
			builder.click(items.get(i));
			sleepms(200);
		}
		builder.keyUp(Keys.CONTROL);

		if(performAction) {
			buildAndExecuteActions(builder);
		}

		return builder;
	}

	private boolean isItemSelected(WebElement item) {
		boolean selected = false;
		String classname = item.getCssValue("className");
		if (!classname.isEmpty()) {
			if (classname.endsWith("selected")) selected = true;
		}
		return selected;
	}
	
	protected List<WebElement> getSelectedItems() {
		SeleniumBase.TurnOffImplicitWait();
		List<WebElement> selectedItems = _listbox.findElements(By.cssSelector("li.highlight"));
		SeleniumBase.TurnOnImplicitWait();
		return selectedItems;
	}

    private List<WebElement> getLockedItems() {
        TurnOffImplicitWait();
        List<WebElement> lockedItems = _listbox.findElements(By.cssSelector("li.item-locked"));
        TurnOnImplicitWait();
        return lockedItems;
    }

    private List<WebElement> getHiddenItems() {
        TurnOffImplicitWait();
        List<WebElement> hiddenItems = _listbox.findElements(By.cssSelector("li.hidden"));
        TurnOnImplicitWait();
        return hiddenItems;
    }

    private List<WebElement> getUnLockedItems() {
        // Get all items
        List<WebElement> unlockedItems = getItems();
        List<WebElement> lockedItems = getLockedItems();
        List<WebElement> hiddenItems = getHiddenItems();
        // Remove locked items if any
        if(lockedItems.size() > 0) {
            unlockedItems.removeAll(lockedItems);
        }
        if(hiddenItems.size() > 0) {
            unlockedItems.removeAll(hiddenItems);
        }
        return unlockedItems;
    }

	public int selectedItemsCount() {
		return getSelectedItems().size();
	}
	
	/********************************************************************/
	/* Add interface implementation here */ 
	/********************************************************************/
	@Override
	public String SelectFirstItem() {
		logger.info(">> SelectFirstItem()...");
		String firstItemName = null;
		List<WebElement> listItems = getItems();
		if (listItems.size() > 0) {
			firstItemName = getText(listItems.get(0));
			listItems.get(0).click();
		}
		else{
			log("WARNING! SelectFirstItem() was called, but the list was empty - skipped!");
		}
		return firstItemName;
	}

	@Override
	public void DeSelectItem(String byName) {
		boolean deSelected = false;
		List<WebElement> items = getSelectedItems();
		for(WebElement i : items) {
			if(getText(i).equals(byName)) {
				// Item is selected
				selectItems(byName, true);
                deSelected = true;
				break;
			}
		}
		if(!deSelected) log("WARNING! Skipped attempt to deselect the item '" +byName+ "', it wasn't selected.");
	}

	@Override
	public void SelectItem(String byName) {
		logger.info(String.format("SelectItem(%s)" , byName));	
		getItem(byName).click();
		logger.info(">> Selected list item '" + byName + "'.");
	}
	
	@Override
	public void SelectMultipleItems(String csvItemsList) {
		logger.info(String.format("SelectMultipleItems(%s)" , csvItemsList));
        DeSelectAll();
		selectItems(csvItemsList, true);
	}

    @Override
    public Actions SelectAllItems() {
        logger.info(String.format("SelectAlItems()"));
        DeSelectAll();
        return selectItems(getItems(), true);
    }

    @Override
    public void DeSelectAll() {
        if(selectedItemsCount() > 0) {
            WebElement firstItem = getSelectedItems().get(0);
            String itemName = getText(firstItem);
            firstItem.click();
            DeSelectItem(itemName);
        }
    }

    public Actions SelectAllUnlockedItems() {
        logger.info(String.format("SelectAllUnlockedItems()"));
        return selectItems(getUnLockedItems(), true);
    }

    @Override
	public void VerifyItemsPresent(String csvOneOrMoreListItems) {
		logger.info(String.format("VerifyItemsPresent(%s)", csvOneOrMoreListItems));
		for (String item : csvOneOrMoreListItems.split(",")) {
			VerifyItemPresent(item);
        }
	}

	@Override
	public void VerifyItemPresent(String item) {
		Verify.verifyTrue(isItemInTheList(item), "FAIL: Expected item <" + item + "> not present in the list!");
	}

	@Override
	public void VerifyItemNotPresent(String item) {
		Verify.verifyFalse(isItemInTheList(item), "FAIL: Unexpected item <" + item + "> is present in the list!");		
	}

    @Override
    public void VerifyItemsNotPresent(String csvOneOrMoreListItems) {
        logger.info(String.format("VerifyItemsNotPresent(%s)", csvOneOrMoreListItems));
        for (String item : csvOneOrMoreListItems.split(",")) {
            VerifyItemNotPresent(item);
        }
    }

    @Override
	public void VerifyListIsEmpty() {
		Verify.verifyTrue(getItems().isEmpty(), "FAIL: The list is not empty!");
	}

    @Override
    public void VerifyItemLocked(String listItem) {
        boolean itemLocked = false;
        for(WebElement col: getLockedItems()) {
            if(getText(col).equals(listItem)) {
                itemLocked = true;
                break;
            }
        }
        Verify.verifyTrue(itemLocked, "FAIL: The item <" +listItem+ "> is not locked!");
    }

}
