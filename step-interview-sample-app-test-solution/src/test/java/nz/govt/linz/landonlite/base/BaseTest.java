package nz.govt.linz.landonlite.base;

import com.microsoft.playwright.Page;
import nz.govt.linz.landonlite.pagefactory.PageFactory;
import nz.govt.linz.landonlite.pages.LandingPage;
import nz.govt.linz.landonlite.pages.TitlePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.Properties;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected PageFactory pageFactory;
    protected Properties prop;
    protected Page page;
    protected LandingPage homePage;
    protected TitlePage titlePage;

    @BeforeAll
    public void setup() {
        pageFactory = new PageFactory();
        prop = pageFactory.initProperties();
        page = pageFactory.launchBrowser(prop);
        homePage = new LandingPage(page);
        titlePage = new TitlePage(page);
    }

    @AfterAll
    public void teardown() {
        if (page != null)
            page.context().browser().close();
    }
}
