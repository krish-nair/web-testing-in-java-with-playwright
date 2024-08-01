package nz.govt.linz.landonlite.pagefactory;

import com.microsoft.playwright.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PageFactory {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;
    private Properties prop;
    /**
     * Launches the browser based on the specified properties and sets the session state.
     * @param prop Properties object containing browser configuration.
     * @return Page object for further interactions.
     */
    public Page launchBrowser(Properties prop) {
        this.prop = prop;
        String browserName = prop.getProperty("browser").trim();

        playwright = Playwright.create();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        browser = createBrowser(browserName, Boolean.parseBoolean(prop.getProperty("headless")));
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = browserContext.newPage();
        page.navigate(prop.getProperty("test"));
        return page;
    }

    /**
     * Creates a browser instance based on the browser name and headless mode.
     * @param browserName Name of the browser to launch.
     * @param headless Whether to run the browser in headless mode.
     * @return Browser instance.
     */
    private Browser createBrowser(String browserName, boolean headless){
        switch (browserName.toLowerCase()) {
            case "chromium":
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "firefox":
                return playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "safari":
                return playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "chrome":
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
    }

    /**
     * Initializes properties from the configuration file.
     * @return Properties object.
     */
    public Properties initProperties () {
        prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config/config.properties")) {
            prop.load(fis);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Configuration file not found", e);
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration file", e);
        }
        return prop;
    }
}
