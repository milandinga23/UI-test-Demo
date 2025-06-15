package com.example.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddEmployeePage extends BasePage {

    @FindBy(xpath = "//h6[text()='Add Employee']")
    private WebElement heading;

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(xpath = "//div[@class='orangehrm-card-container']//input[@class='oxd-input oxd-input--active']")
    private WebElement employeeIdInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    public AddEmployeePage(WebDriver driver) {
        super(driver);
        waitForUrlContains("/pim/addEmployee");
    }

    public boolean isLoaded() {
        try {
            waitForVisibility(heading);
            logger.info("Add employee page úspešne načítaná.");
            return true;
        } catch (Exception e) {
            logger.error("Add employee page sa nepodarilo načítať.", e);
            return false;
        }
    }

    public MyInfoPage addEmployee(String firstName, String lastName, String employeeId) {
        logger.info("Zadávam nového zamestnanca: {} {}", firstName, lastName);
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(employeeIdInput, employeeId);
        click(saveButton);
        logger.info("Klikol som na Uložiť");
        return new MyInfoPage(driver);
    }
}
