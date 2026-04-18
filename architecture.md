# Playwright Test Automation Framework Architecture

## 1. Overview

This document outlines the architecture of the test automation framework built for the `tclife` project. The framework is designed to be robust, maintainable, and scalable for testing modern web applications. It uses a combination of industry-standard tools and design patterns to achieve this.

## 2. Core Technologies

- **Programming Language:** Java 11
- **Build & Dependency Management:** Apache Maven
- **Browser Automation:** Playwright for Java
- **Test Runner:** TestNG
- **Reporting:** Allure Framework

## 3. Project Structure

The framework follows a standard Maven project structure, organized logically to separate concerns.

```
tclife/
├── pom.xml
├── architecture.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/tclife/
│   │           ├── base/
│   │           │   └── PlaywrightFactory.java
│   │           └── pages/
│   │               ├── InventoryPage.java
│   │               └── LoginPage.java
│   └── test/
│       ├── java/
│       │   └── com/tclife/
│       │       ├── base/
│       │       │   └── BaseTest.java
│       │       └── tests/
│       │           └── LoginPageTest.java
│       └── resources/
│           ├── config.properties
│           └── testng.xml
└── target/
    ├── allure-results/
    └── traces/
```

## 4. Architectural Components

### 4.1. Configuration (`config.properties`)

- **Location:** `src/test/resources/config.properties`
- **Purpose:** To externalize test configuration. This allows us to change key parameters like the target browser, base URL, and headless mode without modifying the source code.

### 4.2. Factory Layer (`PlaywrightFactory.java`)

- **Location:** `src/main/java/com/tclife/base/PlaywrightFactory.java`
- **Purpose:** This is a centralized factory responsible for creating and managing Playwright objects (`Playwright`, `Browser`, `BrowserContext`, `Page`).
- **Key Features:**
    - It uses `ThreadLocal` to store Playwright objects, ensuring thread safety and enabling parallel test execution.
    - It reads the `config.properties` file to initialize the browser with the specified settings.

### 4.3. Base Layer (`BaseTest.java`)

- **Location:** `src/test/java/com/tclife/base/BaseTest.java`
- **Purpose:** Acts as the parent class for all test classes. It handles the common setup and teardown logic for tests.
- **Key Features:**
    - **`@BeforeClass`:** Initializes the `PlaywrightFactory` and starts the browser once per test class.
    - **`@BeforeMethod`:** Starts Playwright's Tracing feature before each test case runs.
    - **`@AfterMethod`:** Stops the tracing, saves the trace file, and attaches both the trace file and a screenshot (on failure) to the Allure report.
    - **`@AfterClass`:** Closes the browser after all tests in the class have finished.

### 4.4. Page Object Model (POM) (`/pages` package)

- **Location:** `src/main/java/com/tclife/pages/`
- **Purpose:** This pattern models the web application's user interface. Each class in this package represents a specific page (e.g., `LoginPage`).
- **Responsibilities:**
    - To store the locators (selectors) for web elements on that page.
    - To contain methods that represent user interactions on that page (e.g., `doLogin()`).
    - **Allure Integration:** Methods are wrapped with `Allure.step()`, and screenshots are attached after each action to provide a detailed, visual step-by-step report.

### 4.5. Test Layer (`/tests` package)

- **Location:** `src/test/java/com/tclife/tests/`
- **Purpose:** Contains the actual test cases.
- **Responsibilities:**
    - Each test method represents a specific scenario to be verified.
    - They use methods from the Page Object classes to perform actions.
    - They contain TestNG assertions (`Assert.*`) to validate the expected outcomes.
    - They inherit from `BaseTest` to leverage the browser setup/teardown logic.

### 4.6. Reporting (Allure Framework)

- **Integration:** Configured via `pom.xml` using `allure-testng`, `allure-java-commons`, and `allure-maven` plugin.
- **Features:**
    - Generates an interactive HTML report.
    - Shows a step-by-step breakdown of each test case.
    - Includes screenshots embedded within each step.
    - Attaches a full Playwright Trace file for deep-dive debugging.

## 5. Execution Flow

1.  The user runs the `mvn clean test` command.
2.  Maven invokes the `maven-surefire-plugin`.
3.  Surefire reads `testng.xml` to determine which test classes to run.
4.  TestNG executes the tests. The `AllureTestNg` listener, specified in `pom.xml`, is activated.
5.  For each test, `BaseTest` sets up the browser and starts tracing.
6.  The test method calls Page Object methods. These methods perform browser actions and use `Allure.step()` to log steps and attach screenshots.
7.  After each test, `BaseTest` stops tracing and attaches the results to the Allure report.
8.  The Allure listener writes all collected data into the `target/allure-results` directory.
9.  The user runs `mvn allure:serve`.
10. The `allure-maven` plugin processes the files in `target/allure-results` and generates the final HTML report in a local web server.

## 6. How to Run

1.  **Run Tests:**
    ```sh
    mvn clean test
    ```

2.  **View Report:**
    ```sh
    mvn allure:serve
    ```
