package com.pw;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.File;
import java.nio.file.Paths;

public class TheHerokuHomePage {
    Page page;
    private Locator example1Link;
    private final Locator downloadLink;

    public TheHerokuHomePage(Page page) {
        this.page = page;
//        this.example1Link = page.getAttribute("a",)
        this.downloadLink = page.locator("text = sample-zip-file.zip");
    }

    public void setDownload(){
        page.onDownload(d -> {
            System.out.println(d.path());
            d.saveAs(Paths.get("downloads/", d.suggestedFilename()));
        });
        downloadLink.click();
    }
}
