package com.example.tests;

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

public class BaseTest {

    protected WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void setUp() {
        logger.info("Spúšťam prehliadač a otváram testovanú stránku...");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
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

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
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
}
