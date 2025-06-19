package com.example.tests.base;

import com.example.tests.data.TestData;
import com.example.tests.pages.DashboardPage;
import com.example.tests.pages.LoginPage;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {

    protected WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void setUp() {
        logger.info("Spúšťam prehliadač a otváram testovanú stránku...");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(TestData.TEST_URL);
        logger.info("Načítaná stránka: {}", driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        logger.info("Uzatváram prehliadač...");
        if (driver != null) {
            driver.quit();
        }
        logger.info("Prehliadač ukončený.");
    }

    // Registruj TestWatcher pre každý test
    @RegisterExtension
    TestResultWatcher watcher = new TestResultWatcher(this);

    protected void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public void takeScreenshot(String testName) {
        if (driver == null) return;

        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotDir = "screenshots";
            Files.createDirectories(Paths.get(screenshotDir));
            String path = String.format("%s/%s_%s.png", screenshotDir, testName, timestamp);
            Files.copy(screenshot.toPath(), Paths.get(path));
            logger.error("Screenshot uložený: {}", path);
        } catch (IOException e) {
            logger.error("Nepodarilo sa uložiť screenshot: {}", e.getMessage());
        }
    }

    @Step("Login")
    public DashboardPage loginToApp(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoaded(), "Login page sa nenačítala");
        DashboardPage dashboardPage = loginPage.login(username, password);
        assertTrue(dashboardPage.isLoaded(), "Dashboard sa nenačítal");
        return dashboardPage;
    }
}
