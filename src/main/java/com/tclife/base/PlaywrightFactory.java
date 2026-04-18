package com.tclife.base;

import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class PlaywrightFactory {

    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private Properties prop;

    public static Playwright getPlaywright() {
        return playwright.get();
    }

    public static Browser getBrowser() {
        return browser.get();
    }

    public static BrowserContext getBrowserContext() {
        return browserContext.get();
    }

    public static Page getPage() {
        return page.get();
    }

    /**
     * Initializes the browser based on the properties provided.
     * @param prop Properties object containing browser configuration.
     * @return Page object.
     */
    public Page initBrowser(Properties prop) {
        String browserName = prop.getProperty("browser").trim();
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless").trim());
        String baseUrl = prop.getProperty("baseUrl").trim();

        // Check for CI environment variable
        if (Boolean.parseBoolean(System.getenv("CI"))) {
            System.out.println("CI environment detected. Forcing headless mode.");
            headless = true;
        }

        System.out.println("Initializing browser: " + browserName + " | Headless: " + headless);
        playwright.set(Playwright.create());

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        if (browserName.equalsIgnoreCase("chrome")) {
            launchOptions.setChannel("chrome");
        }
        launchOptions.setHeadless(headless);
        // --start-maximized is not effective in headless mode on Linux
        if (!headless) {
            launchOptions.setArgs(Arrays.asList("--start-maximized"));
        }


        switch (browserName.toLowerCase()) {
            case "chromium":
                browser.set(getPlaywright().chromium().launch(launchOptions));
                break;
            case "firefox":
                browser.set(getPlaywright().firefox().launch(launchOptions));
                break;
            case "safari":
                browser.set(getPlaywright().webkit().launch(launchOptions));
                break;
            case "chrome":
                browser.set(getPlaywright().chromium().launch(launchOptions));
                break;
            default:
                System.out.println("Please pass the right browser name....");
                break;
        }

        browserContext.set(getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(null)));
        page.set(getBrowserContext().newPage());
        getPage().navigate(baseUrl);

        return getPage();
    }

    /**
     * this method is used to initialize the properties from config file
     */
    public Properties init_prop() {
        try {
            FileInputStream ip = new FileInputStream("./src/test/resources/config.properties");
            prop = new Properties();
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }


    public static void closeBrowser() {
        if (getPage() != null) {
            getPage().close();
        }
        if (getBrowserContext() != null) {
            getBrowserContext().close();
        }
        if (getBrowser() != null) {
            getBrowser().close();
        }
        if (getPlaywright() != null) {
            getPlaywright().close();
        }
    }
}
