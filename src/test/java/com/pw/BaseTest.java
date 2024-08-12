package com.pw;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@ExtendWith(TestResultHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    private Playwright playwright;
    private BrowserType browserType;
    private BrowserContext browserContext;
    private Browser browser;
    public Page page;
    protected Properties prop;
    protected HomePage homePage;
    protected AddNewComputerPage addNewComputerPage;
    protected Logger LOG;

    @BeforeAll
    public void launchBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(3000));
    }

    @BeforeEach
    public void setup(){
        browserContext = browser.newContext();
        page = browserContext.newPage();
        prop = readProperties();
        LOG = LoggerFactory.getLogger(BaseTest.class);
        LOG.info("Browser is launched...");
        page.setViewportSize(1920, 1080);
        page.navigate(prop.getProperty("url"), new Page.NavigateOptions().setWaitUntil(WaitUntilState.LOAD));

        homePage = new HomePage(page);
        addNewComputerPage = new AddNewComputerPage(page);

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
}
