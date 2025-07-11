package com.example.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Function;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForPageLoad();
        initElements();
    }

    protected void initElements() {
        PageFactory.initElements(driver, this);
    }

    protected void waitForVisibility(WebElement element) {
        logger.info("Čakám na zviditeľnenie elementu: {}", element);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Klikám na element: {}", element);
            element.click();
        } catch (Exception e) {
            logger.error("Nepodarilo sa kliknúť na element: {}", element, e);
            throw e;
        }
    }

    protected void clickByXpath(String xpath) {
        try {
            logger.info("Klikám na element s XPath: {}", xpath);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            element.click();
        } catch (Exception e) {
            logger.error("Nepodarilo sa kliknúť na element s XPath: {}", xpath, e);
            throw e;
        }
    }

    protected void setInputValueByJavaScript(WebDriver driver, WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", element, text);
    }

    protected void type(WebElement element, String text) {
        waitForVisibility(element);
        logger.info("Zapisujem text '{}' do elementu: {}", text, element);
        element.clear();
        element.sendKeys(text);
    }

    protected void typeByJavaScript(WebElement element, String text) {
        waitForVisibility(element);
        logger.info("Zapisujem text '{}' do elementu: {} - pomocou JavaScriptu", text, element);
        setInputValueByJavaScript(this.driver, element, text);
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        String text = element.getText().trim();
        logger.info("Prečítaný text z elementu: {}", text);
        return text;
    }

    protected void waitForUrlContains(String partialUrl) {
        logger.info("Čakám, kým URL bude obsahovať: {}", partialUrl);
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    protected void waitForExactUrl(String expectedUrl) {
        logger.info("Čakám na presnú URL: {}", expectedUrl);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    protected boolean isUrlCorrect(String partialUrl) {
        boolean result = driver.getCurrentUrl().contains(partialUrl);
        logger.info("Overenie, či aktuálna URL '{}' obsahuje '{}': {}", driver.getCurrentUrl(), partialUrl, result);
        return result;
    }

    protected void waitForTitleContains(String partialTitle) {
        logger.info("Čakám na titulok obsahujúci: {}", partialTitle);
        wait.until(ExpectedConditions.titleContains(partialTitle));
    }

    protected void waitForPageLoad() {
        logger.info("Čakám na načítanie stránky pomocou JavaScriptu.");

        wait.until(driver -> ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public ExpectedCondition<Boolean> elementHasNonEmptyText(WebElement element) {
        return driver -> {
            try {
                String text = element.getText();
                return !text.trim().isEmpty();
            } catch (Exception e) {
                return false;
            }
        };
    }

    public MyInfoPage goToMyInfo() {
        clickByXpath("//span[text()='My Info']");
        return new MyInfoPage(driver);
    }

    public EmployeeInformationPage goToEmployeeListPage() {
        clickByXpath("//span[text()='PIM']");
        return new EmployeeInformationPage(driver);
    }
}
