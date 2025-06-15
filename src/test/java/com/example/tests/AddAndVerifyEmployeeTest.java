package com.example.tests;

import com.example.tests.pages.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddAndVerifyEmployeeTest extends BaseTest{

    private DashboardPage dashboardPage;
    private EmployeeInformationPage employeeListPage;
    private AddEmployeePage addEmployeePage;
    private MyInfoPage myInfoPage;

    @Test
    public void testAddAndFindEmployee() throws InterruptedException {
        String firstName = "John";
        String lastName = "Test";
        String fullName = firstName + " " + lastName;
        String employeeId = String.valueOf(new Random().nextInt(900000) + 100000);

        // Login
        LoginPage loginPage = new LoginPage(driver);
        dashboardPage = loginPage.login(TestData.USERNAME, TestData.PASSWORD);

        // Add employee
        employeeListPage = dashboardPage.goToEmployeeListPage();
        assertTrue(employeeListPage.isLoaded(), "Employee List sa nenačítal");
        addEmployeePage = employeeListPage.goToAddNewEmployee();
        assertTrue(addEmployeePage.isLoaded(), "Add New Employee page sa nenačítala");

        myInfoPage = addEmployeePage.addEmployee(firstName, lastName, employeeId);
        assertTrue(myInfoPage.isLoaded(), "Personal Details sa nenačítal");


        // Verify in employee list
        employeeListPage = dashboardPage.goToEmployeeListPage();
        assertTrue(employeeListPage.isLoaded(), "Employee List sa nenačítal");

        boolean found = employeeListPage.searchEmployeeByEmployeeIdAcrossPages(employeeId);
        assertTrue(found, "Zamestnanec: " + fullName + " s Id:" + employeeId + " nebol nájdený v zozname!");
    }

}
