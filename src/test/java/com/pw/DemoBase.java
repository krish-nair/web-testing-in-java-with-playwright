package com.pw;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DemoBase {
    private Playwright playwright;
    private BrowserType browserType;
    private BrowserContext browserContext;
    private Browser browser;
    private Page page;
    protected Properties prop;
    protected TheHerokuHomePage theHerokuHomePage;
    protected AddNewComputerPage addNewComputerPage;
    protected Logger LOG;

    @BeforeAll
    public void launchBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(3000));
    }

    @BeforeEach
    public void setup(){
        browserContext = browser.newContext(new Browser.NewContextOptions().setAcceptDownloads(true));
        page = browserContext.newPage();
        LOG = LoggerFactory.getLogger(BaseTest.class);
        LOG.info("Browser is launched...");
        page.setViewportSize(1920, 1080);
        page.navigate("https://the-internet.herokuapp.com/download", new Page.NavigateOptions().setWaitUntil(WaitUntilState.LOAD));
        theHerokuHomePage = new TheHerokuHomePage(page);

    }



    @AfterEach
    public void closeContext(){
        browserContext.close();
    }
    @AfterAll
    public void tearDown(){
        browser.close();
        playwright.close();
    }
}
