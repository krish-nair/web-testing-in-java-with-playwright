package com.pw;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HomePage {
    Page page;

    private Locator addNewComputerButton;
    private Locator loadingSpinner;

    public HomePage(Page page) {
        this.page = page;
        this.addNewComputerButton = page.locator("text = Add a new computer");
        this.loadingSpinner = page.locator("#loading-spinner");
    }
    public void click(String text){
        page.locator(text).click();
    }

    public AddNewComputerPage clickOnAddNewComputerButton(){
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add a new computer")).click();
        return new AddNewComputerPage(page);
    }
    public void waitForDashboardToLoad() {
        loadingSpinner.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
    }
    public String getPageTitle(){
        return page.title();
    }
}
