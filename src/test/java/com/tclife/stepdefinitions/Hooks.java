package com.tclife.stepdefinitions;

import com.tclife.base.PlaywrightFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private TestContext testContext;

    public Hooks(TestContext context) {
        this.testContext = context;
    }

    @Before
    public void setup() {
        testContext.pf = new PlaywrightFactory();
        testContext.prop = testContext.pf.init_prop();
        testContext.page = testContext.pf.initBrowser(testContext.prop);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Attach screenshot on failure
            byte[] screenshot = testContext.page.screenshot();
            scenario.attach(screenshot, "image/png", "screenshot");
        }
        PlaywrightFactory.closeBrowser();
    }
}
