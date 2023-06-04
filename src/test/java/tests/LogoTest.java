package tests;

import base.Base;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class LogoTest extends Base {

    HomePage homePage = new HomePage();

    @Test(priority = 0, description = "Logo Test")
    @Description("Check the presence of logo")
    @Severity(SeverityLevel.NORMAL)
    public void isLogoPresent(){

        Assert.assertTrue(homePage.checkLogo());
    }
}
