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
