package nz.govt.linz.landonlite.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TitlePage {
    private Page page;
    private final String searchResultPageHeader = "text = Title #";
    private final String titleDetailsTableRow = "//table/tbody/tr";
    private final String loadingText = "text = Loading...";
    private final String changeOwnerCardTitle = "text = Change Owner";
    private final String newOwnerNameInputField = "//input[@placeholder='Enter the new owner name']";
    private final String saveButton = "//button[text()='Save']";
    public TitlePage(Page page) {
        this.page = page;
    }


    public void changeCurrentOwnerToNewOwner(String newOwnerName){
        page.fill(newOwnerNameInputField, newOwnerName);
        page.click(saveButton);
    }
    public boolean isCurrentOwnerNameDisplayed(){
        page.waitForTimeout(3000);
        Locator row = page.locator(titleDetailsTableRow);
        boolean isVisible = false;
        for (int i = 0; i < row.count(); i++){
            isVisible = row.locator(":scope", new Locator.LocatorOptions()
                    .setHasText("Current Owner")).locator("td").isVisible();
        }
        return isVisible;
    }
    public boolean isDescriptionOfTheTitleDisplayed(){
        page.waitForTimeout(3000);
        Locator row = page.locator(titleDetailsTableRow);
        boolean isVisible = false;
        for (int i = 0; i < row.count(); i++){
            isVisible = row.locator(":scope", new Locator.LocatorOptions()
                    .setHasText("Description")).locator("td").isVisible();
        }
        return isVisible;
    }
    public String getSearchResultPageHeader(){
        return page.textContent(searchResultPageHeader);
    }
    private boolean isLoadingTextVisible(){
        return page.isVisible(loadingText);
    }
    public String getCurrentOwnerName(){
        page.waitForTimeout(3000);
        Locator row = page.locator(titleDetailsTableRow);
        String ownerName = null;
        for (int i = 0; i < row.count(); i++){
            ownerName = row.locator(":scope", new Locator.LocatorOptions()
                    .setHasText("Current Owner")).locator("td").innerText();
        }
        return ownerName;
    }
}
