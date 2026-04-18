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
- **CI/CD:** GitHub Actions, Jenkins
- **BDD:** Cucumber

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
│       │       ├── stepdefinitions/
│       │       │   ├── Hooks.java
│       │       │   ├── LoginSteps.java
│       │       │   └── TestContext.java
│       │       └── tests/
│       │           └── TestRunner.java
│       └── resources/
│           ├── config.properties
│           ├── features/
│           │   └── Login.feature
│           └── testng.xml
└── target/
    ├── allure-results/
    └── traces/
```

## 4. Architectural Components

### 4.1. BDD Layer (`.feature` files)
- **Location:** `src/test/resources/features`
- **Purpose:** Defines test scenarios in a human-readable language (Gherkin). This acts as "living documentation" and allows collaboration between technical and non-technical team members.

### 4.2. Step Definition Layer (`...Steps.java`)
- **Location:** `src/test/java/com/tclife/stepdefinitions`
- **Purpose:** Acts as the "glue" between the feature files and the application code. Each step in a Gherkin scenario is mapped to a Java method here. These methods orchestrate the test flow by calling methods from the Page Object layer.

### 4.3. Page Object Model (POM) (`/pages` package)
- **Location:** `src/main/java/com/tclife/pages/`
- **Purpose:** Encapsulates the UI and user interactions for each page, separating test logic from page structure.

### 4.4. Core Automation Layer (`/base` package)
- **Location:** `src/main/java/com/tclife/base` & `src/test/java/com/tclife/stepdefinitions/Hooks.java`
- **Purpose:** Manages the browser lifecycle, configuration, and core Playwright functionalities.
- **Key Features:**
    - **`PlaywrightFactory`:** A thread-safe factory for creating and managing Playwright browser instances. It is CI/CD aware and can force headless mode.
    - **`Hooks`:** Uses Cucumber's hooks (`@Before`, `@After`) to set up and tear down the browser for each scenario.

### 4.5. Test Runner (`TestRunner.java`)
- **Location:** `src/test/java/com/tclife/tests/TestRunner.java`
- **Purpose:** A bridge between TestNG and Cucumber. It configures Cucumber options, such as the location of feature files, step definitions, and reporting plugins.

## 5. CI/CD with GitHub Actions & Jenkins

The framework is fully integrated with CI/CD pipelines to automate the entire testing process.
- **GitHub Actions:** Defined in `.github/workflows/ci.yml`, it automatically runs tests on every push/pull request and deploys the Allure report to GitHub Pages.
- **Jenkins:** Defined in `Jenkinsfile`, it allows for scheduled runs (e.g., nightly builds), more complex integrations, and sends email notifications.
- **Cross-Repository Triggering:** The CI is designed with `workflow_dispatch` to be triggerable by external systems, such as the main application's CI pipeline, enabling a full DevOps feedback loop.

## 6. How to Run

1.  **Run Tests Locally:** `mvn clean test`
2.  **View Local Report:** `mvn allure:serve`

## 7. Future Improvements

### 7.1. Environment Management
- **Goal:** To easily run the same test suite against different environments (e.g., QA, Staging, Production).
- **Implementation:** Use Maven Profiles (`-P qa`) to switch between different configuration files (`config-qa.properties`, `config-staging.properties`).

### 7.2. Integration with AI-Powered Tools
- **Goal:** To accelerate test creation and improve maintenance efficiency.
- **Concept:** This framework serves as the perfect foundation to integrate with AI-driven testing tools and services (like Microsoft Playwright Testing or similar).
- **Workflow:**
    1.  **AI Generation:** Use an AI tool's CLI to automatically explore the application and generate raw test scripts.
    2.  **Refactoring & Integration:** A QA Engineer would then refactor this raw code, integrating it into the existing BDD/POM structure (i.e., creating feature files, step definitions, and page objects). This ensures the new tests adhere to the framework's high standards of readability and maintainability.
    3.  **Self-Healing:** Leverage AI capabilities to automatically suggest or apply fixes for broken locators, reducing the manual effort of test maintenance.
- **Benefit:** This approach combines the speed of AI generation with the robustness and structure of a well-designed framework.
