package pages;

import base.Base;
import org.openqa.selenium.By;

public class HomePage extends Base {

    By logo = By.xpath("//img[@alt=\"Walmart\"]");

    public Boolean checkLogo(){

        return driver.findElement(logo).isDisplayed();
    }
}
