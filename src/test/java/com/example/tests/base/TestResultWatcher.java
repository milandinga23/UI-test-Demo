package com.example.tests.base;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestResultWatcher implements AfterTestExecutionCallback {

    private final BaseTest baseTest;

    public TestResultWatcher(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean failed = context.getExecutionException().isPresent();
        if (failed) {
            String testName = context.getDisplayName().replace("()", "");
            baseTest.logger.error("Test '{}' zlyhal. Ukladám screenshot...", testName);
            baseTest.takeScreenshot(testName);
        }
    }
}
