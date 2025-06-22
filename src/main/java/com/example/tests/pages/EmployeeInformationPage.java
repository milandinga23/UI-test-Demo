package com.example.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeInformationPage extends BasePage {

    @FindBy(xpath = "//h5[text()='Employee Information']")
    private WebElement heading;

    @FindBy(xpath = "//div[@class='oxd-table-body']/div[contains(@class,'oxd-table-card')]")
    private List<WebElement> employeeRows;

    @FindBy(xpath = "//i[@class='oxd-icon bi-chevron-right']")
    private WebElement nextPageButton;

    @FindBy(xpath = "//button[@class='oxd-button oxd-button--medium oxd-button--secondary']")
    private WebElement addNewEmployeeButton;

    public EmployeeInformationPage(WebDriver driver) {
        super(driver);
        waitForUrlContains("/pim/viewEmployeeList");
    }

    public List<String> getEmployeeNamesFromPage() {
        By employeeRowsLocator = By.xpath("//div[@class='oxd-table-body']/div[contains(@class,'oxd-table-card')]");

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(employeeRowsLocator, 0));

        // employeeRows je už aktualizovaný po waite
        return employeeRows.stream()
                .map(row -> row.findElement(By.xpath(".//div[@role='cell'][2]")).getText().trim())
                .collect(Collectors.toList());
    }

    public boolean isNextPageEnabled() throws InterruptedException {
        waitForPageToLoad();
        Thread.sleep(3000);
        List<WebElement> nextButtons = driver.findElements(By.xpath("//i[@class='oxd-icon bi-chevron-right']"));
        if (nextButtons.isEmpty()) {
            logger.info("Tlačidlo 'Next Page' nie je dostupné.");
            return false;
        }

        WebElement next = nextButtons.get(0);
        boolean enabled = next.isEnabled();
        logger.info("Tlačidlo 'Next Page' nájdené. Enabled: {}", enabled);
        return enabled;
    }

    public void goToNextPage() {
        logger.info("Preklikávam na ďalšiu stránku");
        click(nextPageButton);
        waitForPageToLoad();
    }

    private void waitForPageToLoad() {
        waitForVisibility(heading);
    }

    public boolean searchEmployeeByEmployeeIdAcrossPages(String employeeId) throws InterruptedException {
        do {
            List<String> employeeIds = getEmployeeNamesFromPage();
            logger.info("Hľadaný employee Id: {}", employeeId);
            logger.info("Načítané employee Id zo stránky: {}", employeeIds);
            if (employeeIds.stream().anyMatch(employee -> employee.contains(employeeId))) {
                return true;
            }
            if (isNextPageEnabled()) {
                goToNextPage();
            } else {
                break;
            }
        } while (true);
        return false;
    }

    public AddEmployeePage goToAddNewEmployee() {
        addNewEmployeeButton.click();
        return new AddEmployeePage(driver);
    }

    public boolean isLoaded() {
        try {
            waitForVisibility(heading);
            logger.info("PIM page úspešne načítaná.");
            return true;
        } catch (Exception e) {
            logger.error("PIM page sa nepodarilo načítať.", e);
            return false;
        }
    }
}

