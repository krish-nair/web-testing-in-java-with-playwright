package nz.govt.linz.landonlite.tests;

import nz.govt.linz.landonlite.base.BaseTest;
import nz.govt.linz.landonlite.constants.AppConstants;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LandingPageTest extends BaseTest {
    @Test
    public void verifyHeaderTextAndUserInfoMessage(){
        String actualHeaderText = homePage.getHeaderText();
        String actualUserHelpText = homePage.getUserHelpText();

        assertThat(actualHeaderText).isEqualTo(AppConstants.HEADER_LANDING_PAGE);
        assertThat(actualUserHelpText).isEqualTo(AppConstants.USERINFO_TEXT_LANDING_PAGE);
    }

    @Test
    public void shouldDisplayTitleNumberTitleDescriptionAndCurrentOwnerWithValidTitleNumber(){

        titlePage = homePage.searchTitleNumber(prop.getProperty("validTitleNumber"));
        String actualSearchResultPageHeader = titlePage.getSearchResultPageHeader();

        assertThat(actualSearchResultPageHeader)
                .isEqualTo(AppConstants.HEADER_SEARCHRESULT_PAGE + prop.getProperty("validTitleNumber"));
        System.out.println("3");

        boolean isTitleDescriptionDisplayed = titlePage.isDescriptionOfTheTitleDisplayed();
        boolean isCurrentOwnerNameDisplayed = titlePage.isCurrentOwnerNameDisplayed();

        assertThat(isTitleDescriptionDisplayed).isTrue();
        assertThat(isCurrentOwnerNameDisplayed).isTrue();
    }
}
