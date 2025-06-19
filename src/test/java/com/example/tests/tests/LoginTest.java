package com.example.tests.tests;

import com.example.tests.base.BaseTest;
import com.example.tests.data.TestData;
import com.example.tests.pages.DashboardPage;
import org.junit.jupiter.api.*;

public class LoginTest extends BaseTest {

    private DashboardPage dashboardPage;

    @DisplayName("Valid login test")
    @Test
    public void testValidLogin() {
        dashboardPage = loginToApp(TestData.USERNAME, TestData.PASSWORD);
    }
}