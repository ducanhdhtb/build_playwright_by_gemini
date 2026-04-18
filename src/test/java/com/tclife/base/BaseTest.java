package com.tclife.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseTest {

    protected PlaywrightFactory pf;
    protected Page page;
    protected Properties prop;

    @BeforeClass
    public void setup() {
        pf = new PlaywrightFactory();
        prop = pf.init_prop();
        page = pf.initBrowser(prop);
    }

    @BeforeMethod
    public void startTracing() {
        // Start tracing before each test method
        PlaywrightFactory.getBrowserContext().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    @AfterMethod
    public void stopTracingAndAttach(ITestResult result) throws IOException {
        String traceFileName = "trace-" + result.getMethod().getMethodName() + ".zip";
        Path tracePath = Paths.get("target/traces/" + traceFileName);

        // Stop tracing and save the trace file
        PlaywrightFactory.getBrowserContext().tracing().stop(new Tracing.StopOptions()
                .setPath(tracePath));

        // Attach the trace file to Allure report
        Allure.addAttachment("Playwright Trace", "application/zip", new ByteArrayInputStream(Files.readAllBytes(tracePath)), ".zip");

        // Optional: Attach screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
        }
    }

    @AfterClass
    public void tearDown() {
        PlaywrightFactory.closeBrowser();
    }
}
