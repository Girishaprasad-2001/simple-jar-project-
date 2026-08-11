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

# Jenkins Agent Node Registration Guide

A step-by-step configuration guide for registering a permanent worker node inside the Jenkins Web User Interface.

---

## Step 4: Register the Slave Node in the Web UI

1. **Access the Dashboard**: Open your web browser and navigate to `http://localhost:8080`.
2. **Initial Setup**: Paste your temporary administrator unlock token extracted from the container logs, click through **"Install suggested plugins"**, and create your primary admin account.
3. **Navigate to Node Management**: From the dashboard sidebar menu on the left, click **Manage Jenkins ➡️ Nodes ➡️ New Node**.
4. **Define the Node**: Configure the initialization parameters:
   * **Node name**: `docker-agent`
   * **Type**: `Permanent Agent`
   * Click **Create**.
5. **Configure Agent Options**: On the settings page, establish the following values:
   * **Remote root directory**: `/home/jenkins/agent`
   * **Launch method**: `Launch agent by connecting it to the controller`
6. **Save Changes**: Click **Save** at the bottom of the screen to register your workspace.

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

