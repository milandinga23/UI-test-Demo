package com.example.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

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

    public DashboardPage login(String username, String password) {
        waitForPageLoad(); // over, že stránka je ready
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
        return new DashboardPage(driver);
    }

}
