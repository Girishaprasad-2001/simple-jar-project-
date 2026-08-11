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

