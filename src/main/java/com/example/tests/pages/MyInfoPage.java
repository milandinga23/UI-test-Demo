package com.example.tests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyInfoPage extends BasePage {

    @FindBy(xpath = "//div[@class='orangehrm-edit-employee-name']//h6")
    private WebElement fullNameHeader;

    @FindBy(xpath = "//h6[text()='Personal Details']")
    private WebElement heading;

    public MyInfoPage(WebDriver driver) {
        super(driver);
        waitForUrlContains("/viewPersonalDetails");
    }

    public boolean isLoaded() {
        try {
            waitForVisibility(heading);
            logger.info("PIM page úspešne načítaná.");
            return true;
        } catch (Exception e) {
            logger.error("PIM page sa nepodarilo načítať.", e);
            return false;
        }
    }

    @Step("Získaj meno zo stránky My Info")
    public String getDisplayedFullName() {
        wait.until(elementHasNonEmptyText(fullNameHeader));
        return getText(fullNameHeader);
    }
}
