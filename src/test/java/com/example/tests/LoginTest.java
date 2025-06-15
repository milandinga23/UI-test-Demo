package com.example.tests;

import com.example.tests.pages.DashboardPage;
import com.example.tests.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest{

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Test
    public void testValidLogin() {
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoaded(), "Login page sa nenačítala");
        dashboardPage = loginPage.login(TestData.USERNAME, TestData.PASSWORD);
        assertTrue(dashboardPage.isLoaded(), "Dashboard sa nenačítal");
    }
}