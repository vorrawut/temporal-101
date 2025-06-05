# Temporal Orchestration
* **Date**: 16 July 2025
* **Duration**: 09:00 AM - 05:00 PM
* **Participants**: 70 engineers
* **Venue**: 

## Overview

Designing Reliable Workflows and Activities

This session introduces engineers to the core concepts of building reliable, long-running workflows using Temporal — a durable, open-source orchestration engine designed for fault-tolerant distributed systems. You'll learn how Temporal abstracts away infrastructure failures while providing clear, testable business logic through workflows and activities. With Temporal's runtime durability, workflows can sleep, retry, or resume seamlessly across service restarts and crashes, giving you strong guarantees out of the box.

Participants will get hands-on experience designing workflows and activities, handling timeouts and retries, and modeling real-world use cases where traditional polling or message queues fall short. We'll also cover Temporal's execution model, key architectural components, and how it ensures consistency across microservices — making it a powerful tool for coordinating complex operations in a scalable and observable way.

## [Pre-Training Setup Requirements](SETUP.md)

## Day One

### Lab 1: Hello Workflow – Durable From Line One
**Objectives**:
* Understand how Temporal manages workflow state and execution.
* Experience replay-based execution (no code rerun on restart).
* Introduce workflow vs activity boundaries.

**Steps**:
* Create a simple workflow with a `Workflow.sleep(10.seconds())`
* Add a logging step before and after the sleep.
* Run it, then stop the worker midway — restart and observe replay/resume.
* Discussion point: What persisted? What was re-executed?

**Concept Drop**:
* What is a Workflow (deterministic and replayable)

### Lab 2: Adding Activities & Task Queues – Side-Effects and Isolation
**Objectives**:
* Create real side-effecting activities.
* Learn Temporal's boundary between logic (workflow) and I/O (activity).
* Understand task queues and worker assignment patterns.
* Experience logs and outputs on the activity side.

**Steps**:
* Create an EmailActivity.sendConfirmation() method with a log.
* Invoke it from workflow using specific task queue.
* Configure workers to listen to different task queues.
* Route activities to specific workers using task queue names.
* Throw an exception in the activity and see retry behavior.

**Concept Drop**:
* Activities are retried by Temporal, not you
* Activities must be idempotent or handled defensively
* Task queues route work to specific workers and enable scaling patterns
* Activity timeouts: start-to-close vs schedule-to-close

### Lab 3: Resilience & Durable Timers – Retries, Timeouts & Approval Waiting
**Objectives**:
* Configure retry policies on activities.
* See exponential backoff and max attempts in action.
* Use Workflow.sleep() to represent delay logic.
* Simulate SLA timer / reminder flow and learn how timers survive restarts.

**Steps**:
* Add retry config to your activity with maxAttempts = 3.
* Make your activity randomly fail (50% of the time).
* Watch retries succeed or fail depending on randomness.
* Add `Workflow.sleep(Duration.ofSeconds(60))` between two steps.
* Kill the worker after sleep begins and restart to observe resume behavior.
* Add a short timeout and watch the impact on both retries and timers.

**Concept Drop**:
* Retry policies are declarative with exponential backoff built-in
* Difference between activity vs workflow timeouts
* Durable timers = no busy polling, great for "wait for 3 days" scenarios
* Sleep is persisted — not RAM-based, survives restarts seamlessly

### Lab 4: Signals & Queries (Optional / Advanced)
**Objectives**:
* Learn how external events modify workflow behavior.
* Inject "approval" via a signal to skip sleep early.

**Steps**:
* Create a `receiveApproval()` signal method.
* Update workflow to wait for either signal or sleep.
* Trigger signal via CLI or test code and observe the fast path.

**Concept Drop**:
* Signals let you interact with workflows from outside
* Temporal provides runtime API access (`WorkflowClient.signalWithStart`, etc.)
* This sets up Stage 2 exploration of full signal/query communication models

### Lab 5: Determinism – What Can Break It
**Objectives**:
* Understand replay problems
* Learn how to safely use time and randomness

**Steps**:
* Add `UUID.randomUUID()` and watch the workflow fail on replay
* Replace with Workflow.randomUUID() and see success
* Same test with `System.currentTimeMillis()` vs `Workflow.currentTimeMillis()`

**Concept Drop**:
* Temporal replays workflow logic →  non-deterministic code breaks replay
* Use Temporal-safe APIs
* SideEffect / MutableSideEffect for some escape hatches

## Day Two

### Lab 1: Child Workflows – Orchestrating at Scale
**Objectives**:
* Explore different workflow hierarchy patterns and their use cases
* Understand workflow hand-off vs supervision vs recursion patterns
* Experience how different patterns affect lifecycle and error handling
* Learn when to choose each pattern for different business scenarios

**Steps**:
* Different patterns:
  * **Hand-off**: Workflow-A that starts Workflow-B (handoff to fulfillment)
  * **Recursive**: Workflow that calls itself (polling loop)
  * **Supervised**: Workflow-A uses an activity to launch Workflow-B
* Compare lifecycle behavior: what happens when parent completes vs fails
* Observe execution trees and parent-child relationships in Temporal UI
* Test error propagation: how does child failure affect parent

