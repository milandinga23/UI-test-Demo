package com.example.tests;

import com.example.tests.pages.DashboardPage;
import com.example.tests.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest{

    @Test
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.login(TestData.USERNAME, TestData.PASSWORD);
        assertTrue(dashboardPage.isLoaded(), "Dashboard sa nenačítal");
    }
}