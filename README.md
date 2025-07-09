# MaerskAppAPITesting

This repository contains Automation test scripts for the Maersk API, implemented using a Behavior-Driven Development (BDD) framework.

## Project Structure

- `src/main/java` - Contains the main implementation code
- `src/test/resources/features` - Contains Gherkin feature files
- `src/test/java/scriptRunner` - Contains Cucumber test runners

## Configuration Files

### YAML Configuration Files

This project includes several YAML configuration files:

1. **GitHub Actions Workflow** (`.github/workflows/maven.yml`)
   - Configures CI/CD pipeline for automated building and testing
   - Runs on push to main branch and pull requests
   - Uploads test reports as artifacts

2. **Docker Configuration** (`Dockerfile` and `docker-compose.yml`)
   - Allows running tests in a containerized environment
   - Ensures consistent test execution across different environments

3. **Test Environment Configuration** (`config.yml`)
   - Configures different test environments (test, staging, production)
   - Defines execution parameters and reporting settings

## Running Tests

### Using Maven
```bash
mvn clean test
```

### Using Docker
```bash
docker-compose up
```

### Using GitHub Actions
Push changes to the main branch to trigger the CI/CD pipeline.

## Test Reports

After running the tests, reports can be found in the following location:
- Cucumber HTML Reports: `target/cucumber-reports/cucumber-html-reports/`
- Extent Reports: `target/cucumber-reports/`
