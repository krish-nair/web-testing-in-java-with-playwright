package com.pw;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.List;

public class PlaywrightTest {

    private final Logger LOG = LoggerFactory.getLogger(PlaywrightTest.class);
    @Test
    public void playwrightTest(){
        try(Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.chromium();
            Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            LOG.info("Browser is launched and navigating to google website");
            page.navigate("https://www.google.com");

            String getPageTitle = page.title();
            LOG.info("Page title is '{}'", getPageTitle);
        }
    }

    @Test
    public void browserSupportTest(){
        try(Playwright playwright = Playwright.create()) {
            List<BrowserType> browserTypes = List.of(playwright.chromium(), playwright.firefox(), playwright.webkit());

            for (BrowserType browserType: browserTypes){
                Page page = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false)).newPage();
                page.navigate("https://www.google.com");
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/" + browserType.name() + ".png")));
            }
        }
    }
}
