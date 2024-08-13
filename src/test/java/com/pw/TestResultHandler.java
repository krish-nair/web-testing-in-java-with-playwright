package com.pw;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestResultHandler implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        Object testInstance = extensionContext.getRequiredTestInstance();
        boolean testFailed = extensionContext.getExecutionException().isPresent();

        if (testFailed){
            Field resultField = null;
            try {
                resultField = testInstance.getClass().getField("page");
            } catch (NoSuchFieldException e){
                e.printStackTrace();
            }
            Page page = null;
            try {
                page = (Page) resultField.get(testInstance);
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }
            // Get the method name
            String methodName = extensionContext.getRequiredTestMethod().getName();

            // Get the current timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            // Set the screenshot path using method name and timestamp
            String screenshotName = methodName + "_" + timestamp + ".png";
            assert page != null;
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/" + screenshotName)));
        }

    }
}
