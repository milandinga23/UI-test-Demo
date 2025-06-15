package com.example.tests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//h5[text()='Login']")
    private WebElement heading;

    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        waitForUrlContains("/login");
    }

    public boolean isLoaded() {
        try {
            waitForVisibility(heading);
            logger.info("Login page úspešne načítaná.");
            return true;
        } catch (Exception e) {
            logger.error("Login page sa nepodarilo načítať.", e);
            return false;
        }
    }

    public DashboardPage login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
        return new DashboardPage(driver);
    }

}
