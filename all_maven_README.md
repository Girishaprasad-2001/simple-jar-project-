# Maven Build & Lifecycle Automation Guide

A comprehensive reference sheet for executing core lifecycle phases, debugging dependencies, and optimizing build workflows in Java applications using Maven.

---

## Core Lifecycle Commands

These commands represent the standard lifecycle phases of a Java project. Always execute these from your project root folder (where your `pom.xml` file sits).

*   **`mvn clean`**: Deletes the `target/` directory to remove all old compiled classes and build caches.
*   **`mvn compile`**: Compiles the raw source code files (`.java`) into executable bytecode files (`.class`).
*   **`mvn test`**: Runs all your automated unit tests (like JUnit) and generates code coverage data.
*   **`mvn package`**: Compiles your code, runs your tests, and bundles everything into an executable `.jar` or `.war` file inside the `target/` folder.
*   **`mvn install`**: Packages your project and installs the JAR into your local machine's local repository (`~/.m2/repository`) so other local projects can use it as a dependency.

---

## 🚀 Combining Common Phases (Recommended)

In day-to-day development, you will usually chain these phases together to ensure you are building fresh code from scratch.

*   **`mvn clean compile`**: Wipes old builds and recompiles everything to make sure there are no ghost compilation errors.
*   **`mvn clean test`**: Wipes old test caches and runs your entire unit test suite cleanly (triggers JaCoCo reports).
*   **`mvn clean package`**: The definitive command to safely build your final, production-ready, executable JAR file.

---

## 🏃 Fast Execution Commands

If you want to speed up your local workflow or bypass certain phases, use these commands:

*   **`mvn exec:java -Dexec.mainClass="Main"`**: Runs your application's `main` method immediately without packaging it into a JAR first.
*   **`mvn clean package -DskipTests`**: Compiles and bundles your executable JAR file immediately, completely skipping the execution of your unit tests to save time.
*   **`mvn clean package -Dmaven.test.skip=true`**: The -DskipTests flag still compiles your test files to make sure there are no syntax errors, but it does not execute them. If you want to skip both compiling and running your tests entirely to make the build even faster, use this flag

---

## 🔍 Dependency & Debugging Commands

Use these commands when managing third-party libraries or troubleshooting configuration issues.

*   **`mvn dependency:tree`**: Prints a visual tree diagram of all your project dependencies to help you find and fix library version conflicts.
*   **`mvn help:effective-pom`**: Displays the final XML layout of your `pom.xml` after merging all hidden, default underlying Maven settings.

