package com.pw;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.nio.file.Paths;

public class TestResultHandler implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        Object testInstance = extensionContext.getRequiredTestInstance();
        Boolean testFailed = extensionContext.getExecutionException().isPresent();

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
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/failure.png")));
        }

    }
}
