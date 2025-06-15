package com.example.tests;

import com.example.tests.pages.DashboardPage;
import com.example.tests.pages.LoginPage;
import com.example.tests.pages.MyInfoPage;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginAndCheckMyInfoTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyInfoPage myInfoPage;
    String actualName;

    @DisplayName("Test Employee Full Name")
    @Test
    public void testEmployeeFullName() {
        loginToApp(TestData.USERNAME, TestData.PASSWORD);
        goToMyInfoPage();
        getNameFromMyInfoPage();
        assertName(TestData.EXPECTED_FULL_NAME);
    }

    @Step("Login")
    public void loginToApp(String username, String password) {
        loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoaded(), "Login page sa nenačítala");
        dashboardPage = loginPage.login(username, password);
        assertTrue(dashboardPage.isLoaded(), "Dashboard sa nenačítal");
    }

    @Step("Prejdi na My Info stránku")
    public void goToMyInfoPage() {
        myInfoPage = dashboardPage.goToMyInfo();
        assertTrue(myInfoPage.isLoaded(), "My Info page sa nenačítala");
    }

    @Step("Získaj meno zo stránky My Info")
    public void getNameFromMyInfoPage() {
        actualName = myInfoPage.getDisplayedFullName();
    }

    @Step("Porovnaj s očakávaným")
    public void assertName(String expectedName) {
        assertEquals(expectedName, actualName, "Meno zamestnanca nesúhlasí!");
    }
}
