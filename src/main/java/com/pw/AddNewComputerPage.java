package com.pw;

import com.microsoft.playwright.Keyboard;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AddNewComputerPage {
    private final Page page;
    private final Locator companyDropdown;
    private final Locator computerName;
    private final Keyboard kb;
    private final Locator input;
    private final Locator heading;

    public AddNewComputerPage(Page page) {
        this.page = page;
        this.companyDropdown = page.locator("#company");
        this.computerName = page.locator("#name");
        this.input = page.locator("//div/input");
        this.kb = page.keyboard();
        this.heading = page.locator("text = Add a computer");
    }

    public String getHeaderText(){
        return heading.innerText();
    }
    public void enterValues(){
        input.count();
        System.out.println(input.count());
        input.first().fill("testComputer");
        input.nth(2).fill("01 May 2010");
    }
    public void setComputerNameField(String compName){
        computerName.fill(compName);
    }

    public void selectCompany(){
        companyDropdown.focus();
        kb.press("ArrowDown");
        kb.press("ArrowDown");
        kb.press("ArrowDown");
        kb.press("ArrowDown");
        kb.press("ArrowDown");
//        companyDropdown.selectOption("RCA");
    }

    public String getPageHeader(){
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Add a computer")).innerText();
    }
}
