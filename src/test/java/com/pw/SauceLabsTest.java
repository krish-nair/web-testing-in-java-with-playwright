package com.pw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SauceLabsTest extends BaseTest {
    @Test
    public void verifyLoginTest(){
        page.navigate("https://www.saucedemo.com/inventory.html");
        String heading = sauceLabsHomePage.getPaeHeading();
        Assertions.assertEquals(heading, "Products");
    }

    @Test
    public void addToCart(){
        page.navigate("https://www.saucedemo.com/inventory.html");
        sauceLabsHomePage.setAddToCartButton();

        Assertions.assertEquals(sauceLabsHomePage.getAddToCartButtonText(), "Remove");
    }
}
