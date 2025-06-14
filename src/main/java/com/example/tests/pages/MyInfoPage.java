package com.example.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyInfoPage extends BasePage {

    @FindBy(xpath = "//div[@class='orangehrm-edit-employee-name']//h6")
    private WebElement fullNameHeader;

    public MyInfoPage(WebDriver driver) {
        super(driver);
        waitForUrlContains("/viewPersonalDetails");
    }

    public String getDisplayedFullName() {
        return getText(fullNameHeader);
    }
}
