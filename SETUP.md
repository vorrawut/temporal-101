# Infrastructure Setup

1. Temporal Server Setup
2. Database Setup

# Local Setup

1. IntelliJ
2. JDK 21 or above, with Gradle 8.3 and above
3. Docker and Docker Compose
4. Homebrew

# Local Running Temporal

During this session participants will start by starting their own local Temporal setup.

Setup:
## 1. Setting up Local environment
* Run our [docker-compose.yaml](./docker-compose.yaml)
  ```shell
  docker-compose up --detach
  ```
* Install Temporal CLI from Homebrew
  ```shell
  brew install temporal
  ```
* Create new Namespace for our Labs
  ```shell
  temporal operator namespace create --namespace loan
  ```
* Check Temporal CLI is running and Namespace present
  ```shell
  temporal operator namespace list
  ```

## 2. Starting up our first Temporal Workflow
* Start from Spring Initializr or clone this skeleton repo
* Add dependency for Temporal
```kotlin
implementation("io.temporal:temporal-spring-boot-starter:$temporal")
```
