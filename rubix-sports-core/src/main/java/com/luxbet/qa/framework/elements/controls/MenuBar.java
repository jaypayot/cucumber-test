package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.elements.icontrols.IMenuBar;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MenuBar extends ControlBase implements IMenuBar {
	private static Logger logger = Logger.getLogger(MenuBar.class);
	private WebElement _menubar;
	@SuppressWarnings("unused")
	private By _by;

	public MenuBar(WebElement menubar) {
		super(menubar);
		this._menubar = menubar;
	}

	public MenuBar(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}

	public MenuBar(String menubarId) {
		this(By.id(menubarId));
	}


    @Override
    public void SelectMenuOption(String menuItem_fullPath) {
        Actions actions = new Actions(getDriver());
        WebElement menuHoverLink = getDriver().findElement(By.linkText("RENT"));
        actions.moveToElement(menuHoverLink).perform();
        // TODO: TBC
        //getDriver().findElement(By.cssSelector("a[href='nemc.com/rentals/easy-rent']")).click();
    }
}
