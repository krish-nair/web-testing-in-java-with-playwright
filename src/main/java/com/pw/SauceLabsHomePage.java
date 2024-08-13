package com.pw;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SauceLabsHomePage {
    private Page page;
    private final Locator username;
    private final Locator password;
    private final Locator loginButton;
    private final Locator pageHeading;
    private final Locator addToCartButton;
    private final Locator removeButton;

    public SauceLabsHomePage(Page page){
        this.page = page;
        this.username = page.getByPlaceholder("Username");
        this.password = page.getByPlaceholder("Password");
        this.loginButton = page.locator("#login-button");
        this.pageHeading = page.getByText("Products");
        this.addToCartButton = page.locator("[data-test='add-to-cart-sauce-labs-backpack']");
        this.removeButton = page.locator("#remove-sauce-labs-backpack");
    }

    public String getAddToCartButtonText(){
        return removeButton.innerText();
    }

    public void setAddToCartButton(){
        addToCartButton.click();
    }

    public String getPaeHeading(){
        return pageHeading.innerText();
    }
    public void login(String uname, String pwd){
        username.fill(uname);
        password.fill(pwd);
        loginButton.click();
    }
}
