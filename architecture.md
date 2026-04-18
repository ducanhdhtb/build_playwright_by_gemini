# Playwright Test Automation Framework Architecture

## 1. Overview

This document outlines the architecture of the test automation framework built for the `tclife` project. The framework is designed to be robust, maintainable, and scalable for testing modern web applications. It uses a combination of industry-standard tools and design patterns to achieve this.

## 2. Core Technologies

- **Programming Language:** Java 11
- **Build & Dependency Management:** Apache Maven
- **Browser Automation:** Playwright for Java
- **Test Runner:** TestNG
- **Reporting:** Allure Framework
- **Data-Driven:** Apache POI for Excel integration
- **CI/CD:** GitHub Actions

## 3. Project Structure

The framework follows a standard Maven project structure, organized logically to separate concerns.

```
tclife/
├── .github/
│   └── workflows/
│       └── ci.yml
├── pom.xml
├── architecture.md
├── README.md
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
│       │       ├── tests/
│       │       │   └── LoginPageTest.java
│       │       └── utils/
│       │           └── ExcelUtil.java
│       └── resources/
│           ├── config.properties
│           ├── testdata/
│           │   └── SauceDemoTestData.xlsx
│           └── testng.xml
└── target/
    ├── allure-results/
    └── traces/
```

## 4. Architectural Components

### 4.1. Configuration (`config.properties`)
- **Location:** `src/test/resources/config.properties`
- **Purpose:** To externalize test configuration. This allows us to change key parameters like the target browser, base URL, and headless mode without modifying the source code.

### 4.2. Data-Driven Testing (`ExcelUtil.java`)
- **Location:** `src/test/java/com/tclife/utils/ExcelUtil.java`
- **Purpose:** To separate test data from test logic, enabling true data-driven testing.
- **Key Features:**
    - Uses the Apache POI library to read data from `.xlsx` files.
    - The `getTestData` method converts an Excel sheet into an `Object[][]` array, which is directly consumable by TestNG's `@DataProvider`.
    - Allows non-technical users to manage test cases by simply editing the `SauceDemoTestData.xlsx` file.

### 4.3. Factory Layer (`PlaywrightFactory.java`)
- **Location:** `src/main/java/com/tclife/base/PlaywrightFactory.java`
- **Purpose:** A centralized factory for creating and managing Playwright objects (`Playwright`, `Browser`, `BrowserContext`, `Page`).
- **Key Features:**
    - Uses `ThreadLocal` to ensure thread safety for parallel test execution.
    - Reads `config.properties` to initialize the browser.
    - **CI/CD Aware:** Automatically detects if it's running in a CI environment (like GitHub Actions) and forces headless mode to prevent errors.

### 4.4. Base Layer (`BaseTest.java`)
- **Location:** `src/test/java/com/tclife/base/BaseTest.java`
- **Purpose:** Acts as the parent class for all test classes, handling common setup and teardown logic.
- **Key Features:**
    - **`@BeforeClass` / `@AfterClass`:** Manages the browser lifecycle.
    - **`@BeforeMethod` / `@AfterMethod`:** Manages Playwright's Tracing for each test and attaches results (trace files, screenshots on failure) to the Allure report.

### 4.5. Page Object Model (POM) (`/pages` package)
- **Location:** `src/main/java/com/tclife/pages/`
- **Purpose:** Models the web application's UI, encapsulating locators and user interactions for each page.
- **Key Features:**
    - **Allure Integration:** Methods are wrapped with `Allure.step()`, and screenshots are attached after each action to provide a detailed, visual step-by-step report.

### 4.6. Test Layer (`/tests` package)
- **Location:** `src/test/java/com/tclife/tests/`
- **Purpose:** Contains the actual test cases, which use Page Objects to perform actions and TestNG assertions to validate outcomes.
- **Key Features:**
    - Uses `@DataProvider` to receive data from `ExcelUtil`, allowing one test method to execute multiple scenarios.

## 5. CI/CD with GitHub Actions

The framework is integrated with a CI/CD pipeline defined in `.github/workflows/ci.yml`.

### 5.1. CI for the Framework Itself
This is the primary purpose of the current CI setup. It acts as a quality gate for the test automation code itself.
- **Trigger:** Automatically runs on every `push` or `pull_request` to the `main` branch.
- **Goal:** To ensure that any new or modified test code does not break the existing test suite.
- **Process:**
    1.  Sets up a clean environment.
    2.  Runs `mvn clean test`.
    3.  Generates and deploys the Allure report to GitHub Pages for review.

### 5.2. CI in a Real-World Scenario (Cross-Repository Trigger)
In a real-world project, this framework acts as a "quality gate" for the main web application. The CI pipeline is designed to be triggered by the application's CI pipeline.
- **Trigger:** `workflow_dispatch` event. This allows an external system to start our workflow via an API call.
- **Example Flow:**
    1.  A developer pushes code to the **Application Repository**.
    2.  The **Application CI** builds the app and deploys it to a QA environment (e.g., `https://qa.example.com`).
    3.  The **Application CI** then sends a signal (e.g., using `gh workflow run`) to our **Test Automation Repository**. This signal includes the URL of the QA environment as a parameter.
    4.  Our workflow (`ci.yml`) receives the signal and the URL.
    5.  It runs `mvn clean test`, passing the QA URL to the test execution.
    6.  The test results (pass/fail) are reported back, determining if the application build is safe to proceed to the next stage.

## 6. How to Run

1.  **Run Tests Locally:**
    ```sh
    mvn clean test
    ```

2.  **View Local Report:**
    ```sh
    mvn allure:serve
    ```

## 7. Future Improvements

- **Environment Management:** Implement Maven Profiles (`-P qa`, `-P staging`) to manage different configuration files (`config-qa.properties`) for different test environments.
- **BDD Integration:** Integrate Cucumber to write test scenarios in Gherkin, making them accessible to non-technical stakeholders.
- **Utility Classes:** Create more helper classes (e.g., for API interactions, date manipulation) to reduce code duplication.
