package com.example.tests.tests;

import com.example.tests.base.BaseTest;
import com.example.tests.data.TestData;
import com.example.tests.pages.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.tests.data.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddAndVerifyEmployeeTest extends BaseTest {

    private DashboardPage dashboardPage;
    private EmployeeInformationPage employeeListPage;
    private AddEmployeePage addEmployeePage;
    private MyInfoPage myInfoPage;

    @DisplayName("Add and Find Employee")
    @Test
    public void testAddAndFindEmployee() throws InterruptedException {
        String firstName = randomFirstName();
        String lastName = randomLastName();
        String employeeId = randomEmployeeId();
        String fullName = firstName + " " + lastName;

        dashboardPage = loginToApp(TestData.USERNAME, TestData.PASSWORD);
        myInfoPage = addNewEmployee(employeeId, firstName, lastName);
        employeeListPage = verifyNewEmployee(employeeId, fullName);
    }

    @Step("Pridaj employee")
    public MyInfoPage addNewEmployee(String employeeId, String firstName, String lastName) {
        employeeListPage = dashboardPage.goToEmployeeListPage();
        assertTrue(employeeListPage.isLoaded(), "Employee List sa nenačítal");
        addEmployeePage = employeeListPage.goToAddNewEmployee();
        assertTrue(addEmployeePage.isLoaded(), "Add New Employee page sa nenačítala");

        myInfoPage = addEmployeePage.addEmployee(firstName, lastName, employeeId);
        assertTrue(myInfoPage.isLoaded(), "Personal Details sa nenačítal");
        return myInfoPage;
    }

    @Step("Verifikuj pridaného zamestnanca")
    public EmployeeInformationPage verifyNewEmployee(String employeeId, String fullName) throws InterruptedException {
        employeeListPage = dashboardPage.goToEmployeeListPage();
        assertTrue(employeeListPage.isLoaded(), "Employee List sa nenačítal");
        boolean found = employeeListPage.searchEmployeeByEmployeeIdAcrossPages(employeeId);
        assertTrue(found, "Zamestnanec: " + fullName + " s Id:" + employeeId + " nebol nájdený v zozname!");
        return employeeListPage;
    }
}
