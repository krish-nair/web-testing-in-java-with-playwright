//package com.pw;
//
//import com.microsoft.playwright.*;
//import com.microsoft.playwright.options.WaitUntilState;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Properties;
//
//@ExtendWith(TestResultHandler.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class BaseTest {
//    private Playwright playwright;
//    private BrowserType browserType;
//    private BrowserContext browserContext;
//    private Browser browser;
//    public Page page;
//    protected Properties prop;
//    protected HomePage homePage;
//    protected AddNewComputerPage addNewComputerPage;
//    protected SauceLabsHomePage sauceLabsHomePage;
//    protected Logger LOG;
//
//    @BeforeAll
//    public void launchBrowser(){
//        playwright = Playwright.create();
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//        browserContext = browser.newContext();
//        page = browserContext.newPage();
//        prop = readProperties();
//        LOG = LoggerFactory.getLogger(BaseTest.class);
//        LOG.info("Browser is launched...");
//        page.navigate(prop.getProperty("url4"), new Page.NavigateOptions().setWaitUntil(WaitUntilState.LOAD));
//
//        homePage = new HomePage(page);
//        addNewComputerPage = new AddNewComputerPage(page);
//        sauceLabsHomePage = new SauceLabsHomePage(page);
//        sauceLabsHomePage.login("standard_user", "secret_sauce");
//        Assertions.assertEquals(page.url(), "https://www.saucedemo.com/inventory.html");
//
//        browserContext.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));
//        browserContext.close();
//    }
//
//    @BeforeEach
//    public void setup(){
//        browserContext = browser.newContext(
//                new Browser.NewContextOptions().setStorageStatePath(Paths.get("state.json")));
//        page = browserContext.newPage();
//        page.setViewportSize(1920, 1080);
//    }
//
//    @AfterEach
//    public void closeContext(){
//        browserContext.close();
//    }
//    @AfterAll
//    public void tearDown(){
//        browser.close();
//        playwright.close();
//    }
//    private Properties readProperties(){
//        try {
//            FileInputStream fis = new FileInputStream("src/main/resources/config/config.properties");
//            prop = new Properties();
//            prop.load(fis);
//        } catch (IOException e) {
//            throw new RuntimeException("File not found", e);
//        }
//        return prop;
//    }
//}

package com.pw;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

@ExtendWith(TestResultHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    private Playwright playwright;
    private BrowserType browserType;
    private BrowserContext browserContext;
    private Browser browser;
    protected Page page; // Changed from public to protected for encapsulation
    protected Properties prop;
    protected HomePage homePage;
    protected AddNewComputerPage addNewComputerPage;
    protected SauceLabsHomePage sauceLabsHomePage;
    protected Logger LOG = LoggerFactory.getLogger(BaseTest.class); // Initialize LOG here

    @BeforeAll
    public void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browserContext = browser.newContext();
        page = browserContext.newPage();
        prop = readProperties();

        LOG.info("Browser is launched...");
        page.navigate(prop.getProperty("url4"), new Page.NavigateOptions().setWaitUntil(WaitUntilState.LOAD));

        // Initialize Page Objects here, not in @BeforeEach
        homePage = new HomePage(page);
        addNewComputerPage = new AddNewComputerPage(page);
        sauceLabsHomePage = new SauceLabsHomePage(page);

        // Perform login
        sauceLabsHomePage.login("standard_user", "secret_sauce");
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", page.url(), "Login failed, URL mismatch");

        // Save browser state after successful login
        browserContext.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));
        browserContext.close();
    }

    @BeforeEach
    public void setup() {
        browserContext = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get("state.json")));
        page = browserContext.newPage();
        page.setViewportSize(1920, 1080);

        // Initialize Page Objects again for each test
        homePage = new HomePage(page);
        addNewComputerPage = new AddNewComputerPage(page);
        sauceLabsHomePage = new SauceLabsHomePage(page);
    }

    @AfterEach
    public void closeContext() {
        if (browserContext != null) {
            browserContext.close();
        }
    }

    @AfterAll
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    private Properties readProperties() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config/config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
        return prop;
    }
}

