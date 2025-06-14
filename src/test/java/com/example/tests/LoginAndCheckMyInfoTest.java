package com.example.tests;

import com.example.tests.pages.DashboardPage;
import com.example.tests.pages.LoginPage;
import com.example.tests.pages.MyInfoPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginAndCheckMyInfoTest extends BaseTest {

    @Test
    public void testEmployeeFullName() {
        // 1. Login
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.login(TestData.USERNAME, TestData.PASSWORD);
        assertTrue(dashboardPage.isLoaded(), "Dashboard sa nenačítal");

        // 2. Prejdi na My Info
        MyInfoPage myInfoPage = dashboardPage.goToMyInfo();

        // 3. Získaj meno zo stránky My Info
        String actualName = myInfoPage.getDisplayedFullName();

        // 4. Porovnaj s očakávaným
        assertEquals(TestData.EXPECTED_FULL_NAME, actualName, "Meno zamestnanca nesúhlasí!");
    }
}
