package com.example.tests;

import com.example.tests.pages.DashboardPage;
import com.example.tests.pages.LoginPage;
import com.example.tests.pages.MyInfoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginAndCheckMyInfoTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyInfoPage myInfoPage;

    @DisplayName("Test Employee Full Name")
    @Test
    public void testEmployeeFullName() {
        // 1. Login
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoaded(), "Login page sa nenačítala");
        dashboardPage = loginPage.login(TestData.USERNAME, TestData.PASSWORD);
        assertTrue(dashboardPage.isLoaded(), "Dashboard sa nenačítal");

        // 2. Prejdi na My Info
        myInfoPage = dashboardPage.goToMyInfo();
        assertTrue(myInfoPage.isLoaded(), "My Info page sa nenačítala");


        // 3. Získaj meno zo stránky My Info
        String actualName = myInfoPage.getDisplayedFullName();

        // 4. Porovnaj s očakávaným
        assertEquals(TestData.EXPECTED_FULL_NAME, actualName, "Meno zamestnanca nesúhlasí!");
    }
}
