# Simple Java Web Server Project

A lightweight, standalone Java web server built using Java's native `HttpServer` utility. This application packages into an executable JAR file and runs inside a lightweight Alpine Docker container on port `8080`.

---

## 📂 Project Structure

```text
simple-jar-project/
├── pom.xml
├── Dockerfile
├── README.md
└── src/
    └── main/
        └── java/
            └── Main.java
```

---

## 🛠️ Local Prerequisites

Before compiling or running the project, make sure you have the following software installed:
* **Java Development Kit (JDK)**: Version 21 or higher
* **Apache Maven**: Setup locally to use the `mvn` command
* **Docker Desktop**: For containerization testing

---

## ⚙️ How to Build and Run Locally

### 1. Compile and Package the JAR
Run the following Maven command in the root folder to clean old builds and compile a new executable JAR file:
```bash
mvn clean package
```
This generates the packaged file inside the `target/` directory:
`target/simple-jar-project-1.0-SNAPSHOT.jar`

### 2. Run the JAR Directly
Launch the compiled standalone application using the standard Java archive runner command:
```bash
java -jar target/simple-jar-project-1.0-SNAPSHOT.jar
```

---

## 🐳 Running with Docker

This application includes a multi-platform optimized `Dockerfile` that configures port `8080`.

### 1. Build the Docker Image
```bash
docker build -t simple-web-app .
```

### 2. Run the Docker Container
Map your computer's local port `8080` to the container's network port `8080` using the port forwarding flag (`-p`):
```bash
docker run -p 8080:8080 --name running-web-app simple-web-app
```

---

## 🌐 Verifying the Application

Once the application is running (either locally or inside Docker), open your preferred web browser and navigate to:

```text
http://localhost:8080/?name=YourName
```

### Expected Response
The browser window will display the following plain text output string:
`Hello, YourName! Your executable JAR is running on port 8080.`

