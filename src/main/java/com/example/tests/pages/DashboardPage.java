package com.example.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(xpath = "//h6[text()='Dashboard']")
    private WebElement dashboardHeading;

    public DashboardPage(WebDriver driver) {
        super(driver);
        waitForUrlContains("/dashboard");
    }

    public boolean isLoaded() {
        try {
            waitForVisibility(dashboardHeading);
            logger.info("Dashboard page successfully loaded.");
            return true;
        } catch (Exception e) {
            logger.error("Dashboard page failed to load.", e);
            return false;
        }
    }

}
