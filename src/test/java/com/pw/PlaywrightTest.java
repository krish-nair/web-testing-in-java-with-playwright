package com.pw;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.Assertions;
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

public class PlaywrightTest extends BaseTest{

    @Test
    public void playwrightTest(){
        page.click("//button[text()='Start']");
        Locator loader = page.locator("#loading");

        loader.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        Assertions.assertEquals(page.locator("text = Hello World!").innerText(), "Hello World");
        //page.isVisible("#loading", new Page.IsVisibleOptions());
            /*String path = System.getProperty("user.dir");
            page.setInputFiles("#file-upload", Paths.get(path + File.separator + "pom.xml"));
            page.click("#file-submit");
            page.waitForTimeout(3000);
            String text = page.innerText("text = File Uploaded!");
            Assertions.assertEquals(text, "File Uploaded!");*/

            /*Download download = page.waitForDownload(() -> {
                Locator test2File = page.locator("text = sample.pdf");
                test2File.first().click();
//                page.getByText("Test2.pdf").click();
            });
            download.saveAs(Paths.get("download/", download.suggestedFilename()));*/

//            String text = page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Computer name")).innerText();
//
//            String getPageTitle = page.title();
//            LOG.info("Table row '{}'", text);
    }

    @Test
    public void downloadTest(){
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

    @Test
    public void sauceLabsTest(){

    }
}
