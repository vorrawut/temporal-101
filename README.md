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

> **Note**: Find a good and simplistic use case for local temporal run

## Local Running Temporal

During this session participants will start by starting their own local Temporal setup.
> **Read**: Technical materials [here](MATERIALS.md)

## Labs

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

**Concept Drops**:
* What is a Workflow (deterministic and replayable)

### Lab 2: Adding Activities – Side-Effects and Isolation
**Objectives**:
* Create real side-effecting activities.
* Learn Temporal's boundary between logic (workflow) and I/O (activity).
* Experience logs and outputs on the activity side.

**Steps**:
* Create an EmailActivity.sendConfirmation() method with a log.
* Invoke it from workflow.
* Throw an exception in the activity and see retry behavior.

**Concept Drop**:
* Activities are retried by Temporal, not you
* Activities must be idempotent or handled defensively
* Activity timeouts: start-to-close vs schedule-to-close

### Lab 3: Resilience Through Retries & Timeouts
**Objectives**:
* Configure retry policies on activities.
* See exponential backoff and max attempts in action.
* Simulate transient failure (e.g., random failure in activity).

**Steps**:
* Add retry config to your activity with maxAttempts = 3.
* Make your activity randomly fail (50% of the time).
* Watch retries succeed or fail depending on randomness.
* Add a short timeout and watch the impact.

**Concept Drop**:
* Retry policies are declarative
* Difference between activity vs workflow timeouts

### Lab 4: Durable Timers & Approval Waiting
**Objectives**:
* Use Workflow.sleep() to represent delay logic.
* Simulate SLA timer / reminder flow.
* Learn how timers survive restarts.

**Steps**:
* Add `Workflow.sleep(Duration.ofSeconds(60))` between two steps.
* Kill the worker after sleep begins.
* Restart and observe execution picks up from where it left off.

**Concept Drop**:
* Durable timers = no busy polling
* Great for "wait for 3 days" scenarios
* Sleep is persisted — not RAM-based

### Lab 5: Signals & Queries (Optional / Advanced)
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

### Lab 6: Determinism – What Can Break It
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
