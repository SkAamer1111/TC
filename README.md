# TC
# Task  - 1

# Create **3 pipelines** to validate CI/CD using **GitHub Actions, Jenkins, Docker, AWS EC2, and AWS S3**.

Build a simple web application, create Docker image, test it, deploy it on EC2, and upload logs to S3.

---

# What to Build

Build a simple web app with one endpoint:

```
/health
```

Expected output:

```json
{
  "status": "UP",
  "message": "App is running"
}
```

---

# Where to Use GitHub Actions

Use **GitHub Actions** for the first-level validation.

GitHub Actions should run when code is pushed to GitHub.

Use GitHub Actions for:

```
Code checkout
Install dependencies
Run basic test
Validate Docker build
```

Purpose:

```
GitHub Actions will check whether the code is valid before Jenkins deployment starts.
```

---

# Where to Use Jenkins

Use **Jenkins** for the main CI/CD pipeline and deployment.

Jenkins should run after GitHub Actions passes.

Use Jenkins for:

```
Pull latest code from GitHub
Build Docker image
Run container test
Deploy Docker container to AWS EC2
Upload deployment log to AWS S3
```

Purpose:

```
Jenkins will handle build, test, deployment, and artifact upload.
```

---

# Pipeline 1: GitHub Actions Validation Pipeline

Create this pipeline in GitHub Actions.

It should do:

```
Checkout code
Install dependencies
Run basic test
Validate Docker image build
```

Output:

```
GitHub Actions should pass successfully
```

---

# Pipeline 2: Jenkins Build and Test Pipeline

Create this pipeline in Jenkins using Jenkinsfile.

It should do:

```
Pull latest code from GitHub
Build Docker image
Run Docker container locally
Check /health endpoint
```

Output:

```
Docker image should build successfully
Application health check should pass
```

---

# Pipeline 3: Jenkins Deploy Pipeline

Create this also in Jenkins using Jenkinsfile.

It should do:

```
Connect to AWS EC2
Stop old container
Remove old container
Run new Docker container
Check application on EC2
Upload deployment log to AWS S3
```

Output:

```
Application should run on EC2
Deployment log should be uploaded to S3
```

---

# Flow Diagram

```mermaid
flowchart TD
    A[Developer pushes code to GitHub] --> B[GitHub Actions Validation Pipeline]

    B --> C[Checkout code]
    C --> D[Install dependencies]
    D --> E[Run basic test]
    E --> F[Validate Docker build]

    F --> G{GitHub Actions passed?}

    G -- No --> H[Fix code and push again]

    G -- Yes --> I[Jenkins Build and Test Pipeline]

    I --> J[Pull latest code from GitHub]
    J --> K[Build Docker image]
    K --> L[Run container locally]
    L --> M[Check /health endpoint]

    M --> N{Jenkins test passed?}

    N -- No --> O[Fix issue and rerun Jenkins]

    N -- Yes --> P[Jenkins Deploy Pipeline]

    P --> Q[Connect to AWS EC2]
    Q --> R[Stop and remove old container]
    R --> S[Run new Docker container]
    S --> T[Check app on EC2]
    T --> U[Upload deployment log to AWS S3]

    U --> V[Task completed]
```

---

# Deliverables

Submit:

```
GitHub repository link
Application source code
Dockerfile
GitHub Actions workflow file
Jenkinsfile
Screenshot of GitHub Actions validation pipeline
Screenshot of Jenkins build and test pipeline
Screenshot of Jenkins deploy pipeline
EC2 public IP or URL
Screenshot of application running on EC2
Screenshot of deployment log uploaded in S3
README file with steps
```

---

# Tips and Tricks

Keep the app simple.

Use GitHub Actions only for validation before deployment.

Use Jenkins for actual build, test, deploy, and S3 upload.

Do not store AWS keys, SSH keys, or passwords in code.

Use GitHub Secrets for GitHub Actions.

Use Jenkins Credentials for AWS and EC2 access.

Use clear Docker image tags:

```
app-name:build-number
app-name:git-commit-id
```

Test Docker locally before deploying:

```bash
docker build -t myapp .
docker run -p 8080:8080 myapp
curl http://localhost:8080/health
```

On EC2, stop old container before starting new one:

```bash
docker stop myapp || true
docker rm myapp || true
```

Upload deployment log to S3:

```bash
aws s3 cp deployment.log s3://bucket-name/logs/build-${BUILD_NUMBER}.log
```
