package com.example.tests.tests;

import com.example.tests.base.BaseTest;
import com.example.tests.dao.EmployeeDao;
import com.example.tests.data.TestData;
import com.example.tests.dto.Employee;
import com.example.tests.pages.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.tests.data.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;

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
        checkNewEmployeeInDB(firstName, lastName, employeeId);
    }

    @Step("Pridaj zamestnanca")
    public MyInfoPage addNewEmployee(String employeeId, String firstName, String lastName) {
        employeeListPage = dashboardPage.goToEmployeeListPage();
        assertTrue(employeeListPage.isLoaded(), "Employee List sa nenačítal");
        addEmployeePage = employeeListPage.goToAddNewEmployee();
        assertTrue(addEmployeePage.isLoaded(), "Add New Employee page sa nenačítala");

        myInfoPage = addEmployeePage.addEmployee(firstName, lastName, employeeId);
        assertTrue(myInfoPage.isLoaded(), "Personal Details sa nenačítal");
        return myInfoPage;
    }

    @Step("Verifikuj pridaného zamestnanca na FE")
    public EmployeeInformationPage verifyNewEmployee(String employeeId, String fullName) throws InterruptedException {
        employeeListPage = dashboardPage.goToEmployeeListPage();
        assertTrue(employeeListPage.isLoaded(), "Employee List sa nenačítal");
        boolean found = employeeListPage.searchEmployeeByEmployeeIdAcrossPages(employeeId);
        assertTrue(found, "Zamestnanec: " + fullName + " s Id:" + employeeId + " nebol nájdený v zozname!");
        return employeeListPage;
    }

    @Step("Verifikuj pridaného zamestnanca na DB")
    public void checkNewEmployeeInDB(String firstName, String lastName, String employeeId) {
        logger.info("Overujem pridaného zamestnanca na DB");
        EmployeeDao employeeDao = new EmployeeDao();
        Employee resultFromDB = employeeDao.getEmployeeNameById(employeeId);

        assertEquals(firstName, resultFromDB.getFirstName(), "Prvé meno nesúhlasí");
        assertEquals(lastName, resultFromDB.getLastName(), "Priezvisko nesúhlasí");
    }
}
