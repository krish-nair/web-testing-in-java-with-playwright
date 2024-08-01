package nz.govt.linz.landonlite.pages;

import com.microsoft.playwright.Page;

public class LandingPage {
    private Page page;
    private final String headerText = "text = Welcome to LandOnLite";
    private final String userHelpText = "//p[contains(., 'You can enter a title number')]";
    private final String searchInputField = "//input[@placeholder='Enter a title number']";
    private final String searchButton = "//button[text()='Go']";

    public LandingPage(Page page) {
        this.page = page;
    }

    public TitlePage searchTitleNumber(String titleNumber){
        page.fill(searchInputField, titleNumber);
        page.click(searchButton);
        return new TitlePage(page);
    }

    public String getUserHelpText(){
        return page.innerText(userHelpText);
    }

    public String getHeaderText(){
        return page.textContent(headerText);
    }

    public String getPageTitle(){
        return page.title();
    }
}
