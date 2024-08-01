package nz.govt.linz.landonlite.tests;

import nz.govt.linz.landonlite.base.BaseTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TitlePageTest extends BaseTest {
    @Test
    public void shouldChangeCurrentOwnerName(){
        titlePage = homePage.searchTitleNumber(prop.getProperty("validTitleNumber"));
        titlePage.changeCurrentOwnerToNewOwner(prop.getProperty("newOwnerName"));

        String actualOwnerName = titlePage.getCurrentOwnerName();
        assertThat(actualOwnerName).isEqualTo(prop.getProperty("newOwnerName"));
    }
}
