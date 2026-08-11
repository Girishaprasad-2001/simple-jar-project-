# CI/CD Framework Reference

A high-level comparison guide between popular automation servers used to orchestrate continuous integration and continuous delivery (CI/CD) pipelines.

---

## Jenkins vs. GitHub Actions

Since we previously built your build pipeline using GitHub Actions, it helps to understand how they compare across infrastructure, hosting, and configuration types:

*   **GitHub Actions**: Managed completely by GitHub in the cloud. It runs your configuration scripts automatically on GitHub's own server infrastructure using `.yaml` configurations.
*   **Jenkins**: An open-source server that you must host yourself on your own computer or cloud server. It uses a flexible coding language called Groovy (`Jenkinsfile`) and is highly scalable for complex, enterprise-level project spaces.

---

### Core Structural Layout Summary

| Feature | GitHub Actions | Jenkins |
| :--- | :--- | :--- |
| **Hosting Model** | Cloud-managed (SaaS) | Self-hosted (On-premise / Custom Cloud) |
| **Configuration File** | `.github/workflows/*.yaml` | `Jenkinsfile` (Declarative or Scripted) |
| **Language Base** | YAML | Groovy |
| **Maintenance** | Zero infrastructure overhead | Requires regular server patching and plugin updates |

### Continuous Integration:
* Continuous Integration is the practice of automatically building and testing code every time a developer pushes changes to the repository.
### Continuous delivery :
* Continuous delivery is the practice of automatically preparing and delivering tested code to a staging or pre-production environment but requires manual approval before deploying to production.
### Continuous Deployment :
* Continuous Deploymen is the practice of automatically deploying very code changes that passes all tests diretly to producation without any manual approval. 

### EX:
* CI -- automatically building and testing.
* CD -- automatically preparing and delivering.
* CD -- automatically deploying very code changes.

# Jenkins JDK 21 Distributed Cluster Setup

A step-by-step infrastructure configuration guide for deploying a master-agent architecture using Docker containers and configuring a Java 21 environment.

---

## Step 1: Create an Isolated Docker Network
First, create a dedicated virtual network so your master container and slave container can communicate securely using their container names:

```bash
docker network create jenkins-net
```

---

## Step 2: Launch the JDK 21 Master Container
If you have an old master running, stop it first (`docker stop jenkins-master && docker rm jenkins-master`). Then run the updated command using the `lts-jdk21` image tag:

```bash
docker run -d \
  --name jenkins-master \
  --network jenkins-net \
  -p 8080:8080 \
  -p 50000:50000 \
  -v jenkins_master_data:/var/jenkins_home \
  jenkins/jenkins:lts-jdk21
```

### Retrieve Your Setup Password
Wait about 15 seconds for the master to initialize. Then, extract the temporary administrator unlock token from the container logs:

```bash
docker logs jenkins-master
```

---

## Step 3: Launch the JDK 21 Slave Container
Similarly, swap your worker container to the `alpine-jdk21` image. This ensures your slave node has native access to the Java 21 compiler (`javac`) to process modern source code features:

```bash
docker run -d \
  --name jenkins-slave \
  --network jenkins-net \
  --privileged \
  -u root \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkins/inbound-agent:alpine-jdk21 \
  -url http://jenkins-master:8080 \
  -secret YOUR_COPIED_SECRET_TOKEN_HERE \
  -name docker-agent
```

---

## Step 4: Register JDK 21 Inside the Jenkins Web UI

Even if your containers are running Java 21, you must tell the Jenkins orchestration software where to locate the path to the Java installation.

1. Open your web browser and go to your dashboard at `http://localhost:8080`.
2. From the sidebar menu, click **Manage Jenkins ➡️ Tools**.
3. Scroll down to the **JDK installations** configuration section and click **Add JDK**.
4. Configure the settings:
   * **Name**: `JDK21`
   * **Uncheck** `Install automatically` (since it is already natively bundled into your Docker image).
   * **JAVA_HOME**: `/opt/java/openjdk` *(This is the exact, standard system file path where Eclipse Temurin sets up Java inside official containers)*.
5. Click **Save** at the bottom of the screen.

---

## Step 5: Tell Your Pipeline to Use It
Inside your project script (`Jenkinsfile`), you can now explicitly reference this specific JDK workspace configuration profile so your Maven builds run smoothly:

```groovy
pipeline {
    agent { label 'docker-agent' }
    
    tools {
        // This instructs Jenkins to inject your newly configured JDK 21 path into the build environment
        jdk 'JDK21'
    }

    stages {
        stage('Compile Code') {
            steps {
                // This will verify that java -version outputs version 21
                sh 'java -version'
                sh 'mvn clean package -DskipTests'
            }
        }
    }
}
```