**Concept Drop**:
* Different patterns:
  * **Hand-off**: Parent completes after starting child - sequential stages
  * **Recursive**: Workflow calls itself - for polling, retries, or state machines
  * **Supervised**: Activity launches workflows - parent monitors and intervene
* Patterns have different lifecycle coupling and error propagation behavior
* Choose pattern based on biz logic: sequential vs monitoring vs self-governing

### Lab 2: Worker Versioning
**Objectives**:
* Understand why workflow versioning matters in production
* Learn the dangers of changing workflow logic without proper versioning
* Recognize when versioning strategies are needed

**Steps**:
* Demonstrate a simple workflow change that breaks replay
* Show how existing running workflows fail when logic changes
* Discuss `Workflow.getVersion()` as the safe approach (demo only)
* Review real-world scenarios where versioning becomes critical

**Concept Drop**:
* Changing workflow code can break existing running workflows
* Versioning is complex - plan your workflow structure carefully from the start
* When in doubt, create new workflows rather than modifying existing ones
* Versioning is something to be aware of, not actively pursued

### Lab 3: Fan-Out / Fan-In – Parallel Activity Execution

**Objectives:**
* Use Temporal's `Async.function` to launch parallel activities
* Fan-out multiple parallel activity tasks.
* Fan-in: wait for all of them to complete before proceeding.

**Steps:**
* In workflow, use `Async.function` to fan-out multiple activities
* Collect `futures` and use `.get()` to fan-in to wait for completion
* Log the completion of all jobs.
* Create an activity interface and implementation to process a single job (`processJob(jobId: String)`).
* Run the workflow with a list of job IDs and observe the parallel execution in logs.

**Concept Drop**:
* **Fan-out**: spawning parallel activity tasks.
* **Fan-in**: collecting results with `.get()` on futures.
* Temporal's **activity parallelism** is managed by workers – no thread starvation risk.
* No need for external rate limiting or saga patterns in this scenario – perfect for batch jobs or bulk processing.

### Lab 4: Saga Pattern & What to Avoid
**Objectives**:
* Implement saga-like patterns
* Learn when to avoid them

**Steps**:
* Implement compensation workflows (basic saga).
* Simulate partial failures.
* Discuss better approaches (e.g., using Temporal's durable activities/child workflows).

**Concept Drop**:
* Temporal discourages saga patterns for compensation
* Prefer durable workflows for eventual consistency

### Lab 5: Observability & Metrics
**Objectives**:
* Learn to monitor workflow health and performance
* Understand built-in Temporal metrics vs. custom
* Explore debugging techniques using Temporal UI and structured logging
* Discover what Prometheus metrics Temporal exposes automatically

**Steps**:
* Access Temporal server metrics at `:7233/metrics` endpoint
* Visit worker metrics endpoint at `:8080/metrics` for worker stats
* Examine workflow execution metrics: started, completed, failed, timed out
* Review activity metrics: scheduled, started, completed, failed, retry attempts
* Explore worker metrics: task queue polling, activity/workflow processing rates
* Check resource utilization metrics: goroutines, mem usage, db connections
* Understand histogram for exec duration and queue wait times at `/metrics`
* Query specific metrics using browser or curl commands for workflow patterns
* Compare metrics with actual workflow executions in Temporal UI at `:8233`

**Concept Drop**:
* Temporal exposes metrics on `:7233/metrics` (server) and `:8080/metrics` (worker)
* Workflow metrics track execution lifecycle
* Activity metrics show scheduling, execution, and retry patterns
* Worker metrics reveal processing rates and resource consumption patterns
* Duration histograms help identify performance bottlenecks and SLA violations
* Metrics are labeled with workflow types, activity names, and task queues

## Additional Topics for Consideration

The following topics could potentially be integrated into existing labs or added as supplementary content:

### Core Development Topics
* **Testing Workflows**: Unit testing, integration testing, TestWorkflowEnvironment
* **Continue-As-New**: Handling large workflows, history size limits, long-running processes
* **Search Attributes**: Custom search, workflow discovery, filtering capabilities
* **Schedules**: Cron-like scheduling, batch processing patterns, recurring workflows

### Advanced Patterns
* **Interceptors**: Logging, metrics, custom behavior injection at workflow/activity level
* **Advanced Task Queues**: Task queue priorities, sticky workers, task routing strategies
* **Error Handling Patterns**: Custom exceptions, activity heartbeats, workflow cancellation

### Production & Operations
* **Worker Configuration**: Tuning, capacity planning, resource limits, performance optimization
* **Security**: Authentication, authorization, encryption at rest/transit, namespace isolation
* **Local vs Cloud**: Temporal Cloud considerations, differences from self-hosted deployments
* **Production Best Practices**: Deployment strategies, blue-green deployments, rollback procedures

### Monitoring & Debugging
* **Advanced Observability**: Custom dashboards, SLA monitoring, alerting strategies
* **Debugging Techniques**: Workflow history analysis, common failure patterns, troubleshooting
* **Performance Tuning**: Identifying bottlenecks, optimizing workflow execution, scaling strategies
