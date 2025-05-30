# Temporal Orchestration**Date**: 16 July 2025
**Duration**: 09:00 AM - 05:00 PM
**Participants**: 70 engineers
**Venue**: 

## Overview

Designing Reliable Workflows and Activities

This session introduces engineers to the core concepts of building reliable, long-running workflows using Temporal — a durable, open-source orchestration engine designed for fault-tolerant distributed systems. You’ll learn how Temporal abstracts away infrastructure failures while providing clear, testable business logic through workflows and activities. With Temporal’s runtime durability, workflows can sleep, retry, or resume seamlessly across service restarts and crashes, giving you strong guarantees out of the box.

Participants will get hands-on experience designing workflows and activities, handling timeouts and retries, and modeling real-world use cases where traditional polling or message queues fall short. We’ll also cover Temporal’s execution model, key architectural components, and how it ensures consistency across microservices — making it a powerful tool for coordinating complex operations in a scalable and observable way.

## Pre-Training Setup Requirements

### Infrastructure Setup

1. Temporal Server Setup
2. Database Setup

### Local Setup

1. IntelliJ
2. JDK 21 or above, with Gradle 8.3 and above
3. Docker and Docker Compose
4. Homebrew

## Sessions

> **Note**: Find a good and simplistic use case for local temporal run

### Session 1

### Local Running Temporal

During this session participants will start by starting their own local Temporal setup.

Setup:
* Setting up Local environment
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
* Starting up your first Temporal Workflow 
  * Start from Spring Initializr
  * Add dependency for Temporal
    ```kotlin
    implementation("io.temporal:temporal-spring-boot-starter:$temporal")
    ```
  * Configure your Temporal library
    ```kotlin
    // Ensure Temporal can serialise/deserialise different kinds of payload
    @Configuration
    class TemporalConfig {
    
        companion object {
            const val DEFAULT_TASK_QUEUE = "DefaultTaskQueue"
            // Define more TaskQueue here if you need
        }
    
        /**
         * Creates a DataConverter bean for Temporal workflow data conversion.
         * Supports multiple payload types including null, byte arrays, protobuf, and JSON.
         *
         * @param objectMapper Jackson ObjectMapper for JSON conversion
         * @return Configured DataConverter instance
         */
        @Bean
        fun dataConverter(objectMapper: ObjectMapper): DataConverter {
            return DefaultDataConverter(
                NullPayloadConverter(),
                ByteArrayPayloadConverter(),
                ProtobufJsonPayloadConverter(),
                JacksonJsonPayloadConverter(objectMapper),
            )
        }
    }
    ```
    ```kotlin
    // Ensure our Worker is started when the application is started 
    @Configuration
    class TemporalInitialiser(private val factory: WorkerFactory) {
    
        private val logger = KotlinLogging.logger {}
    
        /**
         * Event listener that starts the Temporal worker factory when the application is ready.
         * This ensures that the worker factory is properly initialized after all Spring beans are created.
         *
         * @param event ApplicationReadyEvent triggered when the application is fully started
         */
        @Observed
        @EventListener(ApplicationReadyEvent::class)
        fun onApplicationReady(event: ApplicationReadyEvent) {
            if (!factory.isStarted) factory.start()
    
            logger.info { ">> WorkerFactory :: Started!" }
        }
    }
    ```
  * Create your first Temporal Workflow
    ```kotlin
    @WorkflowInterface
    interface MyFirstWorkflow {
        
        @WorkflowMethod
        fun start(): String
    }
    
    @WorkflowImpl(taskQueues = [TemporalConfig.DEFAULT_TASK_QUEUE])
    class MyFirstWorkflowImpl : MyFirstWorkflow {
    
        override fun start(): String {
            return "Hello World!"
        }
    }
    ```

### Lunch

### Session 2
