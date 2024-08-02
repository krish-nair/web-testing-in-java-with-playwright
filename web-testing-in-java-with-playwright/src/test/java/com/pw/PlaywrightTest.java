package com.pw;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class PlaywrightTest {
    private Properties prop;
    private final Logger LOG = LoggerFactory.getLogger(PlaywrightTest.class);
    @Test
    public void playwrightTest(){
        prop = readProperties();
        try(Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.chromium();
            Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(3000));
            Page page = browser.newPage();
            LOG.info("Browser is launched...");
            page.navigate(prop.getProperty("url"));
            page.click("text = Add a new computer");

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

    private Properties readProperties(){
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config/config.properties");
            prop = new Properties();
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
        return prop;
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "text = Add a new computer",
            "text = Add a New Computer",
            "text = add a new computer"
    })
    public void playwrightTestWithParameters(String textSelector){
        prop = readProperties();
        try(Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.chromium();
            Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(3000));
            Page page = browser.newPage();
            LOG.info("Browser is launched...");
            page.navigate(prop.getProperty("url"));
            page.click(textSelector);

            String getPageTitle = page.title();
            LOG.info("Page title is '{}'", getPageTitle);
        }
    }
}
