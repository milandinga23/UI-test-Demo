package com.example.tests.tests;

import com.example.tests.base.BaseTest;
import com.example.tests.data.TestData;
import com.example.tests.pages.DashboardPage;
import com.example.tests.pages.MyInfoPage;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginAndCheckMyInfoTest extends BaseTest {

    private DashboardPage dashboardPage;
    private MyInfoPage myInfoPage;
    String actualName;

    @DisplayName("Test Employee Full Name")
    @Test
    public void testEmployeeFullName() {
        dashboardPage = loginToApp(TestData.USERNAME, TestData.PASSWORD);
        myInfoPage = goToMyInfoPage();
        actualName = myInfoPage.getDisplayedFullName();
        assertName(TestData.EXPECTED_FULL_NAME);
    }


    @Step("Prejdi na My Info stránku")
    public MyInfoPage goToMyInfoPage() {
        myInfoPage = dashboardPage.goToMyInfo();
        assertTrue(myInfoPage.isLoaded(), "My Info page sa nenačítala");
        return myInfoPage;
    }

    @Step("Porovnaj s očakávaným")
    public void assertName(String expectedName) {
        assertEquals(expectedName, actualName, "Meno zamestnanca nesúhlasí!");
    }
}
